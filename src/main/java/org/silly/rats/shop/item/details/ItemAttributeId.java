package org.silly.rats.shop.item.details;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
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
	private Item itemType;

	@ManyToOne
	@JoinColumn(name = "value")
	@JsonBackReference
	private AttributeValue attributeValue;
}
