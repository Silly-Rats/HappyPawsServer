package org.silly.rats.shop.attribute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttributeService {
	private final AttributeRepository attributeRepository;

	@Autowired
	public AttributeService(final AttributeRepository attributeRepository) {
		this.attributeRepository = attributeRepository;
	}

	public List<Attribute> getAttributes() {
		return attributeRepository.findAll();
	}

	public List<Attribute> getAttributesByCategory(Integer categoryId) {
		return attributeRepository.findByCategory(categoryId);
	}
}
