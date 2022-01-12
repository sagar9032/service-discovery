package com.discover.userservice.dto;

import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@FieldDefaults(level=AccessLevel.PRIVATE) @Getter @Setter
public class DataBaseDTO {
	String status;
	List<Database> details;
}