package org.silly.rats.shop.item;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.silly.rats.shop.categories.Category;
import org.silly.rats.shop.item.details.ItemAttribute;
import org.silly.rats.shop.item.details.ItemImage;
import org.silly.rats.shop.item.details.ItemType;
import org.silly.rats.shop.order.details.OrderItemDetails;
import org.silly.rats.util.ImageUtil;
import org.silly.rats.util.ImageWrapper;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
	@JsonIgnore
	private List<ItemImage> images;

	@OneToMany(mappedBy = "item")
	private List<ItemType> types;

	@OneToMany(mappedBy = "id.itemType")
	private List<ItemAttribute> attributes;

	@JsonIgnore
	public List<ImageWrapper> getImageWrappers() {
		List<ImageWrapper> loadedImages = new ArrayList<>(images.size());
		for (ItemImage image : images) {
			loadedImages.add(new ImageWrapper(image.getImageName(),
					ImageUtil.loadImage("img/item", image.getImageName())));
		}
		return loadedImages;
	}
}
