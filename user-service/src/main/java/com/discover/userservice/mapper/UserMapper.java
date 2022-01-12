package com.discover.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.discover.userservice.dto.UserDTO;
import com.discover.userservice.model.User;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
	public UserMapper INSTANCE = Mappers.getMapper(UserMapper.class); 
	UserDTO toUserDTO(User user);
	List<UserDTO> toUserDTOs(List<User> users);
	User toUser(UserDTO userDTO);
}