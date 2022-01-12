package com.discover.userservice.dto;

import lombok.experimental.FieldDefaults;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@FieldDefaults(level=AccessLevel.PRIVATE) @Getter @Setter @ToString
public class TicketDTO {
	@Id Long id;
	String name;
	String description;
}