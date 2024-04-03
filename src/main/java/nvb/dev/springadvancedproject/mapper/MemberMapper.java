package nvb.dev.springadvancedproject.mapper;

import nvb.dev.springadvancedproject.dto.MemberDto;
import nvb.dev.springadvancedproject.model.MemberEntity;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MemberMapper {

    @Mapping(source = "memberEntity.id", target = "id")
    @Mapping(source = "memberEntity.fullName", target = "fullName")
    @Mapping(source = "memberEntity.email", target = "email")
    @Mapping(source = "memberEntity.address", target = "address")
    @Mapping(source = "memberEntity.borrowedBooks", target = "borrowedBooks")
    MemberDto toMemberDto(MemberEntity memberEntity);

    @Mapping(source = "memberDto.id", target = "id")
    @Mapping(source = "memberDto.fullName", target = "fullName")
    @Mapping(source = "memberDto.email", target = "email")
    @Mapping(source = "memberDto.address", target = "address")
    @Mapping(source = "memberDto.borrowedBooks", target = "borrowedBooks")
    MemberEntity toMemberEntity(MemberDto memberDto);

}
