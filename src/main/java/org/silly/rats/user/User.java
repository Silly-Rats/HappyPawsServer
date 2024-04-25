package org.silly.rats.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.silly.rats.user.dog.Dog;
import org.silly.rats.user.type.AccountType;
import org.silly.rats.user.worker.Worker;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
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
	private Date dob;

	@Column(name = "phone_num")
	private String phoneNum;
	private String email;

	@ManyToOne
	@JoinColumn(name = "type")
	private AccountType type;

	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "worker_id")
	private Worker workerDetails;

	@Column(name = "image_name")
	@JsonIgnore
	private String imageName;

	@Column(name = "modify_date")
	@JsonIgnore
	private Date modifyDate = new Date();

	@OneToMany(mappedBy = "user")
	private List<Dog> dogs;

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
}
