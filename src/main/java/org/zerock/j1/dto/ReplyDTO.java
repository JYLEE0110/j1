package org.zerock.j1.dto;

import lombok.Data;

// 결과값을 담는 DTO modelMapper로 변환
@Data
public class ReplyDTO {

    private Long rno;

    private String replyText;

    private String replyFile;

    private String replyer;

}
