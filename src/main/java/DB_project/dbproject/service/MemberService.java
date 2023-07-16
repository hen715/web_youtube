package DB_project.dbproject.service;

import DB_project.dbproject.domain.Member;
import DB_project.dbproject.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long join(Member member) {

        validateDuplicateMember(member);
        doNotMakeNonMem(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByu_id(member.getU_id()).ifPresent((m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");//이미 존재하는 회원일 시 오류발생
        }));
    }
    private void doNotMakeNonMem(Member member){
        if(member.getU_id().equals("비회원"))
            throw new IllegalStateException("비회원이라는 아이디로 가입할수 없습니다.");
    }

    public boolean login(String username, String password) {
        Optional<Member> optionalMember = memberRepository.findByu_id(username);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            if (member.getPassword().equals(password)) {
                return true; // 로그인 성공
            }
        }
        return false; // 로그인 실패

    }

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findByu_id(String u_id){return memberRepository.findByu_id(u_id);}
    public void update(String u_id,Member member){
        validateDuplicateMember(member);
        memberRepository.update(u_id,member);}
    public void delete(String u_id){memberRepository.delete(u_id);}
}
