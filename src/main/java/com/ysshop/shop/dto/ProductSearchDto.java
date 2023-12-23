package com.ysshop.shop.dto;
import com.ysshop.shop.constant.BodyType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductSearchDto {
    private String searchDateType;
    private Boolean searchIsSoldOut;        // 상품의 판매상태를 기준으로 상품 데이터 조회
    private BodyType searchBodyType;              // 상품의 카테고리를 기준으로 상품 데이터 조회
    private String searchBy;                        // 상품 조회시 어떤 유형으로 조회할지 선택
    private String searchQuery = "";                // 조회할 검색어 저장할 변수
    private BodyType searchBodyQuery;
}
