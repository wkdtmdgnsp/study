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

            Member member1 = new Member();
            member1.setUsername("관리자1");
            member1.setAge(10);
            member1.setType(MemberType.ADMIN);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("관리자2");
            member2.setAge(10);
            member2.setType(MemberType.ADMIN);
            em.persist(member2);

            // concat 문자열 합치기
//            String query =
//                    "select concat('a', 'b') from Member m";

            // substring 문자열 자르기
//            String query =
//                    "select substring(m.username, 2, 3) from Member m";

            // locate
//            String query =
//                    "select locate('de', 'abcdef') from Member m";

            // size 컬렉션의 크기
//            String query =
//                    "select size(t.members) from Team t";

            // index 컬렉션의 위치 값 찾을때 사용
//            String query =
//                    "select index(t.members) from Team t";

            // 사용자 정의 함수
            String query =
                    "select function('group_concat', m.username ) from Member m";
            List<String> result = em.createQuery(query, String.class)
                    .getResultList();

            for (String s : result) {
                System.out.println("s = " + s);
            }

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
