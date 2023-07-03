package org.zerock.j1.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

// setter는 웬만하면 쓰지않는다.

@Entity
@Table(name="tbl_todo2")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Todo {
    
    // Id는 객체 타입 => persistence Context에서 Hash / equals로 작동하기때문
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tno;

    @Column(length = 300, nullable = false)
    private String title;

}
