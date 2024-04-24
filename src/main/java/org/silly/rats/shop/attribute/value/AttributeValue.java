package org.silly.rats.shop.attribute.value;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.silly.rats.shop.attribute.Attribute;

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

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "attribute")
	private Attribute attribute;

	private String value;
}
