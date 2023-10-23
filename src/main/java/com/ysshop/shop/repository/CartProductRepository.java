package com.ysshop.shop.repository;
import com.ysshop.shop.dto.CartDetailDto;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ysshop.shop.entity.CartProduct;
import org.springframework.data.jpa.repository.Query;
import java.util.List;



public interface CartProductRepository extends JpaRepository<CartProduct, Long>{
    CartProduct findByCartIdAndProductId(Long cartId, Long productId);
    @Query("select new com.ysshop.shop.dto.CartDetailDto(ci.id, i.name, i.price, ci.count) " +
            "from CartProduct ci " +
            "join ci.product i " +
            "where ci.cart.id = :cartId " +
            "order by ci.createTime desc"
    )
    List<CartDetailDto> findCartDetailDtoList(Long cartId);
}
