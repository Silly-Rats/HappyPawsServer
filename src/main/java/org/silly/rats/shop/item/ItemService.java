package org.silly.rats.shop.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
	private final ItemRepository itemRepository;

	public List<Item> getCategoryItem(Integer category) {
		return itemRepository.findByCategoryId(category);
	}
}
