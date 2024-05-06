package org.silly.rats.shop.item.details;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.silly.rats.shop.item.Item;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "item_image")
public class ItemImage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "image_id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "item")
	@JsonBackReference
	@ToString.Exclude
	private Item item;

	@Column(name = "image_name")
	private String imageName;

	@JsonIgnore
	public String getFullImageName() {
		return "img/item/" + imageName;
	}
}
