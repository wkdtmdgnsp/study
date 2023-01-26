package jqpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            // 엔티티 프로젝션
//            List<Member> result = em.createQuery("select m from Member m", Member.class)
//                    .getResultList();
//
//            Member findMember = result.get(0);
//            findMember.setAge(20);
            
            // 명시적 조인 
//            List<Team> result = em.createQuery("select t from Member m join m.team t", Team.class)
//                    .getResultList();

            // 묵시적 조인
//            List<Team> result = em.createQuery("select m.team from Member m", Team.class)
//                    .getResultList();

            // 임베디드 타입 프로젝션
            // 임베디드 타입만으로는 안되고 그것의 엔티티로 시작
//            em.createQuery("select o.address from Order o", Address.class)
//                    .getResultList();

            // 스칼라 타입 프로젝션
//                em.createQuery("select distinct m.username, m.age from Member m")
//                    .getResultList();

            // Query 타입 조회
//            List resultList = em.createQuery("select distinct m.username, m.age from Member m")
//                    .getResultList();
//
//            Object o = resultList.get(0);
//            Object[] result = (Object[]) o;
//            System.out.println("result[0] = " + result[0]);
//            System.out.println("result[1] = " + result[1]);

            // Object[] 타입 조회
//            List<Object[]> resultList = em.createQuery("select distinct m.username, m.age from Member m")
//                    .getResultList();
//
//            Object[] result = resultList.get(0);
//            System.out.println("result[0] = " + result[0]);
//            System.out.println("result[1] = " + result[1]);

            // new 명령어로 조회
            List<MemberDTO> result = em.createQuery("select new jqpl.MemberDTO (m.username, m.age) from Member m", MemberDTO.class)
                    .getResultList();

            MemberDTO memberDTO = result.get(0);
            System.out.println("memberDTO.getUsername() = " + memberDTO.getUsername());
            System.out.println("memberDTO.getAge() = " + memberDTO.getAge());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }

}
