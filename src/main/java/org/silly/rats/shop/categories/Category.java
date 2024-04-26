package org.silly.rats.shop.categories;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.silly.rats.shop.attribute.Attribute;
import org.silly.rats.shop.item.Item;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "category")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id")
	private Integer id;
	private String name;

	@Column(name = "image_name")
	@JsonIgnore
	private String imageName;

	@ManyToOne
	@JoinColumn(name = "sub_category")
	@JsonBackReference
	private Category parent;

	@OneToMany(mappedBy = "parent")
	private List<Category> subCategories;

	@OneToMany(mappedBy = "category")
	private List<Attribute> attributes;

	@OneToMany(mappedBy = "category")
	@JsonIgnore
	private List<Item> items;
}
