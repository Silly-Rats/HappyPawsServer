package org.silly.rats.shop.categories;

import lombok.RequiredArgsConstructor;
import org.silly.rats.shop.attribute.Attribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/category")
@RequiredArgsConstructor
public class CategoryController {
	private final CategoryService categoryService;

	@GetMapping(path = "/info/{category}")
	public Category getCategory(@PathVariable Integer category) {
		return categoryService.getCategory(category);
	}

	@GetMapping(path = "/info")
	public List<Category> getAllCategories() {
		return categoryService.getAllCategories();
	}

	@GetMapping(path = "/attr/{category}")
	public List<Attribute> getAttributes(@PathVariable Integer category) {
		return categoryService.getAttributes(category);
	}
}
