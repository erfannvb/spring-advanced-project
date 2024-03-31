package nvb.dev.springadvancedproject.mapper;

import nvb.dev.springadvancedproject.dto.AuthorDto;
import nvb.dev.springadvancedproject.model.AuthorEntity;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AuthorMapper {

    @Mapping(source = "authorEntity.id", target = "id")
    @Mapping(source = "authorEntity.firstName", target = "firstName")
    @Mapping(source = "authorEntity.lastName", target = "lastName")
    @Mapping(source = "authorEntity.age", target = "age")
    @Mapping(source = "authorEntity.books", target = "books")
    AuthorDto toAuthorDto(AuthorEntity authorEntity);

    @Mapping(source = "authorDto.id", target = "id")
    @Mapping(source = "authorDto.firstName", target = "firstName")
    @Mapping(source = "authorDto.lastName", target = "lastName")
    @Mapping(source = "authorDto.age", target = "age")
    @Mapping(source = "authorDto.books", target = "books")
    AuthorEntity toAuthorEntity(AuthorDto authorDto);

}
