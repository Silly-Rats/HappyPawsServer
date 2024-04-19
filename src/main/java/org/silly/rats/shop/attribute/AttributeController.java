package org.silly.rats.shop.attribute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AttributeController {
	private final AttributeService attributeService;

	@Autowired
	public AttributeController(final AttributeService attributeService) {
		this.attributeService = attributeService;
	}

	@GetMapping(path = "/attribute")
	public List<Attribute> getAttributesByCategory(@RequestParam Integer category) {
		return attributeService.getAttributesByCategory(category);
	}
}
