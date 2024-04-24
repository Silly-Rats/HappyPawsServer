package org.silly.rats.shop.attribute.value;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttributeValueService {
	private final AttributeValueRepository attributeValueRepository;
}
