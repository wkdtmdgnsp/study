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

            // 변경 감지 : 1차 캐시에서 스냅샷(find 값)과 Entity(객체) 비교하여 값이 다르면
            // sql 임시 저장소에 저장하고
            // 커밋 시점에 DB에 update 쿼리 보냄.
//            Member member = em.find(Member.class, 150L);
//            member.setName("ZZZZZ");

            // 잘못 된 예
            // persist 해버리면 덮어쓰기 되어서 set 하지 않은 값은 null 됨.
//            if (member.getName().equals("ZZZZZ")) {
//                em.persist(member);
//            }

            // flush 하면 바로 쿼리 생성하여 sql 임시 저장소에 저장하고
            // 데이터베이스에 동기화
//            em.flush();
//            Member member = new Member(200L, "member200");
//            em.persist(member);

            // detach로 member를 사용하여 준영속이 되면 JPA가 관리하지 않기 떄문에
            // 커밋 시점에 아무일도 일어 나지 않는다.
            // clear 해버리면 영속성 컨텐츠를 비우기 떄문에 같은걸 조회하면 db에서 다시 조회한다.
//            Member member = em.find(Member.class, 150L);
//            member.setName("AAAAA");

//            em.detach(member);
//            em.clear();
//            Member member2 = em.find(Member.class, 150L);
//
//            Member member1 = new Member();
//            member1.setUsername("A");
//
//            Member member2 = new Member();
//            member2.setUsername("B");
//
//            Member member3 = new Member();
//            member3.setUsername("C");
//
//            System.out.println("===================");
//
//            // DB SEQ = 1   |   1
//            // DB SEQ = 51  |   2
//            // DB SEQ = 51  |   3
//
//            em.persist(member1); // 1, 51
//            em.persist(member2); // MEM
//            em.persist(member3); // MEM
//
//            System.out.println("member1.getId() = " + member1.getId());
//            System.out.println("member2.getId() = " + member2.getId());
//            System.out.println("member3.getId() = " + member3.getId());
//
//            System.out.println("===================");

            /**
             * 객체를 테이블에 맞춘 데이터 중심 모델링 하면
             * 객체지향적인 협력 관계를 만들 수 없다.
              */

            /**
             * @JoinColumn이 있는 연관관계의 주인에 값을 입력 해야 한다.
             * 역방향은 null 오류
             * 객체 관계를 고려하여 양쪽 다 값을 입력 해야 한다.
             */
            // 저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            em.persist(member);

            team.addMember(member);

            em.flush();
            em.clear();

            Team findTeam = em.find(Team.class, team.getId()); // 1차 캐시
            List<Member> members = findTeam.getMembers();

            System.out.println("===================");
            System.out.println("findTeam = " + findTeam);
            System.out.println("===================");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
