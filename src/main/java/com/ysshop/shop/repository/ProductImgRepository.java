package com.ysshop.shop.repository;

import com.ysshop.shop.entity.ProductImg;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ProductImgRepository extends JpaRepository<ProductImg, Long> {
    List<ProductImg> findByProductIdOrderByIdAsc(Long productId);
    ProductImg findByProductIdAndIsRepImg(Long productId, Boolean isRepImg);
}
