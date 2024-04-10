package nvb.dev.springadvancedproject.assembler;

import nvb.dev.springadvancedproject.controller.MemberController;
import nvb.dev.springadvancedproject.dto.MemberDto;
import nvb.dev.springadvancedproject.mapper.MemberMapper;
import nvb.dev.springadvancedproject.model.MemberEntity;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MemberModelAssembler implements RepresentationModelAssembler<MemberEntity, EntityModel<MemberDto>> {

    @Override
    @NonNull
    public EntityModel<MemberDto> toModel(@NonNull MemberEntity memberEntity) {
        MemberDto memberDto = MemberMapper.INSTANCE.toMemberDto(memberEntity);
        return EntityModel.of(memberDto,
                linkTo(methodOn(MemberController.class).getMemberById(memberDto.getId())).withSelfRel(),
                linkTo(methodOn(MemberController.class).getAllMembers()).withRel(IanaLinkRelations.COLLECTION));
    }
}
