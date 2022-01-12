package com.discover.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.discover.userservice.utils.ProjectUtils;
import com.discover.userservice.dto.TicketDTO;
import com.discover.userservice.mapper.TicketMapper;
import com.discover.userservice.model.Ticket;
import lombok.extern.slf4j.Slf4j;
import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;

@RestController @RequestMapping("/rest/user/tickets")
@Tag(name = "Ticket Rest Template Service", description = "Ticket API to perform operations on the ticket details.")
@Slf4j(topic="UserRestTemplateController")
public class UserRestTemplateController {

	@Autowired private RestTemplate restTemplate;
	@Autowired private TicketMapper ticketMapper;
	String s = "Retrieved ticket details from ticket-service using RestTemplate: {}";

	@Operation(summary="Get Tickets", description = "Retrieves all the available tickets", tags = { "tickets" },
			responses={
					@ApiResponse(description = "Retrieved all the tickets", responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TicketDTO.class)))),
					@ApiResponse(responseCode = "400", description = "Invalid details supplied", content = @Content),
					@ApiResponse(responseCode = "404", description = "Tickets are not found", content = @Content),
			}
	)
	@GetMapping("/")
	public ResponseEntity<List<TicketDTO>> retrieveAllTickets() {
		log.trace("RestController: UserRestTemplateController, Mapping: /users/tickets-GET, Method: retrieveAllTickets");
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>(headers);
		log.info("Tickets are being retrieved..");
		List<Ticket> tickets = Arrays.asList(restTemplate.exchange(ProjectUtils.TICKET_RT_URL, HttpMethod.GET, entity, Ticket[].class).getBody());
		log.debug(s,tickets.toString());
		log.info(s,tickets.toString());
		return new ResponseEntity<>(ticketMapper.toTicketDTOs(tickets), HttpStatus.OK);
	}

	@Operation(summary="Add a Ticket", tags = { "tickets" }, requestBody =
	@io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "Details of the ticket to be created",
			required = true,
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ticket.class),
					examples = {
							@ExampleObject(
									name = "An example request with all fields provided with example values.",
									summary = "Sample request",
									value = "{ \"name\": \"TicketName1\", \"description\": \"Description1\" }")
					}
			)
	),
			responses={
					@ApiResponse(responseCode = "201", description = "Ticket Created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ticket.class))),
					@ApiResponse(responseCode = "400", description = "Invalid details supplied", content = @Content),
					@ApiResponse(responseCode = "409", description = "Ticket already exists")
			}
	)
	@PostMapping("/")
	public ResponseEntity<Ticket> createTicket(@Parameter(description="User to add. Cannot null or empty.", schema=@Schema(implementation = Ticket.class))
													@Valid @RequestBody final TicketDTO newTicket) {
		log.trace("RestController: UserRestTemplateController, Mapping: /users/tickets/-POST, Method: createTicket");
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<TicketDTO> entity = new HttpEntity<>(newTicket, headers);
		log.info("Ticket is being created..");
		TicketDTO ticket = restTemplate.exchange(ProjectUtils.TICKET_RT_URL, HttpMethod.POST, entity, TicketDTO.class).getBody();
		log.debug(s,ticket);
		log.info(s,ticket);
		return new ResponseEntity<>(ticketMapper.toTicket(ticket), HttpStatus.CREATED);
	}

	@Operation(summary="Update a Ticket", description = "Update an existing ticket by ID", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Details of the ticket to be updated"),  tags = { "tickets" },
			responses={
					@ApiResponse(responseCode = "200", description = "Successful operation"),
					@ApiResponse(responseCode = "201", description = "Updated a ticket by id", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ticket.class))),
					@ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content),
					@ApiResponse(responseCode = "404", description = "Ticket not found"),
					@ApiResponse(responseCode = "405", description = "Validation exception")
			}
	)
	@PutMapping("/{id}")
	public ResponseEntity<Ticket> updateTicketById(@Parameter(description = "id of ticket to be searched") @PathVariable("id") final Long id, @Parameter(description="Ticket to update. Cannot null or empty.", schema=@Schema(implementation = Ticket.class))
		@Valid @RequestBody final TicketDTO editTicket) {
		log.trace("RestController: UserRestTemplateController, Mapping: /users/tickets/id-PUT, Method: updateTicketById");
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		editTicket.setId(id);editTicket.setName(editTicket.getName());editTicket.setDescription(editTicket.getDescription());
		HttpEntity<TicketDTO> entity = new HttpEntity<>(editTicket, headers);
		log.info("Ticket is being updated by ID..");
		TicketDTO ticket = restTemplate.exchange(ProjectUtils.TICKET_RT_URL+id, HttpMethod.PUT, entity, TicketDTO.class).getBody();
		log.debug(s,ticket);
		log.info(s,ticket);
		return new ResponseEntity<>(ticketMapper.toTicket(ticket), HttpStatus.ACCEPTED);
	}

	@Operation(summary="Find ticket by ID", description = "Returns a single ticket", tags = { "tickets" },
			responses={
					@ApiResponse(responseCode = "200", description = "Retrieved a ticket by id", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketDTO.class))),
					@ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content),
					@ApiResponse(responseCode = "404", description = "Ticket Not found", content = @Content),
					@ApiResponse(responseCode = "405", description = "Validation exception")
			}
	)
	@GetMapping("/{id}")
	public ResponseEntity<TicketDTO> retrieveTicketById(@Parameter(description = "id of the ticket to be searched") @PathVariable("id") final Long id) {
		log.trace("RestController: UserRestTemplateController, Mapping: /users/tickets/id-GET, Method: retrieveTicketById");
		log.info("Ticket is being retrieved by ID..");
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Ticket> entity = new HttpEntity<>(headers);
		TicketDTO ticket = restTemplate.exchange(ProjectUtils.TICKET_RT_URL+id, HttpMethod.GET, entity, TicketDTO.class).getBody();
		log.debug(s,ticket);
		log.info(s,ticket);
		return new ResponseEntity<>(ticket, HttpStatus.OK);
	}

}