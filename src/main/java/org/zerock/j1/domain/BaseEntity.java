package org.zerock.j1.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

// 추상 클래스로 선언 단독으로 인스턴스 생성을 못하게 설정 => 상속을 통해서만 사용 가능
@MappedSuperclass // 테이블로 만들어 지지 않게 선언
@EntityListeners(value = { AuditingEntityListener.class }) // 감사 기능 => 엔티티의 생성 및 수정 시간을 자동으로 기록하는 작업
@Getter
public abstract class BaseEntity {
    
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    private LocalDateTime modDate;

}
