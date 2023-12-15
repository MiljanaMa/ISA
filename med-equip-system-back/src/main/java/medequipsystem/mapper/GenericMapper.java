package medequipsystem.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;


public interface GenericMapper<S,T> {

        T toDto( S source );


        S toModel( T target );



        Set<T> toDto(Set<S> sourceList );


        Set<S> toModel( Set<T> targetList );

}
