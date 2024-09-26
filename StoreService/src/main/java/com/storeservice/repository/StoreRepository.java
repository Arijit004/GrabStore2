package com.storeservice.repository;

import com.storeservice.entity.Store;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends MongoRepository<Store, Integer> {

    //custom query method...
    //1. fetch all stores and products from that store based on category...
    @Query("{'allProducts.prod_category' : ?0}")
    public List<Store> findProdByCat(String prodcat);
}
