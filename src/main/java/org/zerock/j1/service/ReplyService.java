package org.zerock.j1.service;

import org.zerock.j1.dto.PageResponseDTO;
import org.zerock.j1.dto.ReplyDTO;
import org.zerock.j1.dto.ReplyPageRequestDTO;

import jakarta.transaction.Transactional;

@Transactional
public interface ReplyService {

    // 결과값은 항상 PageResponseDTO
    PageResponseDTO<ReplyDTO> list(ReplyPageRequestDTO requestDTO);
    
    Long register(ReplyDTO replyDTO);

    ReplyDTO read(Long rno);

    void remove(Long rno);

    void modify(ReplyDTO replyDTO);

}
