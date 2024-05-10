package org.silly.rats.shop.categories;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.silly.rats.shop.attribute.Attribute;
import org.silly.rats.shop.attribute.AttributeRepository;
import org.silly.rats.util.ImageUtil;
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

		Category parentCategory;
		parentCategory = lookCategoryDown(currentCategory, id);

		currentCategory = parentCategory;
		if (currentCategory == null) {
			currentCategory = categoryRepository.findById(id).orElse(null);
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

	public String getCategoryImage(Integer id) {
		Category category = categoryRepository.findById(id).orElseThrow(() ->
				new IllegalArgumentException("There is no category with id: " + id));
		return ImageUtil.loadImage("img/category", category.getImageName());
	}

	private Category lookCategoryDown(Category category, Integer id) {
		if (category.getId().equals(id)) {
			return category;
		}

		if (Hibernate.isInitialized(category.getSubCategories())) {
			for (Category subCategory : category.getSubCategories()) {
				Category newCategory = lookCategoryDown(subCategory, id);
				if (newCategory != null) {
					return newCategory;
				}
			}
		}
		return null;
	}

	public Category getAllCategories() {
		return new Category(null, null, null, null, categoryRepository.findAllBasic(), null, null);
	}
}
