package DB_project.dbproject.repository;

import DB_project.dbproject.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findByu_id(String u_id);
    List<Member> findAll();
    Member update(String u_id,Member member);
    void delete(String u_id);

    }

