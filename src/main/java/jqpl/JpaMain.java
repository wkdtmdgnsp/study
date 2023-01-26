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

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);

            member.setTeam(team);

            em.persist(member);

            em.flush();
            em.clear();

            // inner join
//            String qlString = "select m from Member m join m.team t";

            // left join
//            String qlString = "select m from Member m left join m.team t";

            // 세타(cross) join
//            String qlString = "select m from Member m, Team t where m.username = t.name";

            // join on
//            String qlString = "select m from Member m left join m.team t on t.name='teamA'";

            // 연관 관계 없는 엔티티 조인
            String qlString = "select m from Member m left join Team t on m.username = t.name";

            List<Member> result = em.createQuery(qlString, Member.class)
                    .getResultList();

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
