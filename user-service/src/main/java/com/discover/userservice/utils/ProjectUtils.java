package com.discover.userservice.utils;

public class ProjectUtils {
	private ProjectUtils() {
		throw new IllegalStateException("Utility class");
	}
	public static final String USER_LOCAL_URL = "http://localhost:8081/users/";
	public static final String USER_SERVICE_URL = "http://192.168.0.107:8081/users/";
	public static final String USER_TICKET_URL = "http://localhost:8081/users/tickets/";
	public static final String DB_HEALTH_URL = "http://localhost:8081/actuator/health/db";
	public static final String TICKET_RT_URL = "http://localhost:8082/tickets/";
}