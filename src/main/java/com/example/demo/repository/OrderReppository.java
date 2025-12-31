package com.example.demo.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Order;

public interface OrderReppository extends JpaRepository<Order,Long>{
	List<Order> findByUserEmail(String email) ;
		// TODO Auto-generated method stub
	

	
}
