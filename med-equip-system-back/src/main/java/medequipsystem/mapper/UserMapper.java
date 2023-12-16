package medequipsystem.mapper;

import medequipsystem.domain.Client;
import medequipsystem.dto.ClientDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends GenericMapper<Client, ClientDTO>  {}
