package nvb.dev.springadvancedproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nvb.dev.springadvancedproject.dto.MemberDto;
import nvb.dev.springadvancedproject.mapper.MemberMapper;
import nvb.dev.springadvancedproject.model.MemberEntity;
import nvb.dev.springadvancedproject.service.MemberService;
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

    @PostMapping(path = "/save")
    public ResponseEntity<MemberDto> saveMember(@RequestBody @Valid MemberDto memberDto) {
        MemberEntity memberEntity = memberMapper.toMemberEntity(memberDto);
        MemberEntity savedMember = memberService.saveMember(memberEntity);
        return new ResponseEntity<>(memberMapper.toMemberDto(savedMember), HttpStatus.CREATED);
    }

    @GetMapping(path = "/{memberId}")
    public ResponseEntity<MemberDto> getMemberById(@PathVariable long memberId) {
        Optional<MemberEntity> foundMember = memberService.getMemberById(memberId);
        return foundMember.map(memberEntity -> {
            MemberDto memberDto = memberMapper.toMemberDto(memberEntity);
            return new ResponseEntity<>(memberDto, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<MemberDto> getMemberByFullName(@RequestParam String fullName) {
        Optional<MemberEntity> memberByFullName = memberService.getMemberByFullName(fullName);
        return memberByFullName.map(memberEntity -> {
            MemberDto memberDto = memberMapper.toMemberDto(memberEntity);
            return new ResponseEntity<>(memberDto, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<MemberDto>> getAllMembers() {
        List<MemberEntity> allMembers = memberService.getAllMembers();
        List<MemberDto> memberDtoList = allMembers.stream().map(memberMapper::toMemberDto).toList();
        return new ResponseEntity<>(memberDtoList, HttpStatus.OK);
    }

    @PutMapping(path = "/{memberId}")
    public ResponseEntity<MemberDto> updateMember(@PathVariable long memberId, @RequestBody @Valid MemberDto memberDto) {
        MemberEntity memberEntity = memberMapper.toMemberEntity(memberDto);
        MemberEntity updatedMember = memberService.updateMember(memberId, memberEntity);
        return new ResponseEntity<>(memberMapper.toMemberDto(updatedMember), HttpStatus.OK);
    }

    @PatchMapping(path = "/{memberId}")
    public ResponseEntity<MemberDto> partialUpdate(@PathVariable long memberId,
                                                   @RequestBody @Valid Map<String, Object> memberDto) {
        MemberEntity memberEntity = memberService.partialUpdate(memberId, memberDto);
        return new ResponseEntity<>(memberMapper.toMemberDto(memberEntity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{memberId}")
    public ResponseEntity<HttpStatus> deleteMember(@PathVariable long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.noContent().build();
    }

}
