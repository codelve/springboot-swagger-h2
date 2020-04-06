package com.reach52.asssignment.entity;


import lombok.*;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Table(name = "customer")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
@Builder
public class CustomerEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long userId;

	@Column(nullable = false, length = 50)
	private String name;

	@Column(name = "DOB")
	private Date dateofBirth;

	private String address;

	@Column
	@Lob
	private byte[] user_image;

}
