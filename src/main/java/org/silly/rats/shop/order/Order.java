package org.silly.rats.shop.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.silly.rats.shop.order.details.OrderItemDetails;
import org.silly.rats.shop.order.details.OrderStatus;
import org.silly.rats.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "`order`")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "status")
	@JsonIgnore
	private OrderStatus status;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "order_date")
	private LocalDateTime orderDate = LocalDateTime.now();

	@Column(name = "change_date")
	private LocalDateTime changeDate = LocalDateTime.now();

	@OneToMany(mappedBy = "id.order")
	private List<OrderItemDetails> details = new ArrayList<>();

	public String getStatusName() {
		return status.getName();
	}

	public double getTotalPrice() {
		double total = 0;
		for (OrderItemDetails detail : details) {
			total += detail.getQty() * detail.getItem().getPrice();
		}
		return total;
	}
}