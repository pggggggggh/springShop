package com.ysshop.shop.entity;

import com.ysshop.shop.repository.ProductRepository;
import com.ysshop.shop.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("local")
public class ProductTest {
    @Autowired
    ProductRepository productRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("Auditing 테스트")
    @WithMockUser(username = "kkt", roles = "SELLER")
    public void auditingTest() {
        Product product = new Product();
        productRepository.save(product);

        em.flush();
        em.clear();

        Product findProduct = productRepository.findByProductId(product.getProductId());

        System.out.println("createdBy = " + findProduct.getCreatedBy());
        System.out.println("createTime = " + findProduct.getCreateTime());
    }

}
