package org.silly.rats.shop.attribute;

import jakarta.persistence.*;
import org.silly.rats.shop.categories.Category;

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
	private Category category;

	public Attribute() {}

	public Attribute(Integer id, String name, Category category) {
		this.id = id;
		this.name = name;
		this.category = category;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
