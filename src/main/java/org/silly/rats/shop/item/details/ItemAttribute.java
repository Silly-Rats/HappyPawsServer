package org.silly.rats.shop.item.details;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
