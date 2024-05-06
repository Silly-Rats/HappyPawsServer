package org.silly.rats.shop.item;

import lombok.RequiredArgsConstructor;
import org.silly.rats.config.JwtService;
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

	@GetMapping(path = "/{categoryId}/items")
	public ListWrapper getItemsByCategory(@PathVariable Integer categoryId,
										  @RequestParam Integer start,
										  @RequestParam Integer limit) {
		return itemService.getCategoryItem(categoryId, start, limit);
	}

	@GetMapping(path = "/all/id")
	public List<Integer> getItemsId() {
		return itemService.getItemIds();
	}

	@GetMapping(path = "/{id}/info")
	public Item getItem(@PathVariable Integer id) {
		return itemService.getItem(id);
	}

	@GetMapping(path = "/{id}/images")
	public List<ImageWrapper> getItemImages(@PathVariable Integer id) {
		return itemService.getItemImages(id);
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

		System.out.println(imageName);
		itemService.deleteItemImage(id, imageName);
	}
}
