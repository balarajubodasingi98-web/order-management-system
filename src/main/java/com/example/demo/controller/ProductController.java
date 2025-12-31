package com.example.demo.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;


@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {
	
//	private final ProductRepository repo;
	private final ProductService productService;
	
//	public ProductController(ProductRepository repo) {
//		this.repo=repo;
//	}
	
	public ProductController(ProductService productService) {
		
		this.productService = productService;
	}
	
	@PostMapping
	public Product create(@RequestBody Product product) {
		return productService.createProduct(product);
	}
	
//
//	@GetMapping
//	public List<Product> getAll() {
//		return productService.getAllProducts();
//				
//	}
	@GetMapping
	public List<Product> getProductsForUser() {
	    return productService.getActiveProducts();
	}
	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Product> getAllProductsForAdmin() {
	    return productService.getAllProducts();
	}

	@DeleteMapping("/{id}")
	public String disableProduct(@PathVariable Long id) {
	    productService.disableProduct(id);
	    return "Product disabled successfully";
	}

	
	
}
