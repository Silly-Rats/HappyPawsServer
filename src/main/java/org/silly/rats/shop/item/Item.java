package org.silly.rats.shop.item;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.silly.rats.shop.categories.Category;
import org.silly.rats.shop.item.details.ItemImage;
import org.silly.rats.shop.item.details.ItemType;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "item")
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "category")
	@JsonBackReference
	private Category category;
	private String name;
	private String description;

	@OneToMany(mappedBy = "item")
	private List<ItemImage> images;

	@OneToMany(mappedBy = "item")
	private List<ItemType> types;
}
