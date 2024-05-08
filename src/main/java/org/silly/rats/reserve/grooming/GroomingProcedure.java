package org.silly.rats.reserve.grooming;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "grooming_procedure")
public class GroomingProcedure {
	@Id
	@Column(name = "procedure_id")
	private Long id;

	private String name;
	private Integer minutes;

	@JsonIgnore
	private Double price;

	@OneToMany(mappedBy = "procedure")
	@JsonBackReference
	private List<GroomingDetails> groomingDetails;
}
