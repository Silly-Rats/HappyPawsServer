package org.silly.rats.shop.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/item")
@RequiredArgsConstructor
public class ItemController {
	private final ItemService itemService;

	@GetMapping(path = "/{category}")
	public List<Item> getItemsByCategory(@PathVariable("category") Integer category) {
		return itemService.getCategoryItem(category);
	}
}
