package org.zerock.j1.dto;

import lombok.Data;

//validation도 dto로 들어간다.
// entitiy를 변환하는 코드
@Data
public class TodoDTO {
    
    private Long tno;
    private String title;

}
