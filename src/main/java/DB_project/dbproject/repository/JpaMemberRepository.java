package DB_project.dbproject.repository;

import DB_project.dbproject.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Transactional
public class JpaMemberRepository implements MemberRepository{

    private  final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }



    @Override
    public Member save(Member member) {
        String nativeQuery = "INSERT INTO member (u_id, password, name, phone, nickName) VALUES (?, ?, ?, ?, ?)";
        Query query = em.createNativeQuery(nativeQuery);
        query.setParameter(1, member.getU_id());
        query.setParameter(2, member.getPassword());
        query.setParameter(3, member.getName());
        query.setParameter(4, member.getPhone());
        query.setParameter(5, member.getNickName());
        query.executeUpdate();
        return member;
    }

    @Override
    public Optional<Member> findByu_id(String u_id) {
//        List<Member> result = em.createQuery("select m from Member m",Member.class)
//                .setParameter("u_id",u_id).getResultList();
//        return result.stream().findAny();
        String nativeQuery = "SELECT * FROM member WHERE u_id = :u_id";
        List<Member> result = em.createNativeQuery(nativeQuery, Member.class)
                .setParameter("u_id", u_id)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        String nativeQuery = "SELECT * FROM member";
        return em.createNativeQuery(nativeQuery, Member.class)
                .getResultList();
    }

    @Override
    public Member update(String u_id, Member member) {
        String nativeQuery = "UPDATE member SET password = ?, name = ? ,phone = ?,nickName =?  WHERE u_id = ?";
        Query query = em.createNativeQuery(nativeQuery);


        query.setParameter(1, member.getPassword());
        query.setParameter(2,member.getName());
        query.setParameter(3,member.getPhone());
        query.setParameter(4,member.getNickName());
        query.setParameter(5,u_id);
        query.executeUpdate();
        return member;
    }

    @Override
    public void delete(String u_id) {
        String nativeQuery = "DELETE FROM comment WHERE u_id = ?";
        Query query = em.createNativeQuery(nativeQuery);
        query.setParameter(1, u_id);
        query.executeUpdate();
        nativeQuery = "DELETE FROM video WHERE u_id = ?";
        query = em.createNativeQuery(nativeQuery);
        query.setParameter(1, u_id);
        query.executeUpdate();
        nativeQuery = "DELETE FROM member WHERE u_id = ?";
        query = em.createNativeQuery(nativeQuery);
        query.setParameter(1, u_id);
        query.executeUpdate();

    }
}
