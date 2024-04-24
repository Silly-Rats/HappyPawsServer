package org.silly.rats.shop.attribute;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttributeService {
	private final AttributeRepository attributeRepository;

	public List<Attribute> getAttributes() {
		return attributeRepository.findAll();
	}

	public List<Attribute> getAttributesByCategory(Integer categoryId) {
		return attributeRepository.findByCategoryId(categoryId);
	}
}
