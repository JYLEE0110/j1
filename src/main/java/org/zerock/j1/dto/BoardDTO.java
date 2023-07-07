package org.zerock.j1.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

// Entity가 만들어 졌으면 동일한 DTO도 만든다
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BoardDTO {
    
    private Long bno;

    private String title;
    
    private String content;
    
    private String writer;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDate;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modDate;

}
