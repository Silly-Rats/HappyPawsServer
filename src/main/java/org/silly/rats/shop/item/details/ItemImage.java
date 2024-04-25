package org.silly.rats.shop.item.details;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.silly.rats.shop.item.Item;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
	private Item item;

	@Column(name = "image_name")
	private String imageName;
}
