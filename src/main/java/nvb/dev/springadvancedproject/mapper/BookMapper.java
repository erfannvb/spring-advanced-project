package nvb.dev.springadvancedproject.mapper;

import nvb.dev.springadvancedproject.dto.BookDto;
import nvb.dev.springadvancedproject.model.BookEntity;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookMapper {

    @Mapping(source = "bookEntity.id", target = "id")
    @Mapping(source = "bookEntity.title", target = "title")
    @Mapping(source = "bookEntity.isbn", target = "isbn")
    @Mapping(source = "bookEntity.pages", target = "pages")
    @Mapping(source = "bookEntity.rating", target = "rating")
    @Mapping(source = "bookEntity.genres", target = "genres")
    @Mapping(source = "bookEntity.publishedYear", target = "publishedYear")
    @Mapping(source = "bookEntity.author", target = "author")
    BookDto toBookDto(BookEntity bookEntity);

    @Mapping(source = "bookDto.id", target = "id")
    @Mapping(source = "bookDto.title", target = "title")
    @Mapping(source = "bookDto.isbn", target = "isbn")
    @Mapping(source = "bookDto.pages", target = "pages")
    @Mapping(source = "bookDto.rating", target = "rating")
    @Mapping(source = "bookDto.genres", target = "genres")
    @Mapping(source = "bookDto.publishedYear", target = "publishedYear")
    @Mapping(source = "bookDto.author", target = "author")
    BookEntity toBookEntity(BookDto bookDto);

}
