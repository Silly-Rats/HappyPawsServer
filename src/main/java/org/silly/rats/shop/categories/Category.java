package org.silly.rats.shop.categories;

import jakarta.persistence.*;
import org.silly.rats.shop.attribute.Attribute;

import java.util.List;

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

	@ManyToOne
	@JoinColumn(name = "sub_category")
	private Category parent;

	@OneToMany(mappedBy = "parent")
	private List<Category> subCategories;

	@OneToMany(mappedBy = "category")
	private List<Attribute> attributes;

	public Category() {
	}

	public Category(Integer id, String name, String imageName, Category parent, List<Category> subCategories, List<Attribute> attributes) {
		this.id = id;
		this.name = name;
		this.imageName = imageName;
		this.parent = parent;
		this.subCategories = subCategories;
		this.attributes = attributes;
	}

	public Category(String name, String imageName, Category parent, List<Category> subCategories, List<Attribute> attributes) {
		this(-1, name, imageName, parent, subCategories, attributes);
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getImageName() {
		return imageName;
	}

	public List<Category> getSubCategories() {
		return subCategories;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}
}
