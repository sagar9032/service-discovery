package com.discover.userservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.experimental.FieldDefaults;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NonNull;

@Entity @FieldDefaults(level= AccessLevel.PRIVATE) @Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class Ticket {
	@GeneratedValue(strategy=GenerationType.IDENTITY) @Id Long id;
	@NotBlank @NonNull @Size(min=3,max=25) String name;
	String description;
}