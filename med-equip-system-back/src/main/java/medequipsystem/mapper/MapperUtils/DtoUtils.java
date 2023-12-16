package medequipsystem.mapper.MapperUtils;

import org.modelmapper.ModelMapper;

import java.util.Set;
import java.util.stream.Collectors;

public class DtoUtils {

    public DTOEntity convertToDto(Object obj , DTOEntity mapper){
        return new ModelMapper().map(obj, mapper.getClass());
    }

    public Object convertToEntity(Object obj, DTOEntity mapper){

        return new ModelMapper().map(mapper, obj.getClass());
    }

    public <DTOEntity, Object> Set<DTOEntity> convertToDtos(Set<Object> obj, DTOEntity mapper){

        return (Set<DTOEntity>) obj.stream().map(o -> new ModelMapper().map(o, mapper.getClass())).collect(Collectors.toSet());

    }

    public <DTOEntity, Object> Set<Object> convertToEntities(Object obj, Set<DTOEntity> mapper){

        return (Set<Object>) mapper.stream().map(m -> new ModelMapper().map(m, obj.getClass())).collect(Collectors.toSet());

    }

}
