package com.example.demo.service;
import java.util.List;
import com.example.demo.dto.OrderRequest;
import com.example.demo.entity.Order;

public interface OrderService {
	
	Order placeOrder(OrderRequest request,String userEmail);
	List<Order> getMyOrders(String email);
	Order updateOrderStatus(Long orderId, String status);
	void deleteOrder(Long orderId);

    List<Order> getAllOrders();
}
