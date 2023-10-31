package com.ysshop.shop.service;

import com.ysshop.shop.entity.ProductImg;
import com.ysshop.shop.repository.ProductImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductImgService {
    @Value("${productImgLocation}")
    private String productImgLocation;

    private final ProductImgRepository productImgRepository;
    private final FileService fileService;

    public void saveProductImg(ProductImg productImg, MultipartFile imgFile) throws Exception{
        String oriImgName = imgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        if (!StringUtils.isEmpty(oriImgName)) {
            imgName = fileService.uploadFile(productImgLocation, oriImgName,
                    imgFile.getBytes());
            imgUrl = "/images/item" + imgName;
        }

        productImg.updateProductImg(oriImgName, imgName, imgUrl);
        productImgRepository.save(productImg);
    }
}
