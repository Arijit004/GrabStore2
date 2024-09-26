package com.payment.repository;

import com.payment.entity.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//repository layer...
@Repository
public interface CartRepository extends MongoRepository<Cart, Integer> {
    //custom query methods...
    //1. find cart by user id...
    @Query("{userId:?0}")
    public Optional<Cart> findByUserId(int userId);
}
