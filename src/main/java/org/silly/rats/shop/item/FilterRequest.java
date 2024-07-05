package org.silly.rats.shop.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilterRequest {
	private int page;
	private int size;
	private Map<Integer, List<Integer>> attributes;
	private Double from;
	private Double to;
	private String namePart;
	private String sortBy;
	private boolean asc;
}
