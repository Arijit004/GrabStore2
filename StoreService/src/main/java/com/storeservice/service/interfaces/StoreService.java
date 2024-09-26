package com.storeservice.service.interfaces;

import com.storeservice.dto.ProductDto;
import com.storeservice.entity.Product;
import com.storeservice.entity.Store;
import org.springframework.stereotype.Service;

import java.util.List;

//store service layer...
@Service
public interface StoreService {
    //all required methods...
    //1. add store (for vendor only)...
    public Store addStore(Store s, String fullToken);

    //2. get particular store...
    public Store findStore(int sid);

    //3. get all stores...
    public List<Store> findAllStores();

    //4. find all stores owned by a particular vendor (for vendor only)...
    public List<Store> findAllStoresByVendor(String fullToken);

    //5. get all products by name from different stores...
    public List<Product> findAllProductsByName(String pname);

    //6. get all products of a particular store...
    public List<Product> findAllProductsByStore(int sid);

    //7. get all products based on category from different store...
    public List<Product> findAllProductsByCategory(String pcategory);

    //8. delete store (for vendor only)...
    public String deleteStore(int sid, String fullToken);

    //9. delete products from particular store (for vendor only)...
    public String deleteProductFromStore(int sid, int pid, String fullToken);

    //10. update store details (for vendor only)...
    public Store updateStore(int sid, String sname, String fullToken);

    //11. update product from a particular store (for vendor only)...
    public Store updateProductOfStore(int sid, int pid, ProductDto p, String fullToken);

    //12. add product to a particular store (for vendor only)...
    public Product addProduct(int sid, ProductDto pdto, String fullToken);

    //13. find particular product using sid and pid...
    public ProductDto findProdByPidAndSid(int sid, int pid);

}
