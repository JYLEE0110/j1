package org.zerock.j1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

// 쿼리스트링 => parameter
// extends PageRequestDTO
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReplyPageRequestDTO extends PageRequestDTO {
    
    private Long bno;

    @Builder.Default
    private int page = 1;

    // size는 고정이라 Setter는 주면 안된다. => 일단 해놓음?
    @Builder.Default
    private int size = 20;
    
    private boolean last;
}
