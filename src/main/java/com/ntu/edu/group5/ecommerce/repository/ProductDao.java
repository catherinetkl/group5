package com.ntu.edu.group5.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ntu.edu.group5.ecommerce.entity.CategoryEnum;
import com.ntu.edu.group5.ecommerce.entity.Product;
import com.ntu.edu.group5.ecommerce.entity.ProductDTO;
import com.ntu.edu.group5.ecommerce.entity.ProductStatus;

@Repository
public interface ProductDao extends JpaRepository<Product, Long> {

    @Query("SELECT new com.ntu.edu.group5.ecommerce.entity.ProductDTO(p.productName, p.manufacturer, p.price, p.quantity) "
            + "FROM Product p WHERE p.category = :catenum")
    public List<ProductDTO> getAllProductsInACategory(@Param("catenum") CategoryEnum catenum);

    @Query("SELECT new com.ntu.edu.group5.ecommerce.entity.ProductDTO(p.productName,p.manufacturer,p.price,p.quantity) "
            + "FROM Product p where p.status=:status")
    public List<ProductDTO> getProductsWithStatus(@Param("status") ProductStatus status);

    @Query("SELECT new com.ntu.edu.group5.ecommerce.entity.ProductDTO(p.productName,p.manufacturer,p.price,p.quantity) "
            + "FROM Product p where p.seller.sellerId=:id")
    public List<ProductDTO> getProductsOfASeller(@Param("id") Long id);
}
