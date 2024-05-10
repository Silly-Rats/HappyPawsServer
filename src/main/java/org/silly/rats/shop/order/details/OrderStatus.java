package org.silly.rats.shop.order.details;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.silly.rats.shop.order.Order;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "status")
public class OrderStatus {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "status_id")
	private Byte id;

	@Column(name = "name", length = 20)
	private String name;

	@OneToMany(mappedBy = "status")
	@JsonIgnore
	private List<Order> orders = new ArrayList<>();
}
