package org.silly.rats.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.silly.rats.reserve.grooming.GroomingDetails;
import org.silly.rats.reserve.training.TrainingDetails;
import org.silly.rats.shop.order.Order;
import org.silly.rats.user.dog.Dog;
import org.silly.rats.user.type.AccountType;
import org.silly.rats.user.worker.Worker;
import org.silly.rats.util.ImageUtil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User
	implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;
	private LocalDate dob;

	@Column(name = "phone_num")
	private String phoneNum;
	private String email;

	@ManyToOne
	@JoinColumn(name = "type")
	private AccountType type;

	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "user_id")
	private Worker workerDetails;

	@Column(name = "image_name")
	@JsonIgnore
	private String imageName;

	@Column(name = "modify_date")
	@JsonIgnore
	private LocalDate modifyDate = LocalDate.now();

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<Dog> dogs;

	@OneToMany(mappedBy = "worker", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<TrainingDetails> trainings;

	@OneToMany(mappedBy = "worker")
	@JsonIgnore
	private List<GroomingDetails> groomingDetails;

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<Order> orders;

	@JsonIgnore
	private String password;

	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(type.getName()));
	}

	@Override
	@JsonIgnore
	public String getUsername() {
		return email;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return true;
	}

	public String getType() {
		return type.getName();
	}

	@JsonIgnore
	public String getImage() {
		return ImageUtil.loadImage("img/user", imageName);
	}
}
