package org.silly.rats.shop.item;

import lombok.RequiredArgsConstructor;
import org.silly.rats.util.ImageUtil;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping(path = "/api/item")
@RequiredArgsConstructor
public class ItemController {
	private final ItemService itemService;

	@GetMapping(path = "/{category}/items")
	public ListWrapper getItemsByCategory(@PathVariable Integer category,
											 @RequestParam Integer start,
											 @RequestParam Integer limit) {
		return itemService.getCategoryItem(category, start, limit);
	}

	@GetMapping(path = "/{item}/info")
	public Item getItem(@PathVariable Integer item) {
		return itemService.getItem(item);
	}
}
