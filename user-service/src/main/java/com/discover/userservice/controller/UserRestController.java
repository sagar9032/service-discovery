package com.discover.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.discover.userservice.dto.UserDTO;
import com.discover.userservice.mapper.UserMapper;
import com.discover.userservice.model.User;
import com.discover.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import javax.validation.Valid;

@RestController @RequestMapping("/users")
@Tag(name = "User Service", description = "USER API to perform operations on the user details.")
@Slf4j(topic="UserRestController")
public class UserRestController {
	
    @Autowired UserService userService;
    @Autowired UserMapper userMapper;

    @Operation(summary="Get Users", description = "Retrieves all the available users", tags = { "users" },
            responses={
                    @ApiResponse(description = "Retrieved all the users", responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserDTO.class)))),
                    @ApiResponse(responseCode = "400", description = "Invalid details supplied", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Users are not found", content = @Content),
            }
    )
    @GetMapping(value="/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> retrieveAllUsers() {
        log.trace("RestController: UserRestController, Mapping: /users/-GET, Method: retrieveAllUsers");
        log.info("Users are being retrieved..");
        List<User> users = userService.retrieveAllUsers();
        log.debug("Available users from user-service: {}",users);
        log.info("Available users from user-service: {}",users);
        return ResponseEntity.ok(userMapper.toUserDTOs(users));
    }

    @Operation(summary="Add a User", tags = { "users" }, requestBody =
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Details of the user to be created",
            required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class),
                    examples = {
                            @ExampleObject(
                                    name = "An example request with all fields provided with example values.",
                                    summary = "Sample request",
                                    value = "{ \"name\": \"Ashwani Singh\", \"gender\": \"M\", \"age\": 23 }")
                    }
            )
    ),
            responses={
                    @ApiResponse(responseCode = "201", description = "User Created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid details supplied", content = @Content),
                    @ApiResponse(responseCode = "409", description = "User already exists")
            }
    )
    @PostMapping(value="/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@Parameter(description="User to add. Cannot null or empty.", schema=@Schema(implementation = User.class))
                                                @Valid @RequestBody final UserDTO userDTO) {
    	log.trace("RestController: UserRestController, Mapping: /users/-POST, Method: createUser");
		log.info("User is being created..");
        User user = userMapper.toUser(userDTO);
        log.debug("Entered User details to save: {}",user);
        log.info("Entered User details to save: {}",user);
        return new ResponseEntity<>(userService.createUser(user),HttpStatus.CREATED);
    }

    @Operation(summary="Update a User", description = "Update an existing user by ID", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Details of the user to be updated"),  tags = { "users" },
            responses={
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "201", description = "Updated a user by id", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content),
                    @ApiResponse(responseCode = "404", description = "User not found"),
                    @ApiResponse(responseCode = "405", description = "Validation exception")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUserById(@Parameter(description = "id of user to be searched") @PathVariable Long id, @Parameter(description="User to update. Cannot null or empty.", schema=@Schema(implementation = User.class))
                @RequestBody UserDTO userDTO) {
        log.trace("RestController: UserRestController, Mapping: /users/{id}-PUT, Method: updateUser");
        log.info("User is being updated by id..");
        User user = userMapper.toUser(userDTO);
        log.debug("Entered User details to update: {}",user);
        log.info("Entered User details to update: {}",user);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<>(userService.updateUser(id, user),headers,HttpStatus.ACCEPTED);
    }

    @Operation(summary="Find user by ID", description = "Returns a single user", tags = { "users" },
            responses={
                    @ApiResponse(responseCode = "200", description = "Retrieved a user by id", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content),
                    @ApiResponse(responseCode = "404", description = "User Not found", content = @Content),
                    @ApiResponse(responseCode = "405", description = "Validation exception")
            }
    )
    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> retrieveUserById(@Parameter(description = "id of the user to be searched") @PathVariable Long id) {
    	log.trace("RestController: UserRestController, Mapping: /users/{id}-GET, Method: retrieveUserById");
		log.info("User is being retrieved by ID..");
        User user = userService.retrieveUserById(id);
        log.debug("Retrieved User details from the user-service for the id: {} is: {}",id,user);
        log.info("Retrieved User details from the user-service for the id: {} is: {}",id,user);
        return ResponseEntity.ok(userMapper.toUserDTO(user));
    }

    @Operation(summary = "Deletes a user", description = "Deletes a single user", tags = { "users" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "User not found") })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@Parameter(description="Id of the user to be delete. Cannot be empty.") @PathVariable Long id) {
        log.trace("RestController: UserRestController, Mapping: /users/{id}-GET, Method: deleteUserById");
        log.info("User is being deleted by ID..");
        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}