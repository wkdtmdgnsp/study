package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

//            Member member1 = new Member();
//            member1.setUsername("member1");
//            em.persist(member1);
//
//            Member member2 = new Member();
//            member2.setUsername("member2");
//            em.persist(member2);
//
//            em.flush();
//            em.clear();
//
//            Member m1 = em.find(Member.class, member1.getId());
//            Member m2 = em.getReference(Member.class, member2.getId());
//
//            logic(m1, m2);

            Member member1 = new Member();
            member1.setUsername("member1");
            em.persist(member1);

            em.flush();
            em.clear();

            /**
             * 1차 캐시에 엔티티가 이미 있으면
             * getReference를 호출해도 실제 엔티티를 반환
             * 반대도 마찬가지
             */
//            Member m1 = em.find(Member.class, member1.getId());
//            System.out.println("m1.getClass() = " + m1.getClass());
//
//            Member reference = em.getReference(Member.class, member1.getId());
//            System.out.println("reference.getClass() = " + reference.getClass());
//
//            System.out.println("a == a : " + (m1 == reference));

//            Member refMember = em.getReference(Member.class, member1.getId());
//            System.out.println("m1.getClass() = " + refMember.getClass()); // Proxy
//
//            Member findMember = em.find(Member.class, member1.getId());
//            System.out.println("reference.getClass() = " + findMember.getClass()); // Member
//
//            System.out.println("a == a : " + (refMember == findMember));

            /**
             * EntityManager 종료 되어 있고,
             * 프록시 객체를 초기화 하면
             * LazyInitializationException 에러 발생
             */
            
//            Member refMember = em.getReference(Member.class, member1.getId());
//            System.out.println("m1.getClass() = " + refMember.getClass()); // Proxy
//
////            em.detach(refMember);
////            em.close();
//            em.clear();
//
//            refMember.getUsername();

            Member refMember = em.getReference(Member.class, member1.getId());
            System.out.println("m1.getClass() = " + refMember.getClass()); // Proxy
//            refMember.getUsername(); // 강제 초기화
//            System.out.println("isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(refMember));
            Hibernate.initialize(refMember); // 강제 초기화

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }

    /**
     * 프록시 객체는 원본 엔티티를 상속 받음
     * 프록시 객체 비교시 instanceof 사용
     */
//    private static void logic(Member m1, Member m2) {
//        System.out.println("m1 == m2 : " + (m1 instanceof Member));
//        System.out.println("m1 == m2 : " + (m2 instanceof Member));
//    }

}
