package com.payment.service.logics;

import com.payment.dto.Product;
import com.payment.dto.User;
import com.payment.entity.Cart;
import com.payment.exception.CartAlreadyExistsException;
import com.payment.exception.CartNotFoundException;
import com.payment.exception.ProductNotFoundException;
import com.payment.exception.UserNotFoundException;
import com.payment.feignClient.ProductClient;
import com.payment.feignClient.UserClient;
import com.payment.repository.CartRepository;
import com.payment.service.interfaces.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//service layer...
@Service
public class CartServiceImpl implements CartService {
    //repository object...
    @Autowired
    private CartRepository crepo;

    //product client...
    @Autowired
    private ProductClient pcli;

    //user client...
    @Autowired
    private UserClient ucli;


    @Override
    public Cart createCart(String cname, String fullToken) {
        //every user can will have a single cart, that's why cart_id and user_id will not be set by user end...
        //user can only set cart name...
        //fetch user from token and check user already has cart or not. if user has cart then throw exception...
        User u=ucli.findUser(fullToken).getBody();
        if(u==null){
            throw new UserNotFoundException("User not found with given token!!!");
        }
        if(crepo.findByUserId(u.getId()).isPresent()){
            throw new CartAlreadyExistsException("You already have a cart. Delete existing cart to create new one!!!");
        }

        //cart_id which will be the (number of records in db+1)...
        List<Cart> allCarts=crepo.findAll();
        long cartId=allCarts.size()+1;

        Cart cart=new Cart();
        cart.setCartId(cartId);
        cart.setCartName(cname);
        cart.setUserId(u.getId());
        return crepo.save(cart);
    }

    @Override
    public String deleteCart(String fullToken) {
        //fetch user from token and check user already has cart or not. if user doesn't have cart then throw exception...
        User u=ucli.findUser(fullToken).getBody();
        if(u==null){
            throw new UserNotFoundException("User not found with given token!!!");
        }
        //fetch cart, if not found then throw exception...
        Cart c=crepo.findByUserId(u.getId()).orElseThrow(()->new CartNotFoundException("You don't have any cart!!!"));
        crepo.delete(c);
        return "Deleted!!!";
    }

    @Override
    public Cart updateCart(String cname, String fullToken) {
        //fetch user from token and check user already has cart or not. if user doesn't have cart then throw exception...
        User u=ucli.findUser(fullToken).getBody();
        if(u==null){
            throw new UserNotFoundException("User not found with given token!!!");
        }
        //fetch cart, if not found then throw exception...
        Cart c=crepo.findByUserId(u.getId()).orElseThrow(()->new CartNotFoundException("You don't have any cart!!!"));
        //user can only update cartName...
        c.setCartName(cname);
        return crepo.save(c);
    }

    @Override
    public Cart getCart(String fullToken) {
        //fetch user from token and check user already has cart or not. if user doesn't have cart then throw exception...
        User u=ucli.findUser(fullToken).getBody();
        if(u==null){
            throw new UserNotFoundException("User not found with given token!!!");
        }
        //fetch cart, if not found then throw exception...
        Cart c=crepo.findByUserId(u.getId()).orElseThrow(()->new CartNotFoundException("You don't have any cart!!!"));
        return c;
    }

    @Override
    public Product addProduct(int sid, int pid, String fullToken) {
        //fetch product with the help of productClient...
        Product p=pcli.getProdById(sid, pid).getBody();
        if(p==null){
            throw new ProductNotFoundException("Product not found with given store and product ids!!!");
        }

        //set the product id as dto will be passed from store where id isn't given...
        p.setProd_id(pid);
        p.setStore_id(sid);

        //fetch user from token and check user already has cart or not. if user doesn't have cart then throw exception...
        User u=ucli.findUser(fullToken).getBody();
        if(u==null){
            throw new UserNotFoundException("User not found with given token!!!");
        }
        //fetch cart, if not found then throw exception...
        Cart c=crepo.findByUserId(u.getId()).orElseThrow(()->new CartNotFoundException("You don't have any cart!!!"));
        //now add product to cart and save cart object again...
        c.getAllProducts().add(p);
        //update total price...
        c.setTotalPrice(c.getTotalPrice()+p.getProd_dis_price());
        crepo.save(c);
        return p;
    }

    @Override
    public String deleteProduct(int sid, int pid, String fullToken) {
        //fetch user from token and check user already has cart or not. if user doesn't have cart then throw exception...
        User u=ucli.findUser(fullToken).getBody();
        if(u==null){
            throw new UserNotFoundException("User not found with given token!!!");
        }
        //fetch cart, if not found then throw exception...
        Cart c=crepo.findByUserId(u.getId()).orElseThrow(()->new CartNotFoundException("You don't have any cart!!!"));

        //product price which will be reduced from total amt...
        double pprice=0;

        //now delete product from cart and save cart object again...
        for(Product p:c.getAllProducts()){
            if(p.getProd_id()==pid && p.getStore_id()==sid){
                c.getAllProducts().remove(p);
                pprice=p.getProd_dis_price();
                break;
            }
        }

        //update total price...
        if(c.getTotalPrice()>0) {
            c.setTotalPrice(c.getTotalPrice() - pprice);
        }
        crepo.save(c);
        return "Deleted!!!";
    }

    @Override
    public Map<String, Double> buyCart(String fullToken) {
        //fetch user from token and check user already has cart or not. if user doesn't have cart then throw exception...
        User u=ucli.findUser(fullToken).getBody();
        if(u==null){
            throw new UserNotFoundException("User not found with given token!!!");
        }
        //fetch cart, if not found then throw exception...
        Cart c=crepo.findByUserId(u.getId()).orElseThrow(()->new CartNotFoundException("You don't have any cart!!!"));
        //check if cart is empty or not. if empty, throw exception...
        if(c.getAllProducts().isEmpty()){
            throw new ProductNotFoundException("Your cart is empty!!!");
        }
        //store productName and productPrice in a map and return that map after calculating total...
        Map<String, Double> invoice=new HashMap<>();
        double totalPrice=0;
        for(Product p:c.getAllProducts()){
            invoice.put(p.getProd_name(), p.getProd_dis_price());
            totalPrice+=p.getProd_dis_price();
        }
        invoice.put("Total amt.", totalPrice);
        crepo.delete(c);
        return invoice;
    }
}
