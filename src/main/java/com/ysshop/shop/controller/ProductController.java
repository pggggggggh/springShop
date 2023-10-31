package com.ysshop.shop.controller;
import com.ysshop.shop.entity.User;
import com.ysshop.shop.service.ProductService;
import com.ysshop.shop.dto.ProductFormDto;
import com.ysshop.shop.dto.ProductSearchDto;
import com.ysshop.shop.entity.Product;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
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
    //코드 생략
    //상품관리자 조회
    @GetMapping("/api/admin/products")
    public ResponseEntity<Map<String, Object>> getItems(ProductSearchDto productSearchDto,
                                                        @RequestParam(value = "page", defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 3);
        Page<Product> products = productService.getAdminProductPage(productSearchDto, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("products", products.getContent());
        response.put("currentPage", products.getNumber());
        response.put("totalProducts", products.getTotalElements());
        response.put("totalPages", products.getTotalPages());
        response.put("productSearchDto", productSearchDto);
        response.put("maxPage", 5);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
