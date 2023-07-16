package DB_project.dbproject.repository;

import DB_project.dbproject.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemoryMemberRepository implements MemberRepository{

    private static ArrayList<Member> listOfMembers = new ArrayList<Member>();
    private static long sequence = 0L;
    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        member.setU_id(member.getU_id());
        member.setPassword(member.getPassword());
        member.setName(member.getName());
        member.setPhone(member.getPhone());
        member.setNickName(member.getNickName());
        listOfMembers.add(member);
        return member;
    }
    @Override
    public Optional<Member> findByu_id(String u_id) {
        return listOfMembers.stream().filter(member->member.getU_id().equals(u_id)).findAny();
    }

    @Override
    public List<Member> findAll() {
        return listOfMembers;
    }

    @Override
    public Member update(String u_id, Member member) {
        return null;
    }

    @Override
    public void delete(String u_id) {

    }
}
