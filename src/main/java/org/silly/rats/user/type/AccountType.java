package org.silly.rats.user.type;

import jakarta.persistence.*;

@Entity
@Table(name = "account_type")
public class AccountType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "type_id")
	private Byte id;
	private String name;

	public AccountType() {
	}

	public AccountType(Byte id, String name) {
		this.id = id;
		this.name = name;
	}

	public Byte getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
