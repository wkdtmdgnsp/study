package hellojpa;

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
            // create
//            Member member = new Member();
//            member.setId(2L);
//            member.setName("HelloB");

//            Member findMember = em.find(Member.class, 1L);

            // delete
//            em.remove(findMember);

            // update
            // 값을 변경하면 commit 시점에 변경을 감지하여 update 됨.
//            findMember.setName("HelloJpa");

            // read
//            List<Member> result = em.createQuery("select m from Member m", Member.class)
//                    .setFirstResult(5)
//                    .setMaxResults(8)
//                    .getResultList();
//
//            for (Member member : result) {
//                System.out.println("member.getName() = " + member.getName());
//            }

            // 비영속 : 객체를 생성한 상태
//            Member member = new Member();
//            member.setId(101L);
//            member.setName("HelloJPA");

            // 영속 : 객체를 EntityManager에 저장한 상태
            // 커밋이후 DB에 저장 됨.
//            em.persist(member);

            // 1차 캐시에 의해 동등
//            Member findMember1 = em.find(Member.class, 101L);
//            Member findMember2 = em.find(Member.class, 101L);
//            System.out.println("result = " + (findMember1 == findMember2));

            // 트랜잭션이 커밋 되는 시점까지 쓰기 지연
//            Member member1 = new Member(150L, "A");
//            Member member2 = new Member(160L, "B");
//            em.persist(member1);
//            em.persist(member2);
//            System.out.println("===================");

            // 변경 감지 : 1차 캐시에서 스냅샷(find 값)과 Entity(객체) 비교하여 값이 다르면
            // 커밋 시점에 DB에 update 쿼리 보냄.
            Member member = em.find(Member.class, 150L);
            member.setName("ZZZZZ");

            // 잘못 된 예
            // persist 해버리면 덮어쓰기 되어서 set 하지 않은 값은 null 됨.
//            if (member.getName().equals("ZZZZZ")) {
//                em.persist(member);
//            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
