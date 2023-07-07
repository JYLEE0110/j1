package org.zerock.j1.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

// join을 많이 할 시 Projection
public interface BoardReadDTO {
    
    Long getBno();
    String getTitle();
    String getContent();

    LocalDateTime getRegDate();

    LocalDateTime getModDate();

}
