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
@RequestMapping("/products")
public class ProductController {
    @PostMapping("/")
    public void upload(@Valid @RequestPart(value="dto") ProductDto productDto,
                       @NotEmpty @RequestPart(value="repImg") MultipartFile repImg,
                       @RequestPart(value="otherImg") List<MultipartFile> otherImg) {

    }
}
