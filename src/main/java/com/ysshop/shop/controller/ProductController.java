package com.ysshop.shop.controller;

import com.ysshop.shop.dto.ProductDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seller") // 판매자가 직접 상품을 업로드
public class ProductController {
    @PostMapping("/new/products")
    public void upload(@Valid @RequestPart(value="dto") ProductDto productDto,
                       @NotEmpty @RequestPart(value="repImg") MultipartFile repImg,
                       @RequestPart(value="otherImg", required = false) List<MultipartFile> otherImg) {
        System.out.println(otherImg.size());
    }
}
