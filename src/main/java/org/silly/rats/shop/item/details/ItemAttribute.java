package org.silly.rats.shop.item.details;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.silly.rats.shop.attribute.value.AttributeValue;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "item_attribute")
public class ItemAttribute {
	@Id
	@JsonIgnore
	private Integer item;
	@Id
	private Integer value;

	@ManyToOne
	@JoinColumn(name = "item")
	@JsonBackReference
	private ItemType itemType;

	@ManyToOne
	@JoinColumn(name = "value")
	@JsonBackReference
	private AttributeValue attributeValue;

	public String getAttribute() {
		return attributeValue.getAttribute().getName();
	}

	public String getValue() {
		return attributeValue.getValue();
	}
}
