package org.silly.rats.shop.item;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.silly.rats.shop.item.details.ItemType;

import java.util.*;

public class FindFilteredItemsImpl
	implements FindFilteredItems {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public PageWrapper<CategoryItem> findFilteredCategoryItems(Integer category, Integer page,
															   Integer size, FilterRequest filter) {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("FIND_FILTERED_CATEGORY_ITEMS");
		query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(4, Double.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(5, Double.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(6, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(7, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(8, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(9, Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(10, Boolean.class, ParameterMode.OUT);

		if (filter.getFrom() == null) {
			filter.setFrom(0.0);
		}
		if (filter.getTo() == null) {
			filter.setTo(Double.MAX_VALUE);
		}

		query.setParameter(1, category);
		query.setParameter(2, page);
		query.setParameter(3, size);
		query.setParameter(4, filter.getFrom());
		query.setParameter(5, filter.getTo());
		query.setParameter(6, filter.getNamePart());
		query.setParameter(7, filter.getSortBy() + " " +
				(filter.isAsc() ? " ASC" : " DESC"));

		StringJoiner joiner = new StringJoiner(" OR ");
		for (List<Integer> types : filter.getAttributes().values()) {
			for (Integer type : types) {
				joiner.add("A.value = " + type);
			}
		}

		query.setParameter(8, joiner.toString());
		query.setParameter(9, filter.getAttributes().size());

		query.execute();
		List<Object[]> rows = query.getResultList();
		List<Item> items = new ArrayList<>();

		for (Object[] row : rows) {
			Item item = new Item();
			item.setId((Integer) row[0]);
			item.setName((String) row[1]);
			item.setDescription((String) row[2]);
			items.add(item);
		}

		query.hasMoreResults();
		rows = query.getResultList();
		Map<Integer, List<ItemType>> typeMap = new HashMap<>();
		for (Object[] row : rows) {
			ItemType itemType = new ItemType();
			itemType.setId((Integer) row[0]);
			itemType.setName((String) row[2]);
			itemType.setQty((Integer) row[3]);
			itemType.setPrice((Double) row[4]);
			typeMap.merge((Integer) row[1], new ArrayList<>(List.of(itemType)),
					(oldValue, newValue) -> {
						oldValue.addAll(newValue);
						return oldValue;
					});
		}

		for (Item item : items) {
			item.setTypes(typeMap.get(item.getId()));
		}

		List<CategoryItem> categoryItems = items.stream()
				.map(CategoryItem::new)
				.toList();

		return new PageWrapper<>(categoryItems,
				(Boolean) query.getOutputParameterValue(10));
	}
}
