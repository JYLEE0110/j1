package org.zerock.j1.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

// 모든 Entity는 ID(PK)가 있어야 한다.
// Entitiy Class
// 생성 -> Entity instance
@Entity
@Table(name = "tbl_sample")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Sample {

    @Id
    private String keyCol;

    private String first;

    private String last;

    private String addr;
    
    private String zipCode;
    
    private String city;
}
