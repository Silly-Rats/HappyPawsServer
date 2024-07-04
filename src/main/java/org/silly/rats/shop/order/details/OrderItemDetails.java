package org.silly.rats.shop.order.details;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.silly.rats.shop.item.details.ItemType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "order_item_details")
public class OrderItemDetails {
	@EmbeddedId
	@JsonIgnore
	private OrderDetailId id;
	private Integer qty;

	public ItemType getItem() {
		return id.getItem();
	}
}
