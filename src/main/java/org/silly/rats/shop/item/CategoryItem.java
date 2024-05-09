package org.silly.rats.shop.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.silly.rats.shop.item.details.ItemType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryItem {
	private Integer id;
	private String name;
	private String description;
	private Double price;
	private Boolean available;

	public CategoryItem(Item item) {
		this.id = item.getId();
		this.name = item.getName();
		this.description = item.getDescription();
		this.price = item.getPrice();

		this.available = false;
		for (ItemType itemType : item.getTypes()) {
			if (itemType.getQty() > 0) {
				this.available = true;
				break;
			}
		}
	}
}
