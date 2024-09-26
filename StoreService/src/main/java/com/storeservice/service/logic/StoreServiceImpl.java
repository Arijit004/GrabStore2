package com.storeservice.service.logic;

import com.storeservice.dto.ProductDto;
import com.storeservice.dto.User;
import com.storeservice.entity.Product;
import com.storeservice.entity.Store;
import com.storeservice.exception.*;
import com.storeservice.feignClient.UserClient;
import com.storeservice.repository.StoreRepository;
import com.storeservice.service.interfaces.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    //store repository object...
    @Autowired
    private StoreRepository srepo;

    //user client object (to fetch user details)...
    @Autowired
    private UserClient userClient;

    @Override
    public Store addStore(Store s, String fullToken) {
        //checking if user is vendor or not...
        //if user isn't vendor, throw UnauthorizeException...
        User u=userClient.findUser(fullToken).getBody();
        if(u==null){
            throw new UserNotFoundException("User not found with this token! Please provide valid token!!");
        }
        if(!u.getRole().equalsIgnoreCase("VENDOR")){
            throw new UnauthorizeException("You don't have the access to perform any operation related to store as your role isn't vendor!!!");
        }
        //checking if store already exists with given store id or not...
        if(srepo.findById(s.getStore_id()).isPresent()){
            throw new StoreAlreadyExistsException("Store already exists with given id!!!");
        }
        //setting user_id as vendor_id...
        s.setVendor_id(u.getId());
        return srepo.save(s);
    }

    @Override
    public Store findStore(int sid) {
        return srepo.findById(sid).orElseThrow(()->new StoreNotFoundException("Store not found with given id!!!"));
    }

    @Override
    public List<Store> findAllStores() {
        return srepo.findAll();
    }

    @Override
    public List<Store> findAllStoresByVendor(String fullToken) {
        //fetch the vendor details first...
        //if user isn't vendor, throw UnauthorizeException...
        User u=userClient.findUser(fullToken).getBody();
        if(u==null){
            throw new UserNotFoundException("User not found with this token! Please provide valid token!!");
        }
        if(!u.getRole().equalsIgnoreCase("VENDOR")){
            throw new UnauthorizeException("You don't have the access to perform any operation related to store as your role isn't vendor!!!");
        }

        //if vendor found, search all stores where store's vendor id matches with user's id...
        List<Store> allStores=new ArrayList<>();
        for(Store s:srepo.findAll()){
            if(s.getVendor_id()==u.getId()){
                allStores.add(s);
            }
        }
        return allStores;
    }

    @Override
    public List<Product> findAllProductsByName(String pname) {
        List<Product> allProds=new ArrayList<>();
        for(Store s:srepo.findAll()){
            for(Product p:s.getAllProducts()){
                if(p.getProd_name().equalsIgnoreCase(pname)){
                    allProds.add(p);
                }
            }
        }
        if(allProds.isEmpty()){
            throw new ProductNotFoundException("Product not found with given name. Please check again!!!");
        }
        return allProds;
    }

    @Override
    public List<Product> findAllProductsByStore(int sid) {
        Store s=srepo.findById(sid).orElseThrow(()->new StoreNotFoundException("Store not found with given id!!!"));
        return s.getAllProducts();
    }

    @Override
    public List<Product> findAllProductsByCategory(String pcategory) {
        List<Product> allProds=new ArrayList<>();
        for(Store s:srepo.findAll()){
            for(Product p:s.getAllProducts()){
                if(p.getProd_category().equalsIgnoreCase(pcategory)){
                    allProds.add(p);
                }
            }
        }
        if(allProds.isEmpty()){
            throw new ProductNotFoundException("Product not found with given category. Please check again!!!");
        }
        return allProds;
    }

    @Override
    public String deleteStore(int sid, String fullToken) {
        //fetch the vendor details first...
        //if user isn't vendor, throw UnauthorizeException...
        User u=userClient.findUser(fullToken).getBody();
        if(u==null){
            throw new UserNotFoundException("User not found with this token! Please provide valid token!!");
        }
        if(!u.getRole().equalsIgnoreCase("VENDOR")){
            throw new UnauthorizeException("You don't have the access to perform any operation related to store as your role isn't vendor!!!");
        }
        //fetch the store details using sid...
        Store s=srepo.findById(sid).orElseThrow(()->new StoreNotFoundException("Store not found with given id!!!"));

        //check vendor id matches with user id or not...
        //as we're storing uid as vendor id in those stores which is owned by that particular user/vendor...
        if(s.getVendor_id()!=u.getId()){
            throw new UnauthorizeException("You're not owner of this store so you can't remove it!!!");
        }
        else{
            srepo.delete(s);
        }
        return "Store deleted!!!";
    }

    @Override
    public String deleteProductFromStore(int sid, int pid, String fullToken) {
        //fetch the vendor details first...
        //if user isn't vendor, throw UnauthorizeException...
        User u=userClient.findUser(fullToken).getBody();
        if(u==null){
            throw new UserNotFoundException("User not found with this token! Please provide valid token!!");
        }
        if(!u.getRole().equalsIgnoreCase("VENDOR")){
            throw new UnauthorizeException("You don't have the access to perform any operation related to store as your role isn't vendor!!!");
        }
        //fetch the store details using sid...
        Store s=srepo.findById(sid).orElseThrow(()->new StoreNotFoundException("Store not found with given id!!!"));

        //check vendor id matches with user id or not...
        //as we're storing uid as vendor id in those stores which is owned by that particular user/vendor...
        if(s.getVendor_id()!=u.getId()){
            throw new UnauthorizeException("You're not owner of this store so you can't remove any product from it!!!");
        }
        else{
            //remove the product from the store...
            for(Product p:s.getAllProducts()){
                if(p.getProd_id()==pid){
                    s.getAllProducts().remove(p);
                    break;
                }
            }
            //after removing the product from product list of the store, save the store object again otherwise changes will not reflect...
            srepo.save(s);
        }
        return "Product deleted!!!";
    }

    @Override
    public Store updateStore(int sid, String sname, String fullToken) {
        //fetch the vendor details first...
        //if user isn't vendor, throw UnauthorizeException...
        User u=userClient.findUser(fullToken).getBody();
        if(u==null){
            throw new UserNotFoundException("User not found with this token! Please provide valid token!!");
        }
        if(!u.getRole().equalsIgnoreCase("VENDOR")){
            throw new UnauthorizeException("You don't have the access to perform any operation related to store as your role isn't vendor!!!");
        }
        //fetch the store details using sid...
        Store s=srepo.findById(sid).orElseThrow(()->new StoreNotFoundException("Store not found with given id!!!"));

        //check vendor id matches with user id or not...
        //as we're storing uid as vendor id in those stores which is owned by that particular user/vendor...
        if(s.getVendor_id()!=u.getId()){
            throw new UnauthorizeException("You're not owner of this store so you can't update store details!!!");
        }

        //update store details (updatable fields : store_name) and save that store...
        //store_id, vendor_id and products can't be changed...
        //products can be changed from different endpoint...
        s.setStore_name(sname);
        return srepo.save(s);
    }

    @Override
    public Store updateProductOfStore(int sid, int pid, ProductDto p, String fullToken) {
        //fetch the vendor details first...
        //if user isn't vendor, throw UnauthorizeException...
        User u=userClient.findUser(fullToken).getBody();
        if(u==null){
            throw new UserNotFoundException("User not found with this token! Please provide valid token!!");
        }
        if(!u.getRole().equalsIgnoreCase("VENDOR")){
            throw new UnauthorizeException("You don't have the access to perform any operation related to store as your role isn't vendor!!!");
        }
        //fetch the store details using sid...
        Store s=srepo.findById(sid).orElseThrow(()->new StoreNotFoundException("Store not found with given id!!!"));

        //check vendor id matches with user id or not...
        //as we're storing uid as vendor id in those stores which is owned by that particular user/vendor...
        if(s.getVendor_id()!=u.getId()){
            throw new UnauthorizeException("You're not owner of this store so you can't update store details!!!");
        }

        //variable to check if product found with given id or not...
        int found=0;

        //update details of that particular product...
        for(Product pr:s.getAllProducts()){
            if(pr.getProd_id()==pid){
                //after finding product, update details...
                found=1;
                //prod_id and store_id can't be updated...
                if(!p.getProd_category().isEmpty()){
                    pr.setProd_category(p.getProd_category());
                }
                if(!p.getProd_name().isEmpty()){
                    pr.setProd_name(p.getProd_name());
                }
                if(p.getProd_qty()!=0){
                    pr.setProd_qty(p.getProd_qty());
                }
                if(p.getProd_price()!=0){
                    pr.setProd_price(p.getProd_price());
                }
                if(p.getProd_dis_rate()!=0){
                    pr.setProd_dis_rate(p.getProd_dis_rate());
                }
                if(p.getProd_dis_price()!=0){
                    pr.setProd_dis_price(p.getProd_dis_price());
                }

                //after updating, break for loop...
                break;
            }
        }

        if(found==0){
            throw new ProductNotFoundException("Product not found with given id. Please check again!!!");
        }

        //after changing all details, save store...
        return srepo.save(s);
    }

    @Override
    public Product addProduct(int sid, ProductDto pdto, String fullToken) {
        //fetch the vendor details first...
        //if user isn't vendor, throw UnauthorizeException...
        User u=userClient.findUser(fullToken).getBody();
        if(u==null){
            throw new UserNotFoundException("User not found with this token! Please provide valid token!!");
        }
        if(!u.getRole().equalsIgnoreCase("VENDOR")){
            throw new UnauthorizeException("You don't have the access to perform any operation related to store as your role isn't vendor!!!");
        }
        //fetch the store details using sid...
        Store s=srepo.findById(sid).orElseThrow(()->new StoreNotFoundException("Store not found with given id!!!"));

        //check vendor id matches with user id or not...
        //as we're storing uid as vendor id in those stores which is owned by that particular user/vendor...
        if(s.getVendor_id()!=u.getId()){
            throw new UnauthorizeException("You're not owner of this store so you can't update store details!!!");
        }

        //create product from productdto...
        //product_id, store_id and other fields from productdto will be set here...
        Product p=new Product();
        //last item's id will be the size of product list...
        p.setProd_id(s.getAllProducts().size());
        p.setStore_id(sid);
        p.setProd_name(pdto.getProd_name());
        p.setProd_category(pdto.getProd_category());
        p.setProd_qty(pdto.getProd_qty());
        p.setProd_price(pdto.getProd_price());
        p.setProd_dis_rate(pdto.getProd_dis_rate());
        p.setProd_dis_price(pdto.getProd_dis_price());

        //after creating product, add it to the product list and save the store object to reflect changes...
        s.getAllProducts().add(p);
        srepo.save(s);
        //return created product object...
        return p;
    }

    @Override
    public ProductDto findProdByPidAndSid(int sid, int pid) {
        //find store by id...
        Store s=srepo.findById(sid).orElseThrow(()->new StoreNotFoundException("Store not found with given id!!!"));
        //find the particular product and convert it into productdto. then return it...
        ProductDto pdto=new ProductDto();
        for(Product p:s.getAllProducts()){
            if(p.getProd_id()==pid){
                pdto.setProd_category(p.getProd_category());
                pdto.setProd_name(p.getProd_name());
                pdto.setProd_qty(p.getProd_qty());
                pdto.setProd_price(p.getProd_price());
                pdto.setProd_dis_rate(p.getProd_dis_rate());
                pdto.setProd_dis_price(p.getProd_dis_price());
                break;
            }
        }
        //if product not found, throw exception...
        if(pdto==null){
            throw new ProductNotFoundException("Product not found with given id!!!");
        }
        return pdto;
    }
}
