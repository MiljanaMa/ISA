package medequipsystem.mapper;

import medequipsystem.domain.User;
import medequipsystem.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends GenericMapper<User, UserDTO>  {}
