package org.silly.rats.shop.attribute.value;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.silly.rats.shop.attribute.Attribute;
import org.silly.rats.shop.item.details.ItemAttribute;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "attribute_value")
public class AttributeValue {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "value_id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "attribute")
	@JsonBackReference
	private Attribute attribute;

	private String value;

	@OneToMany(mappedBy = "attributeValue")
	@JsonIgnore
	private List<ItemAttribute> items;

	public Integer getItemsCount() {
		return items.size();
	}
}
