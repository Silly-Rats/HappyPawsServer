package org.silly.rats.shop.item;

import lombok.RequiredArgsConstructor;
import org.silly.rats.shop.categories.Category;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/item")
@RequiredArgsConstructor
public class ItemController {
	private final ItemService itemService;

	@GetMapping(path = "/category/{category}")
	public ListWrapper getItemsByCategory(@PathVariable Integer category,
											 @RequestParam Integer start,
											 @RequestParam Integer limit) {
		return itemService.getCategoryItem(category, start, limit);
	}

	@GetMapping(path = "/info/{item}")
	public Item getItem(@PathVariable Integer item) {
		return itemService.getItem(item);
	}
}
