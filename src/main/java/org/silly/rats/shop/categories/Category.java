package org.silly.rats.shop.categories;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.silly.rats.shop.attribute.Attribute;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id")
	private Integer id;
	private String name;

	@Column(name = "image_name")
	private String imageName;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "sub_category")
	private Category parent;

	@OneToMany(mappedBy = "parent")
	private List<Category> subCategories;

	@OneToMany(mappedBy = "category")
	private List<Attribute> attributes;

	public Category(String name, String imageName, Category parent, List<Category> subCategories, List<Attribute> attributes) {
		this.id = null;
		this.name = name;
		this.imageName = imageName;
		this.parent = parent;
		this.subCategories = subCategories;
		this.attributes = attributes;
	}
}
