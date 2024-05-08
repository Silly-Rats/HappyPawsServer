package org.silly.rats.shop.attribute;

import lombok.Data;

import java.util.List;

@Data
public class AttributeFilter {
	private Integer attribute;
	private List<Integer> values;
}
