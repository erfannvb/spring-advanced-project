package nvb.dev.springadvancedproject.service;

import nvb.dev.springadvancedproject.exception.EntityNotStorableException;
import nvb.dev.springadvancedproject.exception.member.FullNameExistsException;
import nvb.dev.springadvancedproject.exception.member.MemberNotFoundException;
import nvb.dev.springadvancedproject.model.MemberEntity;
import nvb.dev.springadvancedproject.repository.MemberRepository;
import nvb.dev.springadvancedproject.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static nvb.dev.springadvancedproject.MotherObject.anyValidMemberEntity;
import static nvb.dev.springadvancedproject.MotherObject.anyValidMemberMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class MemberServiceTest {

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    MemberServiceImpl memberService;

    @Test
    void testThatSaveMemberCanSaveMember() {
        when(memberRepository.save(any(MemberEntity.class))).thenReturn(anyValidMemberEntity());

        MemberEntity savedMember = memberService.saveMember(anyValidMemberEntity());

        assertEquals("dummy dummy", savedMember.getFullName());
        assertEquals("dummy@dummy.com", savedMember.getEmail());

        verify(memberRepository, atLeastOnce()).save(any(MemberEntity.class));
    }

    @Test
    void testThatSaveMemberThrowsFullNameExistsExceptionIfFullNameExists() {
        when(memberRepository.save(any(MemberEntity.class))).thenReturn(anyValidMemberEntity());
        when(memberRepository.findMemberByFullName(anyString())).thenReturn(Optional.of(anyValidMemberEntity()));

        assertThrows(FullNameExistsException.class, () -> memberService.saveMember(anyValidMemberEntity()));
        verify(memberRepository, atLeastOnce()).findMemberByFullName(anyString());
        verify(memberRepository, never()).save(any(MemberEntity.class));
    }

    @Test
    void testThatSaveMemberThrowsEntityNotStorableException() {
        when(memberRepository.save(any(MemberEntity.class))).thenThrow(DataIntegrityViolationException.class);

        assertThrows(EntityNotStorableException.class, () -> memberService.saveMember(anyValidMemberEntity()));
        verify(memberRepository, atLeastOnce()).save(any(MemberEntity.class));
    }

    @Test
    void testThatGetMemberByIdReturnsTheExistingMember() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(anyValidMemberEntity()));

        MemberEntity memberEntity = memberService.getMemberById(123).orElseThrow();

        assertEquals("dummy dummy", memberEntity.getFullName());
        assertEquals("dummy@dummy.com", memberEntity.getEmail());
        assertEquals("dummy", memberEntity.getAddress().getCountry());

        verify(memberRepository, atLeastOnce()).findById(anyLong());
    }

    @Test
    void testThatGetMemberByIdThrowsMemberNotFoundException() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(MemberNotFoundException.class, () -> memberService.getMemberById(1));
        verify(memberRepository, atLeastOnce()).findById(anyLong());
    }

    @Test
    void testThatGetMemberByFullNameReturnsTheExistingMember() {
        when(memberRepository.findMemberByFullNameIgnoreCase(anyString())).thenReturn(Optional.of(anyValidMemberEntity()));

        MemberEntity memberEntity = memberService.getMemberByFullName("dummy dummy").orElseThrow();

        assertEquals("dummy dummy", memberEntity.getFullName());
        assertEquals("dummy", memberEntity.getAddress().getCountry());
        verify(memberRepository, atLeastOnce()).findMemberByFullNameIgnoreCase(anyString());
    }

    @Test
    void testThatGetMemberByFullNameThrowsMemberNotFoundException() {
        when(memberRepository.findMemberByFullNameIgnoreCase(anyString())).thenReturn(Optional.empty());

        assertThrows(MemberNotFoundException.class, () -> memberService.getMemberByFullName("john doe"));
        verify(memberRepository, atLeastOnce()).findMemberByFullNameIgnoreCase(anyString());
    }

    @Test
    void testThatGetAllMembersReturnsListOfMembers() {
        when(memberRepository.findAll()).thenReturn(List.of(anyValidMemberEntity()));

        List<MemberEntity> allMembers = memberService.getAllMembers();

        assertEquals("dummy dummy", allMembers.getFirst().getFullName());
        assertEquals("dummy@dummy.com", allMembers.getFirst().getEmail());
        verify(memberRepository, atLeastOnce()).findAll();
    }

    @Test
    void testThatGetAllMembersThrowsMemberNotFoundException() {
        when(memberRepository.findAll()).thenReturn(List.of());

        assertThrows(MemberNotFoundException.class, () -> memberService.getAllMembers());
        verify(memberRepository, atLeastOnce()).findAll();
    }

    @Test
    void testThatUpdateMemberCanModifyTheExistingMember() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(anyValidMemberEntity()));
        when(memberRepository.save(any(MemberEntity.class))).thenReturn(anyValidMemberEntity());

        MemberEntity memberEntity = memberService.updateMember(123, anyValidMemberEntity());

        assertEquals("dummy dummy", memberEntity.getFullName());
        assertEquals("dummy@dummy.com", memberEntity.getEmail());

        verify(memberRepository, atLeastOnce()).findById(anyLong());
        verify(memberRepository, atLeastOnce()).save(any(MemberEntity.class));
    }

    @Test
    void testThatUpdateMemberThrowsMemberNotFoundException() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(memberRepository.save(any(MemberEntity.class))).thenReturn(anyValidMemberEntity());

        assertThrows(MemberNotFoundException.class, () -> memberService.updateMember(1, anyValidMemberEntity()));
        verify(memberRepository, atLeastOnce()).findById(anyLong());
        verify(memberRepository, never()).save(any(MemberEntity.class));
    }

    @Test
    void testThatPartialUpdateCanModifyTheExistingMember() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(anyValidMemberEntity()));
        when(memberRepository.save(any(MemberEntity.class))).thenReturn(anyValidMemberEntity());

        MemberEntity memberEntity = memberService.partialUpdate(123, anyValidMemberMap());

        assertEquals("dummy dummy", memberEntity.getFullName());
        assertEquals("dummy@dummy.com", memberEntity.getEmail());

        verify(memberRepository, atLeastOnce()).findById(anyLong());
        verify(memberRepository, atLeastOnce()).save(any(MemberEntity.class));
    }

    @Test
    void testThatPartialUpdateThrowsMemberNotFoundException() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(memberRepository.save(any(MemberEntity.class))).thenReturn(anyValidMemberEntity());

        assertThrows(MemberNotFoundException.class, () -> memberService.partialUpdate(123, anyValidMemberMap()));
        verify(memberRepository, atLeastOnce()).findById(anyLong());
        verify(memberRepository, never()).save(any(MemberEntity.class));
    }

    @Test
    void testThatDeleteMemberCanDeleteTheExistingMember() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(anyValidMemberEntity()));
        when(memberRepository.save(any(MemberEntity.class))).thenReturn(anyValidMemberEntity());

        memberService.deleteMember(123);
        verify(memberRepository, atLeastOnce()).findById(anyLong());
        verify(memberRepository, atLeastOnce()).deleteById(anyLong());
    }

    @Test
    void testThatDeleteMemberThrowsMemberNotFoundException() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(MemberNotFoundException.class, () -> memberService.deleteMember(1));
        verify(memberRepository, atLeastOnce()).findById(anyLong());
        verify(memberRepository, never()).deleteById(anyLong());
    }

}