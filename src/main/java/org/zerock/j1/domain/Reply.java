package org.zerock.j1.domain;

import com.querydsl.core.annotations.Generated;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "t_reply")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
// reply조회할때 board는 제외하고 가져온다.
@ToString(exclude = "board")
public class Reply {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private String replyText;

    private String replyFile;

    private String replyer;

    // ERD 관계 알아서 Board ID를 FK로 잡아준다.
    // 연관관계를 걸때는 기본 LAZY를 걸고 필요한 순간에만 호출하게 설계한다. 
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    public void changeText(String text){
        this.replyText = text;
    }

    public void changeFile(String fileName){
        this.replyFile = fileName;
    }
    
}
