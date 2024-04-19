package org.silly.rats.user;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer user_id;
	private String first_name;
	private String last_name;
	private LocalDate dob;
	private String phone_num;
	private String email;
	private Byte type;
	private String image_name;
	private String password;

	public User() {
	}

	public User(String first_name, String last_name, LocalDate dob, String phone_num, String email, Byte type, String image_name, String password) {
		this(-1, first_name, last_name, dob, phone_num, email, type, image_name, password);
	}

	public User(Integer user_id, String first_name, String last_name, LocalDate dob, String phone_num, String email, Byte type, String image_name, String password) {
		this.user_id = user_id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.dob = dob;
		this.phone_num = phone_num;
		this.email = email;
		this.type = type;
		this.image_name = image_name;
		this.password = password;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getPhone_num() {
		return phone_num;
	}

	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public String getImage_name() {
		return image_name;
	}

	public void setImage_name(String image_name) {
		this.image_name = image_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
