package nvb.dev.springadvancedproject.service;

import nvb.dev.springadvancedproject.model.MemberEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MemberService {

    MemberEntity saveMember(MemberEntity memberEntity);

    Optional<MemberEntity> getMemberById(long memberId);

    Optional<MemberEntity> getMemberByFullName(String fullName);

    List<MemberEntity> getAllMembers();

    MemberEntity updateMember(long memberId, MemberEntity memberEntity);

    MemberEntity partialUpdate(long memberId, Map<String, Object> memberEntity);

    void deleteMember(long memberId);

}
