package jqpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setTeam(teamB);
            em.persist(member3);

            /**
             * 벌크 연산
             * 단건은 Dirty Checking 통해 UPDATE
             * 여러건(대량)을 한 번에 수정하거나 삭제
             * 영속성 컨텍스트를 무시하고 DB에 직접 쿼리
             * 벌크 연산을 먼저 실행 수행후 컨텍스트를 초기화 해줘야함
             * flush 자동 호출 commit, query
             */
            int resultCount = em.createQuery("update Member m set m.age = 20")
                    .executeUpdate();

//            System.out.println("resultCount = " + resultCount);

//            // 컨텍스트를 초기화 하지 않으면
//            // 1차캐시에 있는 0 0 0 으로 표기 됨
//            System.out.println("member1 = " + member1.getAge());
//            System.out.println("member2 = " + member2.getAge());
//            System.out.println("member3 = " + member3.getAge());

            // 초기화후 다시 가져와야 제대로 나이 반영
            em.clear();

            Member findMember = em.find(Member.class, member1.getId());

            System.out.println("findMember.getAge() = " + findMember.getAge());

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
