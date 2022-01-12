package com.discover.userservice.dto;

import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@FieldDefaults(level=AccessLevel.PRIVATE) @Getter @Setter
public class Database {
	String db;
	String validationQuery;
}