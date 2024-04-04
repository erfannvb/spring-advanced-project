package nvb.dev.springadvancedproject.mapper;

import nvb.dev.springadvancedproject.dto.MemberDto;
import nvb.dev.springadvancedproject.model.MemberEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static nvb.dev.springadvancedproject.MotherObject.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {MemberMapperImpl.class})
class MemberMapperTest {

    @Autowired
    MemberMapper memberMapper;

    @Test
    void toMemberDto() {
        MemberDto memberDto = memberMapper.toMemberDto(anyValidMemberEntity());
        assertEquals(anyValidMemberEntity().getId(), memberDto.getId());
        assertEquals(anyValidMemberEntity().getFullName(), memberDto.getFullName());
        assertEquals(anyValidMemberEntity().getEmail(), memberDto.getEmail());
        assertEquals(anyValidMemberEntity().getAddress(), memberDto.getAddress());
        assertEquals(anyValidMemberEntity().getType(), memberDto.getType());
        assertEquals(anyValidMemberEntity().getBorrowedBooks(), memberDto.getBorrowedBooks());
    }

    @Test
    void toMemberDto_Null_Fields() {
        MemberDto memberDto = memberMapper.toMemberDto(anyInvalidMemberEntity());
        assertNull(memberDto.getId());
        assertNull(memberDto.getFullName());
        assertNull(memberDto.getEmail());
        assertNull(memberDto.getAddress());
        assertNull(memberDto.getType());
        assertNull(memberDto.getBorrowedBooks());
    }

    @Test
    void toMemberDto_Null() {
        MemberDto memberDto = memberMapper.toMemberDto(null);
        assertNull(memberDto);
    }

    @Test
    void toMemberEntity() {
        MemberEntity memberEntity = memberMapper.toMemberEntity(anyValidMemberDto());
        assertEquals(anyValidMemberDto().getId(), memberEntity.getId());
        assertEquals(anyValidMemberDto().getFullName(), memberEntity.getFullName());
        assertEquals(anyValidMemberDto().getEmail(), memberEntity.getEmail());
        assertEquals(anyValidMemberDto().getAddress(), memberEntity.getAddress());
        assertEquals(anyValidMemberDto().getType(), memberEntity.getType());
        assertEquals(anyValidMemberDto().getBorrowedBooks(), memberEntity.getBorrowedBooks());
    }

    @Test
    void toMemberEntity_Null_Fields() {
        MemberEntity memberEntity = memberMapper.toMemberEntity(anyInvalidMemberDto());
        assertNull(memberEntity.getId());
        assertNull(memberEntity.getFullName());
        assertNull(memberEntity.getEmail());
        assertNull(memberEntity.getAddress());
        assertNull(memberEntity.getType());
    }

    @Test
    void toMemberEntity_Null() {
        MemberEntity memberEntity = memberMapper.toMemberEntity(null);
        assertNull(memberEntity);
    }
}