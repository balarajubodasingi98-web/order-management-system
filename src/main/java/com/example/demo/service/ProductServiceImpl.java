package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{
	private final ProductRepository productRepository;

	public ProductServiceImpl(ProductRepository productRepository) {
		
		this.productRepository = productRepository;
	}
	
	@Override
	public Product createProduct(Product product) {
		return productRepository.save(product);
	}
	
	@Override
	public List<Product> getAllProducts(){
		return productRepository.findAll();
	}

	
	public void disableProduct(Long productId) {
		// TODO Auto-generated method stub
		Product product = productRepository.findById(productId)
		        .orElseThrow(() -> new RuntimeException("Product not found"));

		product.setActive(false);   // 🔥 SOFT DELETE
		productRepository.save(product);
		
	}

	public List<Product> getActiveProducts() {
		// TODO Auto-generated method stub
		return productRepository.findByActiveTrue();
	}
	 public List<Product> getProductsForAdmin() {
	        return productRepository.findAll();
	    }
	

}
