package org.silly.rats.shop.order.details;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.silly.rats.shop.item.details.ItemType;
import org.silly.rats.shop.order.Order;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class OrderDetailId {
	@ManyToOne
	@JoinColumn(name = "item_id")
	private ItemType item;

	@ManyToOne
	@JoinColumn(name = "order_id")
	@JsonBackReference
	private Order order;
}
