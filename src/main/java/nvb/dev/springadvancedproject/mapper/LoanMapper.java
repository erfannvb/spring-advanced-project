package nvb.dev.springadvancedproject.mapper;

import nvb.dev.springadvancedproject.dto.LoanDto;
import nvb.dev.springadvancedproject.model.LoanEntity;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LoanMapper {

    @Mapping(source = "loanEntity.id", target = "id")
    @Mapping(source = "loanEntity.loanDate", target = "loanDate")
    @Mapping(source = "loanEntity.dueDate", target = "dueDate")
    @Mapping(source = "loanEntity.returnDate", target = "returnDate")
    @Mapping(source = "loanEntity.member", target = "member")
    @Mapping(source = "loanEntity.book", target = "book")
    LoanDto toLoanDto(LoanEntity loanEntity);

    @Mapping(source = "loanDto.id", target = "id")
    @Mapping(source = "loanDto.loanDate", target = "loanDate")
    @Mapping(source = "loanDto.dueDate", target = "dueDate")
    @Mapping(source = "loanDto.returnDate", target = "returnDate")
    @Mapping(source = "loanDto.member", target = "member")
    @Mapping(source = "loanDto.book", target = "book")
    LoanEntity toLoanEntity(LoanDto loanDto);

}
