package org.silly.rats.shop.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
	private final ItemRepository itemRepository;
	private Integer currentCategory;
	private List<Item> items;

	public ListWrapper getCategoryItem(Integer category, Integer start, Integer limit) {
		if (currentCategory == null || !currentCategory.equals(category)) {
			items = itemRepository.findByCategoryId(category);
			currentCategory = category;
		}

		return new ListWrapper(items.subList(start, Math.min(start + limit, items.size())),
				items.size() > start + limit);
	}

	public Item getItem(Integer item) {
		return itemRepository.findById(item).orElse(null);
	}
}
