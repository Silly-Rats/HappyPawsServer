package org.silly.rats.shop.item;

import lombok.RequiredArgsConstructor;
import org.silly.rats.shop.attribute.AttributeFilter;
import org.silly.rats.shop.item.details.ItemAttribute;
import org.silly.rats.shop.item.details.ItemImage;
import org.silly.rats.shop.item.details.ItemImageRepository;
import org.silly.rats.util.ImageUtil;
import org.silly.rats.util.ImageWrapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
	private final ItemRepository itemRepository;
	private final ItemImageRepository itemImageRepository;
	private Integer currentCategory;
	private List<AttributeFilter> attributeFilters = new ArrayList<>();
	private List<Item> items = new ArrayList<>();
	private List<Item> filtered;

	public void setFilters(List<AttributeFilter> attributeFilters) {
		this.attributeFilters = attributeFilters;
		filter();
	}

	private void filter() {
		filtered = items.stream()
				.filter(item -> {
					for (AttributeFilter attributeFilter : attributeFilters) {
						if (attributeFilter.getValues().isEmpty()) {
							return true;
						}
						boolean contains = false;
						for (Integer values : attributeFilter.getValues()) {
							contains = contains || containsValue(item.getAttributes(), values);
						}
						if (!contains) {
							return false;
						}
					}
					return true;
				})
				.toList();
	}

	public boolean containsValue(List<ItemAttribute> attributes, Integer value) {
		for (ItemAttribute itemAttribute : attributes) {
			if (itemAttribute.getId().getAttributeValue().getId().equals(value)) {
				return true;
			}
		}
		return false;
	}

	public ListWrapper getCategoryItem(Integer categoryId, Integer start, Integer limit) {
		if (currentCategory == null || !currentCategory.equals(categoryId)) {
			items = itemRepository.findByCategoryId(categoryId);
			currentCategory = categoryId;
			filter();
		}

		if (filtered.size() < start) {
			return new ListWrapper(new ArrayList<>(), false);
		}

		return new ListWrapper(filtered.subList(start, Math.min(start + limit, filtered.size())),
				filtered.size() > start + limit);
	}

	public Item getItem(Integer id) {
		return itemRepository.findById(id).orElse(null);
	}

	public List<Integer> getItemIds() {
		return itemRepository.getAllIds();
	}

	public List<ImageWrapper> getItemImages(Integer id) {
		Item item = itemRepository.findById(id).orElse(null);
		if (item == null) {
			return null;
		}

		return item.getImageWrappers();
	}


	public String getItemImage(Integer id) {
		Item item = itemRepository.findById(id).orElse(null);
		if (item == null) {
			return null;
		}

		return item.getMainImage();
	}

	public void addItemImages(Integer id, List<String> images) {
		Item item = itemRepository.findById(id).orElse(null);
		if (item == null) {
			return;
		}

		List<ItemImage> imageList = item.getImages();
		for (String image : images) {
			String imageName = id + "_" + Instant.now().toEpochMilli();
			ItemImage itemImage = new ItemImage(null, item, imageName + ".png");
			itemImage = itemImageRepository.save(itemImage);

			imageList.add(itemImage);
			ImageUtil.saveImage(image, "img/item", imageName);

		}
	}

	public void deleteItemImage(Integer id, String imageName) {
		Item item = itemRepository.findById(id).orElse(null);
		if (item == null) {
			return;
		}

		for (ItemImage itemImage : item.getImages()) {
			if (itemImage.getImageName().equals(imageName)) {
				try {
					Files.delete(Path.of(itemImage.getFullImageName()));
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				itemImageRepository.delete(itemImage);
			}
		}
	}
}
