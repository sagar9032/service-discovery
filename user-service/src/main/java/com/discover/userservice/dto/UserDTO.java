package com.discover.userservice.dto;

import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Id;

@FieldDefaults(level=AccessLevel.PRIVATE) @Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class UserDTO {
	@Id Long id;
	String name;
	String gender;
	byte age;
}