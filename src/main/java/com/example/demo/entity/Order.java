package com.example.demo.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name="orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date orderDate;
	private String status;
	@Column(name="amount")
	private double totalAmount;
	
	@ManyToOne
	private User user;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="order_id")
	private List<OrderItem> items;
	
	public Order() {
		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double gettotalAmount() {
		return totalAmount;
	}
	public void settotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public User getUser() {
        return user;
    }

    public void setUser(User user) {   // 🔥 fixes setUser error
        this.user = user;
    }
    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) { // ✅ FIXED
        this.items = items;
    }
	

}
