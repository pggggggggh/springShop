package com.ysshop.shop.controller;
import com.ysshop.shop.dto.MainProductDto;
import com.ysshop.shop.entity.User;
import com.ysshop.shop.service.ProductService;
import com.ysshop.shop.dto.ProductFormDto;
import com.ysshop.shop.dto.ProductSearchDto;
import com.ysshop.shop.entity.Product;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @PostMapping("/products")
    public ResponseEntity<?> upload(@Valid @RequestPart(value="dto") ProductFormDto productFormDto,
                                    @RequestPart(value="images", required = true) List<MultipartFile> imgList) {
        try {
            productService.saveProduct(productFormDto, imgList);
        } catch (Exception e) {
            System.out.println(e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 그냥 상품 조회
    @GetMapping({"/products", "/products/{page}"})
    public ResponseEntity getProducts(@RequestBody ProductSearchDto productSearchDto, @PathVariable("page") Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
        Page<MainProductDto> products = productService.getProductPage(productSearchDto, pageable);

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    //상품관리자 조회
    @GetMapping({"/admin/products", "/admin/products/{page}"})
    public ResponseEntity getAdminProducts(@RequestBody ProductSearchDto productSearchDto, @PathVariable("page") Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
        Page<Product> products = productService.getAdminProductPage(productSearchDto, pageable);
        System.out.println(products.getContent());

        return new ResponseEntity<>(products, HttpStatus.OK);
    }


}
