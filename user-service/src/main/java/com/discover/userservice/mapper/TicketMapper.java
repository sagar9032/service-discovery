package com.discover.userservice.mapper;

import com.discover.userservice.dto.TicketDTO;
import com.discover.userservice.model.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    public TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);
    TicketDTO toTicketDTO(Ticket ticket);
    List<TicketDTO> toTicketDTOs(List<Ticket> tickets);
    Ticket toTicket(TicketDTO ticketDTO);
}