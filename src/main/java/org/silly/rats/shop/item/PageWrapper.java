package org.silly.rats.shop.item;

import lombok.Data;

import java.util.List;

@Data
public class PageWrapper<T> {
	private final List<T> items;
	private final Boolean hasMore;

	public PageWrapper(List<T> items, Boolean hasMore) {
		this.items = items;
		this.hasMore = hasMore;
	}
}
