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

            Team teamA = new Team();
            teamA.setName("teamA");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("teamB");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member1");
            member2.setTeam(teamB);
            em.persist(member2);

            em.flush();
            em.clear();

            /**
             * fetch LAZY, EAGER
             */
//            Member m = em.find(Member.class, member1.getId());
//
//            System.out.println("m = " + m.getTeam().getClass());
//
//            System.out.println("==============");
//            System.out.println("m.getTeam().getName() = " + m.getTeam().getName());
//            System.out.println("==============");

            List<Member> members = em.createQuery("select m from Member m join fetch m.team", Member.class)
                    .getResultList();

            // SQL : select * from Member
            // SQL : select * from Member where TEAM_ID = xxx

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
