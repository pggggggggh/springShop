package com.ysshop.shop.service;
import com.ysshop.shop.repository.ProductRepository;
import com.ysshop.shop.repository.ProductImgRepository;
import com.ysshop.shop.dto.ProductDto;
import com.ysshop.shop.entity.Product;
import com.ysshop.shop.entity.ProductImg;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;
import java.util.List;


public class ProductService {
@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ProductRepository productRepository;
    private final ProductImgService productImgService;
    private final ProductImgRepository productImgRepository;

    public Long saveItem(ProductDto productDto, List<MultipartFile> productImgFileList) throws Exception{

        //상품 등록
        Product product = productDto.createItem();
        productRepository.save(product);
        //이미지 등록



        return product.getId();
}
