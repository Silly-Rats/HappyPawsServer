package org.silly.rats.shop.item.details;

import lombok.Data;
import org.silly.rats.shop.item.Item;

@Data
public class ItemWrapper {
	private Integer id;
	private String name;

	public ItemWrapper(Item item) {
		this.id = item.getId();
		this.name = item.getName();
	}
}
