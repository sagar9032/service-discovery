package com.discover.userservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import com.discover.userservice.dto.TicketDTO;
import com.discover.userservice.model.Ticket;
import java.util.List;

@FeignClient(name="ticket-service", url = "http://localhost:8082")
public interface UserFeignClientService {
	
	@PostMapping("/tickets/")
	Ticket saveTicket(TicketDTO newTicket);
	
	@GetMapping("/tickets/{id}")
	Ticket getTicketById(@PathVariable("id") Long id);
	
	@GetMapping("/tickets/")
    List<Ticket> getAllTicketDetails();
	
	@PutMapping("/tickets/{id}")
	Ticket updateTicketById(@PathVariable("id") Long id, final TicketDTO editTicket);
	
}