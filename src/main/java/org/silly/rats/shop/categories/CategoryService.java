package org.silly.rats.shop.categories;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.silly.rats.shop.attribute.Attribute;
import org.silly.rats.shop.attribute.AttributeRepository;
import org.silly.rats.shop.item.Item;
import org.silly.rats.shop.item.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
	private final CategoryRepository categoryRepository;
	private final AttributeRepository attributeRepository;
	private Category currentCategory = null;

	public Category getCategory(Integer id) {
		if (currentCategory == null) {
			currentCategory = categoryRepository.findById(id).orElse(null);
			return currentCategory;
		}

		if (id.equals(currentCategory.getId())) {
			return currentCategory;
		}

		if (id.equals(0)) {
			currentCategory = new Category();
			currentCategory.setSubCategories(
					categoryRepository.findAllBasic());
			return currentCategory;
		}

		Category parentCategory;
		parentCategory = lookCategoryDown(currentCategory, id);
		if (parentCategory == null) {
			parentCategory = lookCategoryUp(currentCategory, id);
		}

		currentCategory = parentCategory;
		if (currentCategory == null) {
			currentCategory = categoryRepository.getReferenceById(id);
		}
		return currentCategory;
	}

	public List<Attribute> getAttributes(Integer category) {
		if (currentCategory == null || !currentCategory.getId().equals(category) ||
				!Hibernate.isInitialized(currentCategory.getAttributes())) {
			return attributeRepository.findByCategoryId(category);
		}
		return currentCategory.getAttributes();
	}

	private Category lookCategoryDown(Category category, Integer id) {
		if (category.getId().equals(id)) {
			return category;
		}

		for (Category subCategory : category.getSubCategories()) {
			Category newCategory = lookCategoryDown(subCategory, id);
			if (newCategory != null) {
				return newCategory;
			}
		}
		return null;
	}

	private Category lookCategoryUp(Category category, Integer id) {
		while (category != null) {
			category = category.getParent();
			if (category.getId().equals(id)) {
				break;
			}
		}
		return category;
	}
}
