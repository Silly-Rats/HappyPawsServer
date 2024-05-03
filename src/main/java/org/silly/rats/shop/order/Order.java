package org.silly.rats.shop.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.silly.rats.shop.order.details.OrderItemDetails;
import org.silly.rats.shop.order.details.OrderStatus;
import org.silly.rats.user.User;

import java.time.Instant;
import java.time.LocalDate;
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
	@Column(name = "order_id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "status")
	private OrderStatus status;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "order_date")
	private LocalDateTime orderDate = LocalDateTime.now();

	@Column(name = "change_date")
	private LocalDateTime changeDate = LocalDateTime.now();

	@OneToMany(mappedBy = "order")
	List<OrderItemDetails> details = new ArrayList<>();

	public String getStatus() {
		return status.getName();
	}
}