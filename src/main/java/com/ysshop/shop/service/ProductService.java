package com.ysshop.shop.service;

import com.ysshop.shop.dto.*;
import com.ysshop.shop.entity.Product;
import com.ysshop.shop.entity.ProductImg;
import com.ysshop.shop.repository.ProductImgRepository;
import com.ysshop.shop.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductImgService productImgService;
    private final ProductImgRepository productImgRepository;

    public Long saveProduct(ProductFormDto productFormDto, List<MultipartFile> imgList) throws Exception {
        //상품 등록
        Product product = productFormDto.createProduct();
        productRepository.save(product);
        //이미지 등록

        for (int i=0;i<imgList.size();i++) {
            ProductImg productImg = new ProductImg();
            productImg.setProduct(product);

            if (i == 0) productImg.setIsRepImg(true);
            else productImg.setIsRepImg(false);

            productImgService.saveProductImg(productImg, imgList.get(i));
        }

        return product.getId();
    }
    // 상품조회
    @Transactional(readOnly = true)
    public Page<Product> getAdminProductPage(ProductSearchDto productSearchDto, Pageable pageable){
        return productRepository.getAdminProductPage(productSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public Page<MainProductDto> getProductPage(ProductSearchDto productSearchDto, Pageable pageable){
        return productRepository.getProductPage(productSearchDto, pageable);
    }

    public ProductFormDto getProductDetail(Long productId) {
        List<ProductImg> productImgList = productImgRepository.findByProductIdOrderByIdAsc(productId);
        List<ProductImgDto> productImgDtoList = new ArrayList<>();
        for (ProductImg productImg : productImgList) {
            ProductImgDto productImgDto = ProductImgDto.of(productImg);
            productImgDtoList.add(productImgDto);
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(EntityNotFoundException::new);
        ProductFormDto productFormDto = ProductFormDto.of(product);
        productFormDto.setProductImgDtoList(productImgDtoList);

        return productFormDto;
    }
}
