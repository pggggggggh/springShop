package com.ysshop.shop.service;

import com.ysshop.shop.dto.*;
import com.ysshop.shop.entity.Product;
import com.ysshop.shop.entity.ProductImg;
import com.ysshop.shop.repository.ProductImgRepository;
import com.ysshop.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductImgRepository productImgRepository;

    public Long saveProduct(ProductFormDto productFormDto, List<MultipartFile> productImgFileList) throws Exception {

        //상품 등록
        Product product = productFormDto.createProduct();
        productRepository.save(product);
        //이미지 등록

        return product.getId();
    }
}
