package org.silly.rats.shop.order.details;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.silly.rats.shop.item.details.ItemType;
import org.silly.rats.shop.order.Order;

@Data
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
