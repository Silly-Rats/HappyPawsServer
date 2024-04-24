package org.silly.rats.shop.categories;

import lombok.RequiredArgsConstructor;
import org.silly.rats.shop.attribute.Attribute;
import org.silly.rats.shop.attribute.AttributeService;
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
	private final AttributeService attributeService;

	@GetMapping(path = "/{category}")
	public Category getCategory(@PathVariable Integer category) {
		return categoryService.setCurrentCategory(category);
	}

	@GetMapping(path = "/{category}/subCategories")
	public List<Category> getSubCategories(@PathVariable Integer category) {
		return categoryService.getSubCategories(category);
	}

	@GetMapping(path = "/{category}/attributes")
	public List<Attribute> getCategoryAttributes(@PathVariable Integer category) {
		Category currentCategory = categoryService.getCurrentCategory();
		if (currentCategory != null &&  currentCategory.getId().equals(category)) {
			return currentCategory.getAttributes();
		}

		return attributeService.getAttributesByCategory(category);
	}
}
