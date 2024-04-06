package nvb.dev.springadvancedproject.service.impl;

import lombok.RequiredArgsConstructor;
import nvb.dev.springadvancedproject.exception.EntityNotStorableException;
import nvb.dev.springadvancedproject.exception.member.FullNameExistsException;
import nvb.dev.springadvancedproject.exception.member.MemberNotFoundException;
import nvb.dev.springadvancedproject.model.MemberEntity;
import nvb.dev.springadvancedproject.repository.MemberRepository;
import nvb.dev.springadvancedproject.service.MemberService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheManager = "redisCacheManager")
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public MemberEntity saveMember(MemberEntity memberEntity) {
        try {
            if (fullNameExists(memberEntity.getFullName()))
                throw new FullNameExistsException(memberEntity.getFullName());
            return memberRepository.save(memberEntity);
        } catch (DataIntegrityViolationException e) {
            throw new EntityNotStorableException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(key = "#memberId", value = "member")
    public Optional<MemberEntity> getMemberById(long memberId) {
        return Optional.ofNullable(memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId)));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(key = "#fullName", value = "member")
    public Optional<MemberEntity> getMemberByFullName(String fullName) {
        return Optional.ofNullable(memberRepository.findMemberByFullNameIgnoreCase(fullName)
                .orElseThrow(() -> new MemberNotFoundException(fullName)));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "memberList")
    public List<MemberEntity> getAllMembers() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        if (memberEntityList.isEmpty()) throw new MemberNotFoundException();
        return memberEntityList;
    }

    @Override
    public MemberEntity updateMember(long memberId, MemberEntity memberEntity) {
        Optional<MemberEntity> optionalMember = memberRepository.findById(memberId);
        if (optionalMember.isPresent()) {

            MemberEntity existingMember = optionalMember.get();

            existingMember.setFullName(memberEntity.getFullName());
            existingMember.setEmail(memberEntity.getEmail());
            existingMember.setAddress(memberEntity.getAddress());

            return memberRepository.save(existingMember);

        } else {
            throw new MemberNotFoundException(memberId);
        }
    }

    @Override
    public MemberEntity partialUpdate(long memberId, Map<String, Object> memberEntity) {
        Optional<MemberEntity> foundMember = memberRepository.findById(memberId);
        if (foundMember.isPresent()) {

            memberEntity.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(MemberEntity.class, key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, foundMember.get(), value);
            });

            return memberRepository.save(foundMember.get());

        } else {
            throw new MemberNotFoundException(memberId);
        }
    }

    @Override
    @CacheEvict(key = "#memberId", value = "member", beforeInvocation = true)
    public void deleteMember(long memberId) {
        Optional<MemberEntity> optionalMember = memberRepository.findById(memberId);
        optionalMember.ifPresentOrElse(
                _ -> memberRepository.deleteById(memberId),
                () -> {
                    throw new MemberNotFoundException(memberId);
                }
        );
    }

    private boolean fullNameExists(String fullName) {
        return memberRepository.findMemberByFullName(fullName).isPresent();
    }
}
