package com.payment.feignClient;

import com.payment.dto.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//this interface will receive product details from StoreService...
@FeignClient(name = "STORE-SERVICE")
public interface ProductClient {
    //store service handler method to fetch product details. check StoreController...
    @GetMapping("/grabstore2/store/findprodid")
    public ResponseEntity<Product> getProdById(@RequestParam("sid") int sid, @RequestParam("pid") int pid);
}
