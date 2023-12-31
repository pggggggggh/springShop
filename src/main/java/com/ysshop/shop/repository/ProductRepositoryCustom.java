package com.ysshop.shop.repository;
import com.ysshop.shop.dto.MainProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.ysshop.shop.dto.ProductSearchDto;
import com.ysshop.shop.entity.Product;
public interface ProductRepositoryCustom {
    Page<Product> getAdminProductPage(ProductSearchDto productSearchDto, Pageable pageable);

    Page<MainProductDto> getProductPage(ProductSearchDto productSearchDto, Pageable pageable);
}
