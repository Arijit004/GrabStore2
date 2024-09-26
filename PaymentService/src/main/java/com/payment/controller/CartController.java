package com.payment.controller;

import com.payment.exception.CartAlreadyExistsException;
import com.payment.exception.CartNotFoundException;
import com.payment.exception.ProductNotFoundException;
import com.payment.exception.UserNotFoundException;
import com.payment.service.interfaces.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//cart controller...
@RestController
@RequestMapping("/grabstore2/cart")
public class CartController {
    //service layer...
    @Autowired
    private CartService cserv;

    //create cart...
    @PostMapping("/create")
    public ResponseEntity<?> createCart(@RequestParam("cname") String cname, @RequestHeader("Authorization") String fullToken){
        try{
            return new ResponseEntity<>(cserv.createCart(cname, fullToken), HttpStatus.CREATED);
        }
        catch (UserNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (CartAlreadyExistsException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    //delete cart...
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCart(@RequestHeader("Authorization") String fullToken){
        try{
            return new ResponseEntity<>(cserv.deleteCart(fullToken), HttpStatus.OK);
        }
        catch (UserNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (CartNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //update cart...
    @PutMapping("/update/{cname}")
    public ResponseEntity<?> updateCart(@PathVariable("cname") String cname, @RequestHeader("Authorization") String fullToken){
        try{
            return new ResponseEntity<>(cserv.updateCart(cname, fullToken), HttpStatus.OK);
        }
        catch (UserNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (CartNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //get cart...
    @GetMapping("/get")
    public ResponseEntity<?> getCart(@RequestHeader("Authorization") String fullToken){
        try{
            return new ResponseEntity<>(cserv.getCart(fullToken), HttpStatus.FOUND);
        }
        catch (UserNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (CartNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //add product...
    @PostMapping("/addprod")
    public ResponseEntity<?> addProd(@RequestParam("sid") int sid, @RequestParam("pid") int pid, @RequestHeader("Authorization") String fullToken){
        try{
            return new ResponseEntity<>(cserv.addProduct(sid, pid, fullToken), HttpStatus.OK);
        }
        catch (ProductNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (UserNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (CartNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //delete product...
    @DeleteMapping("/delprod/{sid}/{pid}")
    public ResponseEntity<?> delProd(@PathVariable int sid, @PathVariable int pid, @RequestHeader("Authorization") String fullToken){
        try{
            return new ResponseEntity<>(cserv.deleteProduct(sid, pid, fullToken), HttpStatus.OK);
        }
        catch (ProductNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (UserNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (CartNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //buy products / invoice...
    @GetMapping("/buycart")
    public ResponseEntity<?> buyCart(@RequestHeader("Authorization") String fullToken){
        try{
            return new ResponseEntity<>(cserv.buyCart(fullToken), HttpStatus.OK);
        }
        catch (ProductNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (UserNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (CartNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
