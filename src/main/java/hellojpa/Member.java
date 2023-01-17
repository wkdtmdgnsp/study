package hellojpa;

import javax.persistence.*;

// @SequenceGenerator 시퀀스 정보 직접 지정
// @SequenceGenerator(name = "member_seq_generator", sequenceName = "member_seq")
// @TableGenerator 테이블을 활용해 시퀀스 처럼 사용한다. 성능은 떨어지지만 모든 DB에서 적용 가능, 잘안씀
@Entity
public class Member {

    // @Id 직접 할당
    // @GeneratedValue 자동 할당
    /**
     *  IDENTITY 기본 키 생성을 데이터베이스에 위임
     *  sql 실행해야 기본키를 알수 있기 떄문에 persist 하면 바로 쿼리 생성하여 DB에 날림
     *  그래서 임시 저장소에 모은뒤 커밋 시점에 sql 실행을 하지 않음
     */

    /**
     *      SEQUENCE 시퀀스를 만들어 사용
     *      allocationSize = 1 일때,
     *      persist 할떄마다 DB 시퀀스 값을 가져옴. 쿼리는 임시 저장소에 저장하고 커밋시점에 실행
     *      allocationSize = 50 (기본값) 일때,
     *      처음 persist 할때 DB 시퀀스에서 50개를 가져와 놓고,
     *      이후 메모리에서 50개 사용후 다시 DB 시퀀스 값을 가져옴 (성능 최적화)
     *      여러대의 서버에서도 동시성 문제 없음
     */
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

//    @Column(name = "TEAM_ID")
//    private Long teamId;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", team=" + team +
                '}';
    }
}
