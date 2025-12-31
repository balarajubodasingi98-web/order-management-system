package com.example.demo.controller;

import java.util.Date;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.OrderRequest;
import com.example.demo.entity.Order;
import com.example.demo.repository.OrderReppository;
import com.example.demo.service.OrderService;


@RestController
@RequestMapping("/api/orders")
@CrossOrigin
public class OrderController {
	
//	private final OrderReppository repo;
//
//	public OrderController(OrderReppository repo) {
//		
//		this.repo = repo;
//	}
//	
//	@PostMapping
//	public Order create(@RequestBody Order order) {
//		order.setOrderDate(new Date());
//		order.setStatus("CREATED");
//		return repo.save(order);
//	}
//	
//	@GetMapping
//	public List<Order> getAll(){
//		return repo.findAll();
//	}
	
	
	private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Order placeOrder(@RequestBody OrderRequest request,
                            Authentication authentication) {

        String userEmail = authentication.getName();
        return orderService.placeOrder(request, userEmail);
    }
    
    @GetMapping("/my")
    public List<Order> myOrders(Authentication authentication) {
    	 System.out.println("LOGGED-IN USER = " + authentication.getName());
        return orderService.getMyOrders(authentication.getName());
//    	return orderService.getAllOrders();
    }
    
    
 // ================= ADMIN =================

    // 🔥 View ALL orders (ADMIN)
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Order> getAllOrdersForAdmin() {
        return orderService.getAllOrders();
    }

    // 🔥 Update order status (ADMIN)
    @PutMapping("/admin/{orderId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public Order updateStatus(@PathVariable Long orderId,
                              @RequestParam String status) {
        return orderService.updateOrderStatus(orderId, status);
    }
}
