package com.ntu.edu.group5.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ntu.edu.group5.ecommerce.exception.CategoryNotFoundException;
import com.ntu.edu.group5.ecommerce.exception.ProductNotFoundException;
import com.ntu.edu.group5.ecommerce.entity.CategoryEnum;
import com.ntu.edu.group5.ecommerce.entity.Product;
import com.ntu.edu.group5.ecommerce.entity.ProductDTO;
import com.ntu.edu.group5.ecommerce.entity.ProductStatus;
import com.ntu.edu.group5.ecommerce.entity.Seller;
import com.ntu.edu.group5.ecommerce.repository.ProductDao;
import com.ntu.edu.group5.ecommerce.repository.SellerDao;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao prodDao;

    @Autowired
    private SellerService sService;

    @Autowired
    private SellerDao sDao;

    @Override
    public Product addProductToCatalog(String token, Product product) {
        Seller seller1 = sService.getLoggedInSellerDetails(token);
        product.setSeller(seller1);

        // Save the product to the database
        Product addedProduct = prodDao.save(product);

        // Update the seller's product list
        Seller existingSeller = sService.getSellerByContactNo(seller1.getContactNo(), token);
        existingSeller.getProduct().add(addedProduct);
        sDao.save(existingSeller);

        return addedProduct;
    }

    @Override
    public Product getProductFromCatalogById(Long id) throws ProductNotFoundException {
        Optional<Product> opt = prodDao.findById(id);
        return opt.orElseThrow(() -> new ProductNotFoundException("Product not found with given id"));
    }

    @Override
    public String deleteProductFromCatalog(Long id) throws ProductNotFoundException {
        Optional<Product> opt = prodDao.findById(id);

        if (opt.isPresent()) {
            Product prod = opt.get();
            prodDao.delete(prod);
            return "Product deleted from catalog";
        } else {
            throw new ProductNotFoundException("Product not found with given id");
        }
    }

    @Override
    public Product updateProductInCatalog(Product prod) throws ProductNotFoundException {
        Optional<Product> opt = prodDao.findById(prod.getProductId());

        if (opt.isPresent()) {
            return prodDao.save(prod);
        } else {
            throw new ProductNotFoundException("Product not found with given id");
        }
    }

    @Override
    public List<Product> getAllProductsInCatalog() {
        List<Product> list = prodDao.findAll();
        if (!list.isEmpty()) {
            return list;
        } else {
            throw new ProductNotFoundException("No products in catalog");
        }
    }

    @Override
    public List<ProductDTO> getProductsOfCategory(CategoryEnum catenum) {
        List<ProductDTO> list = prodDao.getAllProductsInACategory(catenum);
        if (!list.isEmpty()) {
            return list;
        } else {
            throw new CategoryNotFoundException("No products found with category: " + catenum);
        }
    }

    @Override
    public List<ProductDTO> getProductsOfStatus(ProductStatus status) {
        List<ProductDTO> list = prodDao.getProductsWithStatus(status);
        if (!list.isEmpty()) {
            return list;
        } else {
            throw new ProductNotFoundException("No products found with given status: " + status);
        }
    }

    @Override
    public Product updateProductQuantityWithId(Long id, ProductDTO prodDto) {
        Optional<Product> opt = prodDao.findById(id);

        if (opt.isPresent()) {
            Product prod = opt.get();
            prod.setQuantity(prod.getQuantity() + prodDto.getQuantity());
            if (prod.getQuantity() > 0) {
                prod.setStatus(ProductStatus.AVAILABLE);
            }
            return prodDao.save(prod);
        } else {
            throw new ProductNotFoundException("No product found with this Id");
        }
    }

    @Override
    public List<ProductDTO> getAllProductsOfSeller(Long id) {
        List<ProductDTO> list = prodDao.getProductsOfASeller(id);

        if (!list.isEmpty()) {
            return list;
        } else {
            throw new ProductNotFoundException("No products with SellerId: " + id);
        }
    }
}
