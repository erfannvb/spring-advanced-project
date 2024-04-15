package nvb.dev.springadvancedproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nvb.dev.springadvancedproject.assembler.MemberModelAssembler;
import nvb.dev.springadvancedproject.dto.MemberDto;
import nvb.dev.springadvancedproject.dto.request.MailRequest;
import nvb.dev.springadvancedproject.mapper.MemberMapper;
import nvb.dev.springadvancedproject.model.MemberEntity;
import nvb.dev.springadvancedproject.service.MailService;
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
@Tag(name = "Member Controller", description = "Performing Crud Operations on Members")
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper memberMapper;
    private final MemberModelAssembler memberModelAssembler;
    private final MailService mailService;

    @Operation(summary = "Create Member", description = "Creates a member from the provided payload")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Creation of Member"),
            @ApiResponse(responseCode = "400", description = "Bad Request : Unsuccessful Submission")
    })
    @PostMapping(path = "/save")
    public ResponseEntity<EntityModel<MemberDto>> saveMember(@RequestBody @Valid MemberDto memberDto) {
        MemberEntity memberEntity = memberMapper.toMemberEntity(memberDto);
        MemberEntity savedMember = memberService.saveMember(memberEntity);

        mailService.sendMail(MailRequest.builder()
                .recipient(memberDto.getEmail())
                .subject("Sign Up")
                .message("Welcome to the Library Management System")
                .build());

        EntityModel<MemberDto> model = memberModelAssembler.toModel(savedMember);
        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    @Operation(summary = "Get Member by ID", description = "Returns a member based on an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Retrieval of Member"),
            @ApiResponse(responseCode = "404", description = "Member Not Found")
    })
    @GetMapping(path = "/{memberId}")
    public ResponseEntity<EntityModel<MemberDto>> getMemberById(@PathVariable long memberId) {
        Optional<MemberEntity> foundMember = memberService.getMemberById(memberId);
        return foundMember.map(memberModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get Member by FullName", description = "Returns a member based on the full name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Retrieval of Member"),
            @ApiResponse(responseCode = "404", description = "Member Not Found")
    })
    @GetMapping
    public ResponseEntity<EntityModel<MemberDto>> getMemberByFullName(@RequestParam String fullName) {
        Optional<MemberEntity> memberByFullName = memberService.getMemberByFullName(fullName);
        return memberByFullName.map(memberModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all the members", description = "Provides a list of all members")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Retrieval of Members"),
            @ApiResponse(responseCode = "404", description = "Members Not Found")

    })
    @GetMapping(path = "/all")
    public ResponseEntity<CollectionModel<EntityModel<MemberDto>>> getAllMembers() {
        List<MemberEntity> allMembers = memberService.getAllMembers();
        if (allMembers.isEmpty()) return ResponseEntity.notFound().build();

        CollectionModel<EntityModel<MemberDto>> collectionModel = memberModelAssembler.toCollectionModel(allMembers);

        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @Operation(summary = "Update Member", description = "Update member based on an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Updating of Member"),
            @ApiResponse(responseCode = "404", description = "Member Not Found"),
            @ApiResponse(responseCode = "400", description = "Bad Request : Unsuccessful Submission")
    })
    @PutMapping(path = "/{memberId}")
    public ResponseEntity<EntityModel<MemberDto>> updateMember(@PathVariable long memberId, @RequestBody @Valid MemberDto memberDto) {
        MemberEntity memberEntity = memberMapper.toMemberEntity(memberDto);
        MemberEntity updatedMember = memberService.updateMember(memberId, memberEntity);
        EntityModel<MemberDto> model = memberModelAssembler.toModel(updatedMember);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @Operation(summary = "Partial Update Member", description = "Partial update member based on an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Updating of Member"),
            @ApiResponse(responseCode = "404", description = "Member Not Found"),
            @ApiResponse(responseCode = "400", description = "Bad Request : Unsuccessful Submission")
    })
    @PatchMapping(path = "/{memberId}")
    public ResponseEntity<EntityModel<MemberDto>> partialUpdate(@PathVariable long memberId,
                                                                @RequestBody Map<String, Object> memberDto) {
        MemberEntity memberEntity = memberService.partialUpdate(memberId, memberDto);
        EntityModel<MemberDto> model = memberModelAssembler.toModel(memberEntity);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @Operation(summary = "Delete Member by ID", description = "Delete a member based on an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful deletion of member"),
            @ApiResponse(responseCode = "404", description = "Member does not exist")
    })
    @DeleteMapping(path = "/{memberId}")
    public ResponseEntity<HttpStatus> deleteMember(@PathVariable long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.noContent().build();
    }

}
