package org.silly.rats.shop.item;

import lombok.RequiredArgsConstructor;
import org.silly.rats.config.JwtService;
import org.silly.rats.shop.item.details.ItemType;
import org.silly.rats.util.ImageWrapper;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.List;

@RestController
@RequestMapping(path = "/api/item")
@RequiredArgsConstructor
public class ItemController {
	private final ItemService itemService;
	private final JwtService jwtService;

	@PostMapping(path = "/{categoryId}/items")
	public PageWrapper<CategoryItem> getItemsByCategory(@PathVariable Integer categoryId,
										  @RequestParam Integer page,
										  @RequestParam Integer size,
										  @RequestBody FilterRequest filterRequest) {
		return itemService.getFilteredCategoryItems(categoryId, page, size, filterRequest);
	}

	@GetMapping(path = "/all/id")
	public List<Integer> getItemsId() {
		return itemService.getItemIds();
	}

	@GetMapping(path = "/{id}/info")
	public Item getItemDetails(@PathVariable Integer id) {
		return itemService.getItem(id);
	}

	@GetMapping(path = "/type/{id}/info")
	public ItemType getItemType(@PathVariable Integer id) {
		return itemService.getItemType(id);
	}

	@GetMapping(path = "/{id}/images")
	public List<ImageWrapper> getItemImages(@PathVariable Integer id) {
		return itemService.getItemImages(id);
	}

	@GetMapping(path = "/{id}/image")
	public String getItemImage(@PathVariable Integer id) {
		return itemService.getItemImage(id);
	}

	@PatchMapping(path = "/{id}/images/add")
	public void addItemImages(@RequestHeader(name = "Authorization") String token,
											   @PathVariable Integer id,
											   @RequestBody List<String> images)
			throws AuthenticationException {
		token = token.substring(7);
		if (!jwtService.extractClaim(token, (c) -> c.get("type")).equals("shop worker")) {
			throw new AuthenticationException("User is not a shop worker");
		}

		itemService.addItemImages(id, images);
	}

	@PatchMapping(path = "/{id}/images/delete")
	public void deleteItemImage(@RequestHeader(name = "Authorization") String token,
							  @PathVariable Integer id,
							  @RequestBody String imageName)
			throws AuthenticationException {
		token = token.substring(7);
		if (!jwtService.extractClaim(token, (c) -> c.get("type")).equals("shop worker")) {
			throw new AuthenticationException("User is not a shop worker");
		}
		itemService.deleteItemImage(id, imageName);
	}
}
