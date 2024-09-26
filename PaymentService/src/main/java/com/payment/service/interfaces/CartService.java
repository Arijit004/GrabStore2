package com.payment.service.interfaces;

import com.payment.dto.Product;
import com.payment.entity.Cart;
import org.springframework.stereotype.Service;

import java.util.Map;

//cart service...
@Service
public interface CartService {
    //1. create cart...
    public Cart createCart(String cname, String fullToken);

    //2. delete cart...
    public String deleteCart(String fullToken);

    //3. update cart...
    public Cart updateCart(String cname, String fullToken);

    //4. get cart...
    public Cart getCart(String fullToken);

    //5. add products to cart...
    public Product addProduct(int sid, int pid, String fullToken);

    //6. delete particular product from a cart...
    public String deleteProduct(int sid, int pid, String fullToken);

    //7. delete all products from a cart / can be a buying option as after buying, cart will be cleared...
    //we'll keep a map for storing prodName and prodPrice. so that we can print total price of all products...
    public Map<String, Double> buyCart(String fullToken);

}
