package org.zerock.j1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


// 검색 / page 처리 후 리스트 결과
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BoardListRcntDTO {
    
    private Long bno;
    private String title;
    private String writer;
    private long replyCount;

}
