package org.silly.rats.shop.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.silly.rats.shop.item.details.ItemImage;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryItem {
	private Integer id;
	private String name;
	private String description;

	public CategoryItem(Item item) {
		this.id = item.getId();
		this.name = item.getName();
		this.description = item.getDescription();
	}
}
