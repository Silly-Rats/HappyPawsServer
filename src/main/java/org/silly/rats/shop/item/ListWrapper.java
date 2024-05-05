package org.silly.rats.shop.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ListWrapper {
	private final List<CategoryItem> items;
	private final Boolean hasMore;

	public ListWrapper(final List<Item> items, final Boolean hasMore) {
		this.items = items.stream()
				.map(CategoryItem::new)
				.toList();
		this.hasMore = hasMore;
	}
}
