package nvb.dev.springadvancedproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nvb.dev.springadvancedproject.assembler.MemberModelAssembler;
import nvb.dev.springadvancedproject.dto.MemberDto;
import nvb.dev.springadvancedproject.mapper.MemberMapper;
import nvb.dev.springadvancedproject.model.MemberEntity;
import nvb.dev.springadvancedproject.service.MemberService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/member")
@RequiredArgsConstructor
@Validated
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper memberMapper;
    private final MemberModelAssembler memberModelAssembler;

    @PostMapping(path = "/save")
    public ResponseEntity<EntityModel<MemberDto>> saveMember(@RequestBody @Valid MemberDto memberDto) {
        MemberEntity memberEntity = memberMapper.toMemberEntity(memberDto);
        MemberEntity savedMember = memberService.saveMember(memberEntity);
        EntityModel<MemberDto> model = memberModelAssembler.toModel(savedMember);
        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{memberId}")
    public ResponseEntity<EntityModel<MemberDto>> getMemberById(@PathVariable long memberId) {
        Optional<MemberEntity> foundMember = memberService.getMemberById(memberId);
        return foundMember.map(memberModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<EntityModel<MemberDto>> getMemberByFullName(@RequestParam String fullName) {
        Optional<MemberEntity> memberByFullName = memberService.getMemberByFullName(fullName);
        return memberByFullName.map(memberModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/all")
    public ResponseEntity<CollectionModel<EntityModel<MemberDto>>> getAllMembers() {
        List<MemberEntity> allMembers = memberService.getAllMembers();
        if (allMembers.isEmpty()) return ResponseEntity.notFound().build();

        CollectionModel<EntityModel<MemberDto>> collectionModel = memberModelAssembler.toCollectionModel(allMembers);

        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @PutMapping(path = "/{memberId}")
    public ResponseEntity<EntityModel<MemberDto>> updateMember(@PathVariable long memberId, @RequestBody @Valid MemberDto memberDto) {
        MemberEntity memberEntity = memberMapper.toMemberEntity(memberDto);
        MemberEntity updatedMember = memberService.updateMember(memberId, memberEntity);
        EntityModel<MemberDto> model = memberModelAssembler.toModel(updatedMember);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @PatchMapping(path = "/{memberId}")
    public ResponseEntity<EntityModel<MemberDto>> partialUpdate(@PathVariable long memberId,
                                                                @RequestBody Map<String, Object> memberDto) {
        MemberEntity memberEntity = memberService.partialUpdate(memberId, memberDto);
        EntityModel<MemberDto> model = memberModelAssembler.toModel(memberEntity);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{memberId}")
    public ResponseEntity<HttpStatus> deleteMember(@PathVariable long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.noContent().build();
    }

}
