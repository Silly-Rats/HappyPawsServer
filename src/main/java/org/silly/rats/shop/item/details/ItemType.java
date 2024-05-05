package org.silly.rats.shop.item.details;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.silly.rats.shop.item.Item;
import org.silly.rats.shop.order.details.OrderItemDetails;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "item_type")
public class ItemType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "type_id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "item_id")
	@JsonBackReference
	private Item item;
	private String name;
	private Integer qty;
	private Double price;

	@OneToMany(mappedBy = "id.item")
	@JsonIgnore
	private List<OrderItemDetails> orders;
}
