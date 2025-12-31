package com.example.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.service.OrderService;

import com.example.demo.dto.OrderItemRequest;
import com.example.demo.dto.OrderRequest;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.repository.OrderReppository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;

@Service
public class OrderServiceImpl implements OrderService{
	
	private final OrderReppository orderReppository;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	
	

	public OrderServiceImpl(OrderReppository orderReppository, UserRepository userRepository,
			ProductRepository productRepository) {
		this.orderReppository = orderReppository;
		this.userRepository = userRepository;
		this.productRepository = productRepository;
	}


	
	public Order placeOrder(OrderRequest request, String userEmail) {
		User user=userRepository.findByEmail(userEmail)
				.orElseThrow(()->new RuntimeException("user not found"));
		
		
		Order order=new Order();
		order.setOrderDate(new Date());
		order.setStatus("created");
		order.setUser(user);
		
		
		double total=0;
		List<OrderItem> orderItems=new ArrayList<>();
		for(OrderItemRequest itemReq:request.items) {
			Product product=productRepository.findById(itemReq.productId)
					.orElseThrow(()-> new RuntimeException("Product not found"));
			if (!product.getActive()) {
			    throw new RuntimeException("Product is disabled");
			}
			product.setStock(product.getStock()-itemReq.quantity);
			
			OrderItem item=new OrderItem();
			item.setProduct(product);
			item.setQuantity(itemReq.quantity);
			item.setPrice(product.getPrice()*itemReq.quantity);
			
			total+=item.getPrice();
			orderItems.add(item);
		
		}
		order.settotalAmount(total);
		order.setItems(orderItems);
		return orderReppository.save(order);
	}



	@Override
	public List<Order> getMyOrders(String email) {
		// TODO Auto-generated method stub
		return orderReppository.findByUserEmail(email);
	}



	@Override
	public Order updateOrderStatus(Long orderId, String status) {
		// TODO Auto-generated method stub
		Order order=orderReppository.findById(orderId)
				.orElseThrow(()-> new RuntimeException ("order not found"));
		order.setStatus(status);
		return orderReppository.save(order);
	}



	@Override
	public List<Order> getAllOrders() {
		// TODO Auto-generated method stub
		return orderReppository.findAll();
	}



	public void deleteOrder(Long orderId) {
		// TODO Auto-generated method stub
		orderReppository.deleteById(orderId);
		
	}
	
	
	
	

	
}
