package com.ntu.edu.group5.ecommerce.service;

import java.util.List;

import com.ntu.edu.group5.ecommerce.entity.CategoryEnum;
import com.ntu.edu.group5.ecommerce.entity.Product;
import com.ntu.edu.group5.ecommerce.entity.ProductDTO;
import com.ntu.edu.group5.ecommerce.entity.ProductStatus;

public interface ProductService {

	public Product addProductToCatalog(String token, Product product);

	public Product getProductFromCatalogById(Long productId);

	public String deleteProductFromCatalog(Long productId);

	public Product updateProductInCatalog(Product product);

	public List<Product> getAllProductsInCatalog();

	public List<ProductDTO> getAllProductsOfSeller(Long sellerId);

	public List<ProductDTO> getProductsOfCategory(CategoryEnum catenum);

	public List<ProductDTO> getProductsOfStatus(ProductStatus status);

	public Product updateProductQuantityWithId(Long productId, ProductDTO prodDTO);

}
