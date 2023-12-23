package com.ysshop.shop.repository;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ysshop.shop.constant.BodyType;
import com.ysshop.shop.dto.MainProductDto;
import com.ysshop.shop.dto.ProductSearchDto;
import com.ysshop.shop.dto.QMainProductDto;
import com.ysshop.shop.entity.Product;
import com.ysshop.shop.entity.QProduct;
import com.ysshop.shop.entity.QProductImg;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import org.thymeleaf.util.StringUtils;
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom{
    private JPAQueryFactory queryFactory;
    public ProductRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
    // 3.

    private BooleanExpression searchIsSoldOutEq(Boolean searchIsSoldOut) {
        // 4.
        return searchIsSoldOut == null ? null : QProduct.product.isSoldOut.eq(searchIsSoldOut);
        // 4.
    }

    private BooleanExpression regDtsAfter(String searchDateType) {
        // 5.
        LocalDateTime dateTime = LocalDateTime.now();

        if(StringUtils.equals("all", searchDateType) || searchDateType == null) {
            return null;
        } else if (StringUtils.equals("1d", searchDateType)) {
            dateTime = dateTime.minusDays(1);
        } else if (StringUtils.equals("1w", searchDateType)) {
            dateTime = dateTime.minusWeeks(1);
        } else if (StringUtils.equals("1m", searchDateType)) {
            dateTime = dateTime.minusMonths(1);
        } else if (StringUtils.equals("6m", searchDateType)) {
            dateTime = dateTime.minusMonths(6);
        }

        return QProduct.product.createTime.after(dateTime);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery) {
        // 6.
        if(StringUtils.equals("name", searchBy)) {
            return QProduct.product.name.like("%" + searchQuery + "%");
        } else if (StringUtils.equals("countryOfManufacture", searchBy)) {
            return QProduct.product.countryOfManufacture.like("%" + searchQuery + "%");
        }

        return null;
    }
    private BooleanExpression searchByBodyType(BodyType searchBodyType, BodyType searchBodyQuery){

        if(searchBodyType == BodyType.RECTANGLE){
            return QProduct.product.bodyType.eq(searchBodyQuery);
        } else if(searchBodyType == BodyType.TRIANGLE){
            return QProduct.product.bodyType.eq(searchBodyQuery);
        } else if(searchBodyType == BodyType.HOURGLASS) {
            return QProduct.product.bodyType.eq(searchBodyQuery);
        } else if(searchBodyType == BodyType.INVERTED_TRIANGLE) {
            return QProduct.product.bodyType.eq(searchBodyQuery);
        } else if(searchBodyType == BodyType.ROUND) {
            return QProduct.product.bodyType.eq(searchBodyQuery);
        }
        return null;
    }

    @Override
    public Page<Product> getAdminProductPage(ProductSearchDto productSearchDto, Pageable pageable) {

        QueryResults<Product> results = queryFactory        // 7.
                .selectFrom(QProduct.product)
                .where(regDtsAfter(productSearchDto.getSearchDateType()),
                        searchIsSoldOutEq(productSearchDto.getSearchIsSoldOut()),
                        searchByLike(productSearchDto.getSearchBy(),
                                productSearchDto.getSearchQuery()))
                .orderBy(QProduct.product.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Product> content = results.getResults();
        Long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<MainProductDto> getProductPage(ProductSearchDto productSearchDto, Pageable pageable) {
        QProduct product = QProduct.product;
        QProductImg productImg = QProductImg.productImg;

        QueryResults<MainProductDto> results = queryFactory.select(
                        new QMainProductDto(
                                product.id,
                                product.name,
                                productImg.imgUrl,
                                product.price)
                )
                .from(productImg)
                .join(productImg.product, product)
                .where(productImg.isRepImg.eq(true))
                .where(searchByLike("name", productSearchDto.getSearchQuery()))
                .orderBy(product.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<MainProductDto> content = results.getResults();
        Long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }
}
