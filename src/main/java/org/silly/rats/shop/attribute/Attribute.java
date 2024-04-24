package org.silly.rats.shop.attribute;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.silly.rats.shop.attribute.value.AttributeValue;
import org.silly.rats.shop.categories.Category;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "attribute")
public class Attribute {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "attribute_id")
	private Integer id;
	private String name;

	@ManyToOne
	@JoinColumn(name = "category")
	@JsonBackReference
	private Category category;

	@OneToMany(mappedBy = "attribute")
	List<AttributeValue> values;
}
