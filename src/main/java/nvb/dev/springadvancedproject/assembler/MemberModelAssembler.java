package nvb.dev.springadvancedproject.assembler;

import nvb.dev.springadvancedproject.controller.MemberController;
import nvb.dev.springadvancedproject.model.MemberEntity;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Configuration
public class MemberModelAssembler implements RepresentationModelAssembler<MemberEntity, EntityModel<MemberEntity>> {

    @Override
    @NonNull
    public EntityModel<MemberEntity> toModel(@NonNull MemberEntity member) {
        return EntityModel.of(member,
                linkTo(methodOn(MemberController.class).getMemberById(member.getId())).withSelfRel(),
                linkTo(methodOn(MemberController.class).getAllMembers()).withRel(IanaLinkRelations.COLLECTION));
    }
}
