package org.silly.rats.shop.item;

import lombok.RequiredArgsConstructor;
import org.silly.rats.shop.item.details.*;
import org.silly.rats.util.ImageUtil;
import org.silly.rats.util.ImageWrapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ItemService {
	private final ItemRepository itemRepository;
	private final ItemImageRepository itemImageRepository;
	private final ItemTypeRepository itemTypeRepository;
	private Integer currentCategory;
	private Map<Integer, List<Integer>> attributeFilters = new HashMap<>();
	private List<Item> items = new ArrayList<>();
	private List<Item> filtered;
	private Comparator<Item> comparator = Comparator.comparingInt(Item::getId);
	private Double from;
	private Double to;
	private String namePart = "";

	public void setFilters(FilterRequest request) {
		this.attributeFilters = request.getAttributes();
		this.from = request.getFrom();
		this.to = request.getTo();
		this.namePart = request.getNamePart();

		if (request.getSortBy().equals("price")) {
			comparator = Comparator.comparingDouble(Item::getPrice);
		} else if (request.getSortBy().equals("name")) {
			comparator = Comparator.comparing(Item::getName);
		} else {
			comparator = Comparator.comparingInt(Item::getId);
		}

		if (!request.isAsc()) {
			comparator = comparator.reversed();
		}

		filter();
	}

	private void filter() {
		filtered = items.stream()
				.filter(item -> item.isPriceInRange(from, to))
				.filter(item -> item.isNameContains(namePart))
				.filter(item -> {
					for (List<Integer> attributeFilter : attributeFilters.values()) {
						if (attributeFilter.isEmpty()) {
							return true;
						}
						boolean contains = false;
						for (Integer values : attributeFilter) {
							contains = contains || containsValue(item.getAttributes(), values);
						}
						if (!contains) {
							return false;
						}
					}
					return true;
				})
				.sorted(comparator)
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
		return itemRepository.findById(id).orElseThrow(() ->
				new IllegalArgumentException("There is no item with id: " + id));
	}

	public ItemType getItemType(Integer id) {
		return itemTypeRepository.findById(id).orElseThrow(() ->
				new IllegalArgumentException("There is no item type with id: " + id));
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
