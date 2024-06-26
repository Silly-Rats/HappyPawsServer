package org.silly.rats.shop.item.details;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.silly.rats.shop.attribute.value.AttributeValue;
import org.silly.rats.shop.item.Item;

@Data
@Embeddable
public class ItemAttributeId {
	@ManyToOne
	@JoinColumn(name = "item")
	@JsonBackReference
	private Item item;

	@ManyToOne
	@JoinColumn(name = "value")
	@JsonBackReference
	private AttributeValue attributeValue;
}
