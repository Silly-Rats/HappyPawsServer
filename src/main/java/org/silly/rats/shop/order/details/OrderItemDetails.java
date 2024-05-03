package org.silly.rats.shop.order.details;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.silly.rats.shop.item.Item;
import org.silly.rats.shop.item.details.ItemType;
import org.silly.rats.shop.order.Order;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "order_item_details")
public class OrderItemDetails {
	@Id
	@Column(name = "item_id")
	@JsonIgnore
	private Integer itemId;

	@Id
	@Column(name = "order_id")
	@JsonIgnore
	private Integer orderId;
	private Integer qty;

	@ManyToOne
	@JoinColumn(name = "item_id")
	private ItemType item;

	@ManyToOne
	@JoinColumn(name = "order_id")
	@JsonBackReference
	private Order order;
}
