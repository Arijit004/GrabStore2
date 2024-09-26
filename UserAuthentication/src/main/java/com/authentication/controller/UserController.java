package com.authentication.controller;

import com.authentication.dto.LoginCredentials;
import com.authentication.entity.User;
import com.authentication.exception.UnauthorizedException;
import com.authentication.exception.UserAlreadyExistsException;
import com.authentication.exception.UserNotFoundException;
import com.authentication.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//user controller...
@RestController
@RequestMapping("/grabstore2/user")
public class UserController {
    //service layer...
    @Autowired
    private UserService userv;

    //register handler...
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User u){
        try {
            return new ResponseEntity<>(userv.register(u), HttpStatus.CREATED);
        }
        catch (UserAlreadyExistsException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    //login user...
    @GetMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginCredentials lc){
        try {
            return new ResponseEntity<>(userv.login(lc), HttpStatus.FOUND);
        }
        catch (UserNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //update own account with the help of token...
    //take token from "authorization" header...
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody User u, @RequestHeader("Authorization") String fullToken){
        try{
            System.out.println("Token : "+fullToken);
            return new ResponseEntity<>(userv.update(fullToken, u), HttpStatus.OK);
        }
        catch (UserNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //delete own account with the help of token...
    //take token from "authorization" header...
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String fullToken){
        try{
            System.out.println("Token : "+fullToken);
            return new ResponseEntity<>(userv.delete(fullToken), HttpStatus.OK);
        }
        catch (UserNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //view own account with the help of token...
    @GetMapping("/find")
    public ResponseEntity<?> findUser(@RequestHeader("Authorization") String fullToken){
        try{
            System.out.println("Token : "+fullToken);
            return new ResponseEntity<>(userv.findSelf(fullToken), HttpStatus.OK);
        }
        catch (UserNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //view all user accounts. only available for admin...
    @GetMapping("/findall")
    public ResponseEntity<?> findAll(@RequestHeader("Authorization") String fullToken){
        try{
            System.out.println("Token : "+fullToken);
            return new ResponseEntity<>(userv.findAll(fullToken), HttpStatus.OK);
        }
        catch (UserNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (UnauthorizedException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

}
