package org.silly.rats.shop.item;

public interface findFilteredItems {
	PageWrapper<CategoryItem> findFilteredCategoryItems(Integer category, Integer page,
								   Integer size, FilterRequest filter);
}
