package com.discover.userservice.model;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.experimental.FieldDefaults;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "Class representing a user tracked by the application.")
@Entity @FieldDefaults(level=AccessLevel.PRIVATE) @Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class User {

	@Schema(accessMode = AccessMode.READ_ONLY, description = "Unique identifier of the user. No two users can have the same id.", example = "1")
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

	@Schema(accessMode = AccessMode.READ_WRITE, description = "Name of the User.", example = "Aayush Saxena", minLength = 3, maxLength = 25)
	@NotNull @NotBlank(message="Not null can't empty") @Size(min=3,max=25) String name;

	@Schema(accessMode = AccessMode.READ_WRITE, description = "Gender of the User.", example = "M", allowableValues = {"M","F"})
	@NotNull(message="No null can empty") @Pattern(regexp="^[MF]{1}",message="Allows only M and F") String gender;

	@Schema(accessMode = AccessMode.READ_WRITE, description = "Age of the User. Non-negative integer.", example = "23", type="number")
	@NotNull @Min(value=0) @Max(value=100) byte age;

	public User(String name, String gender, byte age) {
		this.name = name;
		this.gender = gender;
		this.age = age;
	}

}