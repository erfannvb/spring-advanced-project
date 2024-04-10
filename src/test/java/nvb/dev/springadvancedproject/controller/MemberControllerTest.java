package nvb.dev.springadvancedproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nvb.dev.springadvancedproject.assembler.MemberModelAssembler;
import nvb.dev.springadvancedproject.dto.MemberDto;
import nvb.dev.springadvancedproject.mapper.MemberMapper;
import nvb.dev.springadvancedproject.model.MemberEntity;
import nvb.dev.springadvancedproject.security.JwtService;
import nvb.dev.springadvancedproject.security.impl.UserDetailsServiceImpl;
import nvb.dev.springadvancedproject.service.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static nvb.dev.springadvancedproject.MotherObject.*;
import static nvb.dev.springadvancedproject.constant.Constant.BEARER;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    JwtService jwtService;

    @Mock
    MemberMapper memberMapper;

    @Mock
    MemberModelAssembler memberModelAssembler;

    @MockBean
    UserDetailsServiceImpl userDetailsService;

    @MockBean
    MemberService memberService;

    private String generateToken() {
        UserDetails user = User.builder()
                .username("anyString")
                .password("anyString")
                .roles("ADMIN")
                .build();
        return jwtService.generateToken(user);
    }

    @Test
    void testThatSaveMemberReturnsHttpStatusCode201Created() throws Exception {
        when(memberService.saveMember(any(MemberEntity.class))).thenReturn(anyValidMemberEntity());
        when(memberMapper.toMemberEntity(anyValidMemberDto())).thenReturn(anyValidMemberEntity());
        when(memberMapper.toMemberDto(anyValidMemberEntity())).thenReturn(anyValidMemberDto());
        when(memberModelAssembler.toModel(any(MemberEntity.class))).thenReturn(null);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String memberJson = objectMapper.writeValueAsString(anyValidMemberDto());

        mockMvc.perform(post("/api/member/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                        .content(memberJson)
                )
                .andExpect(status().isCreated());
    }

    @Test
    void testThatSaveMemberReturnsHttpStatusCode400BadRequestWhenFullNameIsBlank() throws Exception {
        MemberEntity memberEntity = anyValidMemberEntity();
        memberEntity.setFullName("");

        MemberDto memberDto = anyValidMemberDto();
        memberDto.setFullName("");

        when(memberService.saveMember(any(MemberEntity.class))).thenReturn(memberEntity);
        when(memberMapper.toMemberEntity(memberDto)).thenReturn(memberEntity);
        when(memberMapper.toMemberDto(memberEntity)).thenReturn(memberDto);
        when(memberModelAssembler.toModel(any(MemberEntity.class))).thenReturn(null);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String memberJson = objectMapper.writeValueAsString(memberDto);

        mockMvc.perform(post("/api/member/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                        .content(memberJson)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testThatSaveMemberReturnsHttpStatusCode400BadRequestWhenFullNameIsNull() throws Exception {
        MemberEntity memberEntity = anyValidMemberEntity();
        memberEntity.setFullName(null);

        MemberDto memberDto = anyValidMemberDto();
        memberDto.setFullName(null);

        when(memberService.saveMember(any(MemberEntity.class))).thenReturn(memberEntity);
        when(memberMapper.toMemberEntity(memberDto)).thenReturn(memberEntity);
        when(memberMapper.toMemberDto(memberEntity)).thenReturn(memberDto);
        when(memberModelAssembler.toModel(any(MemberEntity.class))).thenReturn(null);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String memberJson = objectMapper.writeValueAsString(memberDto);

        mockMvc.perform(post("/api/member/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                        .content(memberJson)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testThatSaveMemberReturnsHttpStatusCode400BadRequestWhenEmailIsBlank() throws Exception {
        MemberEntity memberEntity = anyValidMemberEntity();
        memberEntity.setEmail("");

        MemberDto memberDto = anyValidMemberDto();
        memberDto.setEmail("");

        when(memberService.saveMember(any(MemberEntity.class))).thenReturn(memberEntity);
        when(memberMapper.toMemberEntity(memberDto)).thenReturn(memberEntity);
        when(memberMapper.toMemberDto(memberEntity)).thenReturn(memberDto);
        when(memberModelAssembler.toModel(any(MemberEntity.class))).thenReturn(null);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String memberJson = objectMapper.writeValueAsString(memberDto);

        mockMvc.perform(post("/api/member/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                        .content(memberJson)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testThatSaveMemberReturnsHttpStatusCode400BadRequestWhenEmailIsNull() throws Exception {
        MemberEntity memberEntity = anyValidMemberEntity();
        memberEntity.setEmail(null);

        MemberDto memberDto = anyValidMemberDto();
        memberDto.setEmail(null);

        when(memberService.saveMember(any(MemberEntity.class))).thenReturn(memberEntity);
        when(memberMapper.toMemberEntity(memberDto)).thenReturn(memberEntity);
        when(memberMapper.toMemberDto(memberEntity)).thenReturn(memberDto);
        when(memberModelAssembler.toModel(any(MemberEntity.class))).thenReturn(null);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String memberJson = objectMapper.writeValueAsString(memberDto);

        mockMvc.perform(post("/api/member/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                        .content(memberJson)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testThatSaveMemberReturnsHttpStatusCode400BadRequestWhenTypeIsNotValid() throws Exception {
        MemberEntity memberEntity = anyValidMemberEntity();
        memberEntity.setType("Something");

        MemberDto memberDto = anyValidMemberDto();
        memberDto.setType("Something");

        when(memberService.saveMember(any(MemberEntity.class))).thenReturn(memberEntity);
        when(memberMapper.toMemberEntity(memberDto)).thenReturn(memberEntity);
        when(memberMapper.toMemberDto(memberEntity)).thenReturn(memberDto);
        when(memberModelAssembler.toModel(any(MemberEntity.class))).thenReturn(null);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String memberJson = objectMapper.writeValueAsString(memberDto);

        mockMvc.perform(post("/api/member/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                        .content(memberJson)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testThatGetMemberByIdReturnsHttpStatusCode200Ok() throws Exception {
        when(memberService.getMemberById(anyLong())).thenReturn(Optional.of(anyValidMemberEntity()));
        when(memberMapper.toMemberEntity(anyValidMemberDto())).thenReturn(anyValidMemberEntity());
        when(memberMapper.toMemberDto(anyValidMemberEntity())).thenReturn(anyValidMemberDto());
        when(memberModelAssembler.toModel(anyValidMemberEntity())).thenReturn(EntityModel.of(anyValidMemberDto()));
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(get("/api/member/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                )
                .andExpect(status().isOk());
    }

    @Test
    void testThatGetMemberByIdReturnsHttpStatusCode404NotFound() throws Exception {
        when(memberService.getMemberById(anyLong())).thenReturn(Optional.empty());
        when(memberMapper.toMemberEntity(anyValidMemberDto())).thenReturn(anyValidMemberEntity());
        when(memberMapper.toMemberDto(anyValidMemberEntity())).thenReturn(anyValidMemberDto());
        when(memberModelAssembler.toModel(anyValidMemberEntity())).thenReturn(EntityModel.of(anyValidMemberDto()));
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(get("/api/member/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void testThatGetMemberBuFullNameReturnsHttpStatusCode200Ok() throws Exception {
        when(memberService.getMemberByFullName(anyString())).thenReturn(Optional.of(anyValidMemberEntity()));
        when(memberMapper.toMemberEntity(anyValidMemberDto())).thenReturn(anyValidMemberEntity());
        when(memberMapper.toMemberDto(anyValidMemberEntity())).thenReturn(anyValidMemberDto());
        when(memberModelAssembler.toModel(anyValidMemberEntity())).thenReturn(EntityModel.of(anyValidMemberDto()));
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(get("/api/member")
                        .queryParam("fullName", "dummy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                )
                .andExpect(status().isOk());
    }

    @Test
    void testThatGetMemberBuFullNameReturnsHttpStatusCode404NotFound() throws Exception {
        when(memberService.getMemberByFullName(anyString())).thenReturn(Optional.empty());
        when(memberMapper.toMemberEntity(anyValidMemberDto())).thenReturn(anyValidMemberEntity());
        when(memberMapper.toMemberDto(anyValidMemberEntity())).thenReturn(anyValidMemberDto());
        when(memberModelAssembler.toModel(anyValidMemberEntity())).thenReturn(EntityModel.of(anyValidMemberDto()));
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(get("/api/member")
                        .queryParam("fullName", "dummy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void testThatGetAllMembersReturnsHttpStatusCode200Ok() throws Exception {
        when(memberService.getAllMembers()).thenReturn(List.of(anyValidMemberEntity()));
        when(memberMapper.toMemberEntity(anyValidMemberDto())).thenReturn(anyValidMemberEntity());
        when(memberMapper.toMemberDto(anyValidMemberEntity())).thenReturn(anyValidMemberDto());
        when(memberModelAssembler.toModel(anyValidMemberEntity())).thenReturn(EntityModel.of(anyValidMemberDto()));
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(get("/api/member/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                )
                .andExpect(status().isOk());
    }

    @Test
    void testThatGetAllMembersReturnsHttpStatusCode404NotFound() throws Exception {
        when(memberService.getAllMembers()).thenReturn(Collections.emptyList());
        when(memberMapper.toMemberEntity(anyValidMemberDto())).thenReturn(anyValidMemberEntity());
        when(memberMapper.toMemberDto(anyValidMemberEntity())).thenReturn(anyValidMemberDto());
        when(memberModelAssembler.toModel(anyValidMemberEntity())).thenReturn(EntityModel.of(anyValidMemberDto()));
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(get("/api/member/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void testThatUpdateMemberReturnsHttpStatusCode200Ok() throws Exception {
        when(memberService.updateMember(anyLong(), any(MemberEntity.class))).thenReturn(anyValidMemberEntity());
        when(memberMapper.toMemberEntity(anyValidMemberDto())).thenReturn(anyValidMemberEntity());
        when(memberMapper.toMemberDto(anyValidMemberEntity())).thenReturn(anyValidMemberDto());
        when(memberModelAssembler.toModel(anyValidMemberEntity())).thenReturn(EntityModel.of(anyValidMemberDto()));
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String memberJson = objectMapper.writeValueAsString(anyValidMemberDto());

        mockMvc.perform(put("/api/member/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                        .content(memberJson)
                )
                .andExpect(status().isOk());
    }

    @Test
    void testThatUpdateMemberReturnsHttpStatusCode400BadRequestWhenFullNameIsBlank() throws Exception {
        MemberEntity memberEntity = anyValidMemberEntity();
        memberEntity.setFullName("");

        MemberDto memberDto = anyValidMemberDto();
        memberDto.setFullName("");

        when(memberService.updateMember(anyLong(), any(MemberEntity.class))).thenReturn(memberEntity);
        when(memberMapper.toMemberEntity(memberDto)).thenReturn(memberEntity);
        when(memberMapper.toMemberDto(memberEntity)).thenReturn(memberDto);
        when(memberModelAssembler.toModel(memberEntity)).thenReturn(EntityModel.of(memberDto));
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String memberJson = objectMapper.writeValueAsString(memberDto);

        mockMvc.perform(put("/api/member/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                        .content(memberJson)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testThatUpdateMemberReturnsHttpStatusCode400BadRequestWhenFullNameIsNull() throws Exception {
        MemberEntity memberEntity = anyValidMemberEntity();
        memberEntity.setFullName(null);

        MemberDto memberDto = anyValidMemberDto();
        memberDto.setFullName(null);

        when(memberService.updateMember(anyLong(), any(MemberEntity.class))).thenReturn(memberEntity);
        when(memberMapper.toMemberEntity(memberDto)).thenReturn(memberEntity);
        when(memberMapper.toMemberDto(memberEntity)).thenReturn(memberDto);
        when(memberModelAssembler.toModel(memberEntity)).thenReturn(EntityModel.of(memberDto));
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String memberJson = objectMapper.writeValueAsString(memberDto);

        mockMvc.perform(put("/api/member/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                        .content(memberJson)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testThatUpdateMemberReturnsHttpStatusCode400BadRequestWhenEmailIsBlank() throws Exception {
        MemberEntity memberEntity = anyValidMemberEntity();
        memberEntity.setEmail("");

        MemberDto memberDto = anyValidMemberDto();
        memberDto.setEmail("");

        when(memberService.updateMember(anyLong(), any(MemberEntity.class))).thenReturn(memberEntity);
        when(memberMapper.toMemberEntity(memberDto)).thenReturn(memberEntity);
        when(memberMapper.toMemberDto(memberEntity)).thenReturn(memberDto);
        when(memberModelAssembler.toModel(memberEntity)).thenReturn(EntityModel.of(memberDto));
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String memberJson = objectMapper.writeValueAsString(memberDto);

        mockMvc.perform(put("/api/member/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                        .content(memberJson)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testThatUpdateMemberReturnsHttpStatusCode400BadRequestWhenEmailIsNull() throws Exception {
        MemberEntity memberEntity = anyValidMemberEntity();
        memberEntity.setEmail(null);

        MemberDto memberDto = anyValidMemberDto();
        memberDto.setEmail(null);

        when(memberService.updateMember(anyLong(), any(MemberEntity.class))).thenReturn(memberEntity);
        when(memberMapper.toMemberEntity(memberDto)).thenReturn(memberEntity);
        when(memberMapper.toMemberDto(memberEntity)).thenReturn(memberDto);
        when(memberModelAssembler.toModel(memberEntity)).thenReturn(EntityModel.of(memberDto));
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String memberJson = objectMapper.writeValueAsString(memberDto);

        mockMvc.perform(put("/api/member/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                        .content(memberJson)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testThatUpdateMemberReturnsHttpStatusCode400BadRequestWhenTypeIsNotValid() throws Exception {
        MemberEntity memberEntity = anyValidMemberEntity();
        memberEntity.setType("Something");

        MemberDto memberDto = anyValidMemberDto();
        memberDto.setType("Something");

        when(memberService.updateMember(anyLong(), any(MemberEntity.class))).thenReturn(memberEntity);
        when(memberMapper.toMemberEntity(memberDto)).thenReturn(memberEntity);
        when(memberMapper.toMemberDto(memberEntity)).thenReturn(memberDto);
        when(memberModelAssembler.toModel(memberEntity)).thenReturn(EntityModel.of(memberDto));
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String memberJson = objectMapper.writeValueAsString(memberDto);

        mockMvc.perform(put("/api/member/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                        .content(memberJson)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testThatPartialUpdateReturnsHttpStatusCode200Ok() throws Exception {
        when(memberService.partialUpdate(anyLong(), any(Map.class))).thenReturn(anyValidMemberEntity());
        when(memberMapper.toMemberEntity(anyValidMemberDto())).thenReturn(anyValidMemberEntity());
        when(memberMapper.toMemberDto(anyValidMemberEntity())).thenReturn(anyValidMemberDto());
        when(memberModelAssembler.toModel(anyValidMemberEntity())).thenReturn(EntityModel.of(anyValidMemberDto()));
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String memberJson = objectMapper.writeValueAsString(anyValidMemberDto());

        mockMvc.perform(patch("/api/member/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                        .content(memberJson)
                )
                .andExpect(status().isOk());
    }

    @Test
    void testThatDeleteMemberReturnsHttpStatusCode204NoContent() throws Exception {
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(delete("/api/member/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                )
                .andExpect(status().isNoContent());
    }

}