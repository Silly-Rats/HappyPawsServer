package org.silly.rats.shop.item.details;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.silly.rats.shop.attribute.value.AttributeValue;
import org.silly.rats.shop.item.Item;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "item_attribute")
public class ItemAttribute {
	@EmbeddedId
	@JsonIgnore
	private ItemAttributeId id;

	public String getAttribute() {
		return id.getAttributeValue().getAttribute().getName();
	}

	public String getValue() {
		return id.getAttributeValue().getValue();
	}
}
