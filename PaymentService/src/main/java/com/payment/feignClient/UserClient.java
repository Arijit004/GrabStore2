package com.payment.feignClient;

import com.payment.dto.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

//this interface will receive user details from UserAuthentication...
@FeignClient(name="USER-SERVICE")
public interface UserClient {
    //user handler method to fetch user details (check UserController)...
    @GetMapping("/grabstore2/user/find")
    public ResponseEntity<User> findUser(@RequestHeader("Authorization") String fullToken);
}
