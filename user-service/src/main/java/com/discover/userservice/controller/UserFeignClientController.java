package com.discover.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.discover.userservice.dto.TicketDTO;
import com.discover.userservice.model.Ticket;
import com.discover.userservice.service.UserFeignClientService;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import javax.validation.Valid;

@RestController @RequestMapping("/feign/user/tickets")
@Slf4j(topic="UserFeignClientController")
public class UserFeignClientController {

	@Autowired UserFeignClientService userFeignClientService;
	String s = "Retrieved ticket details from ticket-service using FeignClient: {}";

	@GetMapping("/")
	public ResponseEntity<List<Ticket>> findAllTicketDetails() {
		log.trace("RestController: UserFeignClientController, Mapping: /user/tickets-GET, Method: retrieveTicketDetails");
		log.info("Tickets are being retrieved..");
		List<Ticket> tickets = userFeignClientService.getAllTicketDetails();
		log.debug(s,tickets.toString());
		log.info(s,tickets);
		return new ResponseEntity<>(tickets,HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<Ticket> createTicket(@Valid @RequestBody final TicketDTO ticketDTO) {
		log.trace("RestController: UserFeignClientController, Mapping: /user/tickets-POST, Method: createTicket");
		ticketDTO.setName(ticketDTO.getName());
		ticketDTO.setDescription(ticketDTO.getDescription());
		log.info("Ticket is being created..");
		Ticket ticket = userFeignClientService.saveTicket(ticketDTO);
		log.debug(s,ticket.toString());
		log.info(s,ticket);
		return new ResponseEntity<>(ticket,HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Ticket> saveOrUpdateTicketById(@PathVariable("id") final Long id, @Valid @RequestBody final TicketDTO editTicket) {
		log.trace("RestController: UserFeignClientController, Mapping: /user/tickets id-PUT, Method: updateTicketById");
		log.info("Ticket is being updated by id..");
		Ticket ticket = userFeignClientService.updateTicketById(id,editTicket);
		log.debug(s,ticket.toString());
		log.info(s,ticket);
		return new ResponseEntity<>(ticket,HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Ticket> findTicketById(@PathVariable("id") final Long id) {
		log.trace("RestController: UserFeignClientController, Mapping: /user/tickets id-GET, Method: retrieveTicketById");
		log.info("Ticket is being retrieved by id..");
		Ticket ticket = userFeignClientService.getTicketById(id);
		log.debug(s,ticket.toString());
		log.info(s,ticket);
		return new ResponseEntity<>(ticket,HttpStatus.OK);
	}
	
}