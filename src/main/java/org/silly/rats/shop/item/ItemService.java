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

	public boolean containsValue(List<ItemAttribute> attributes, Integer value) {
		for (ItemAttribute itemAttribute : attributes) {
			if (itemAttribute.getId().getAttributeValue().getId().equals(value)) {
				return true;
			}
		}
		return false;
	}

	public PageWrapper<CategoryItem> getFilteredCategoryItems(Integer categoryId, Integer page,
															  Integer size, FilterRequest filers) {
		return itemRepository.findFilteredCategoryItems(categoryId, page, size, filers);
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
