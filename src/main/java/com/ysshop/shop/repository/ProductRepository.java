package com.ysshop.shop.repository;

import com.ysshop.shop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, QuerydslPredicateExecutor<Product>, ProductRepositoryCustom {
  List<Product> findByName(String name);

  @Query(value = "select i from Product i where i.additionalDescription like %:additionalDescription% order by i.price desc", nativeQuery = true)
  List<Product> findByAdditionalDescription(@Param("additionalDescription") String additionalDescription);
}
