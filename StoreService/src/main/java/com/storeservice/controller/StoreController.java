package com.storeservice.controller;

import com.storeservice.dto.ProductDto;
import com.storeservice.entity.Store;
import com.storeservice.exception.*;
import com.storeservice.service.interfaces.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.plugins.tiff.GeoTIFFTagSet;

//store controller...
@RestController
@RequestMapping("/grabstore2/store")
public class StoreController {
    //service layer...
    @Autowired
    private StoreService sserv;

    //handler methods...
    //1. add store...
    @PostMapping("/add")
    public ResponseEntity<?> addStore(@RequestBody Store s, @RequestHeader("Authorization") String fullToken){
        try{
            System.out.println("Token : "+fullToken);
            return new ResponseEntity<>(sserv.addStore(s, fullToken), HttpStatus.CREATED);
        }
        catch (UserNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (UnauthorizeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        catch (StoreAlreadyExistsException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    //2. find store...
    //endpoint : .../grabstore2/store/find?storeId=1
    @GetMapping("/find")
    public ResponseEntity<?> findStoreById(@RequestParam("storeId") int storeId){
        try{
            return new ResponseEntity<>(sserv.findStore(storeId), HttpStatus.FOUND);
        }
        catch (StoreNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //3. find all stores...
    //endpoint : .../grabstore2/store/findall
    @GetMapping("/findall")
    public ResponseEntity<?> findAllStores(){
        return new ResponseEntity<>(sserv.findAllStores(), HttpStatus.OK);
    }

    //4. find all stores of a vendor...
    @GetMapping("/findbyvendor")
    public ResponseEntity<?> findAllStoresByVendor(@RequestHeader("Authorization") String fullToken){
        try{
            System.out.println("Token : "+fullToken);
            return new ResponseEntity<>(sserv.findAllStoresByVendor(fullToken), HttpStatus.OK);
        }
        catch (UserNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (UnauthorizeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    //5. find all products by product name...
    //endpoint : .../grabstore2/store/findname?pname="..."
    @GetMapping("/findname")
    public ResponseEntity<?> findProdByName(@RequestParam("pname") String pname){
        try {
            return new ResponseEntity<>(sserv.findAllProductsByName(pname),HttpStatus.OK);
        }
        catch (ProductNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //6. get all products from a particular store...
    @GetMapping("/findprods")
    public ResponseEntity<?> findProdsFromStore(@RequestParam("sid") int sid){
        try {
            return new ResponseEntity<>(sserv.findAllProductsByStore(sid), HttpStatus.OK);
        }
        catch (StoreNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //7. get all products based on category...
    @GetMapping("/findprodcat")
    public ResponseEntity<?> findProdsByCat(@RequestParam("prodcat") String prodcat){
        try {
            return new ResponseEntity<>(sserv.findAllProductsByCategory(prodcat), HttpStatus.OK);
        }
        catch (ProductNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //8. delete store...
    //endpoint : .../grabstore2/store/deletestore?sid=1
    @DeleteMapping("/deletestore")
    public ResponseEntity<?> deleteStore(@RequestParam("sid") int sid, @RequestHeader("Authorization") String fullToken){
        try{
            return new ResponseEntity<>(sserv.deleteStore(sid, fullToken), HttpStatus.OK);
        }
        catch (UserNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (UnauthorizeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        catch (StoreNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //9. delete product from a particular store...
    //endpoint : .../grabstore2/store/deleteprod?sid=1&pid=1
    @DeleteMapping("/deleteprod")
    public ResponseEntity<?> deleteProductFromStore(@RequestParam("sid") int sid, @RequestParam("pid") int pid, @RequestHeader("Authorization") String fullToken){
        try{
            return new ResponseEntity<>(sserv.deleteProductFromStore(sid, pid, fullToken), HttpStatus.OK);
        }
        catch (UserNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (UnauthorizeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        catch (StoreNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //10. update store details...
    //vendor is able to update store name only, so that we don't need any dto to take data. we'll take it using @PathVariable...
    @PutMapping("/updatestore/{sid}/{sname}")
    public ResponseEntity<?> updateStore(@PathVariable int sid, @PathVariable String sname, @RequestHeader("Authorization") String fullToken){
        try{
            return new ResponseEntity<>(sserv.updateStore(sid, sname, fullToken), HttpStatus.OK);
        }
        catch (UserNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (UnauthorizeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        catch (StoreNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //11. update product details...
    @PutMapping("/updateprod/{sid}/{pid}")
    public ResponseEntity<?> updateprod(@PathVariable("sid") int sid, @PathVariable("pid") int pid, @RequestBody ProductDto p, @RequestHeader("Authorization") String fullToken){
        try{
            return new ResponseEntity<>(sserv.updateProductOfStore(sid, pid, p, fullToken), HttpStatus.OK);
        }
        catch (UserNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (UnauthorizeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        catch (StoreNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (ProductNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //12. add product to a particular store...
    //endpoint : .../grabstore2/store/addprod?sid=1
    @PostMapping("/addprod")
    public ResponseEntity<?> addProd(@RequestParam("sid") int sid, @RequestBody ProductDto pdto, @RequestHeader("Authorization") String fullToken){
        try{
            return new ResponseEntity<>(sserv.addProduct(sid, pdto, fullToken), HttpStatus.CREATED);
        }
        catch (UserNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (UnauthorizeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        catch (StoreNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //13. find product by sid and pid...
    @GetMapping("/findprodid")
    public ResponseEntity<?> getProdById(@RequestParam("sid") int sid, @RequestParam("pid") int pid){
        try {
            return new ResponseEntity<>(sserv.findProdByPidAndSid(sid, pid), HttpStatus.OK);
        }
        catch (StoreNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (ProductNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
