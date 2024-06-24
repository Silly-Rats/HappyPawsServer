package org.silly.rats.shop.item;

public interface FindFilteredItems {
	PageWrapper<CategoryItem> findFilteredCategoryItems(Integer category, Integer page,
								   Integer size, FilterRequest filter);
}
