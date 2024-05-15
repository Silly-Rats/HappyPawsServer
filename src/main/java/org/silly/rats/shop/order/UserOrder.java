package org.silly.rats.shop.order;

import lombok.Data;
import org.silly.rats.shop.order.details.OrderItemDetails;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserOrder {
	private Integer id;
	private String status;
	private LocalDateTime orderDate;
	private LocalDateTime changeDate;
	private Double totalPrice;
	private List<OrderItemDetails> details;

	public UserOrder(Order order) {
		this.id = order.getId();
		this.status = order.getStatusName();
		this.orderDate = order.getOrderDate();
		this.changeDate = order.getChangeDate();
		this.totalPrice = order.getTotalPrice();
		this.details = order.getDetails();
	}
}
