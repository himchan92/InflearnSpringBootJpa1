package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    //저장
    public void save(Member member) {
        em.persist(member);
    }

    // ID 기준 조회
    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    //전체 조회
    public List<Member> findAll() {
        // JPQL : 엔티티 대상 SQL 수행
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    //성명 기준 조회
    public List<Member> findByName(String name) {
        // JPQL 파라미터 (: 파라미터), 대입
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
            .setParameter("name", name)
            .getResultList();
    }
}
