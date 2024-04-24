package org.silly.rats.shop.categories;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
	private final CategoryRepository categoryRepository;
	@Getter
	private Category currentCategory = null;

	public Category setCurrentCategory(Integer id) {
		if (currentCategory == null) {
			currentCategory = categoryRepository.findById(id).orElse(null);
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

	public List<Category> getSubCategories(Integer parent) {
		if (currentCategory == null || !currentCategory.getId().equals(parent)) {
			return categoryRepository.findAllSubCategories(parent == 0 ? null : parent);
		}
		return currentCategory.getSubCategories();
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
