package com.ysshop.shop.repository;

import com.ysshop.shop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, QuerydslPredicateExecutor<Product>  {
  List<Product> findByName(String name);

    @Query(value = "select i from Product i where i.additionalDescription like %:additionalDescription% order by i.price desc", nativeQuery = true)
    List<Product> findByadditionalDescription(@Param("additionalDescription") String additionalDescription);
}
