package org.zerock.j1.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.j1.domain.Reply;
import org.zerock.j1.dto.PageResponseDTO;
import org.zerock.j1.dto.ReplyDTO;
import org.zerock.j1.dto.ReplyPageRequestDTO;
import org.zerock.j1.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;

    private final ModelMapper modelMapper;

    @Override
    public PageResponseDTO<ReplyDTO> list(ReplyPageRequestDTO requestDTO) {

        // 댓글 마지막페이지 인지 확인
        boolean last = requestDTO.isLast();

        int pageNum = requestDTO.getPage();

        // last = true이면 => 댓글 마지막 페이지
        if(last){

            long totalCount = replyRepository.getCountBoard(requestDTO.getBno());

            pageNum = (int)(Math.ceil(totalCount / (double)requestDTO.getSize()));

        }

        Pageable pageable = PageRequest.of(pageNum-1, requestDTO.getSize(), Sort.by("rno").ascending());

        Page<Reply> result = replyRepository.listBoard(requestDTO.getBno(), pageable);

        log.info("---------------");
        // log.info(result.getNumber());
        // log.info(result.getContent());

        long totalReplyCount = result.getTotalElements();

       List<ReplyDTO> dtoList = result.get().map(en -> modelMapper.map(en, ReplyDTO.class)).collect(Collectors.toList());

       // ReplyPageRequestDTO에 RequestDTO Extends로 해결
       PageResponseDTO<ReplyDTO> responseDTO = new PageResponseDTO<>(dtoList, totalReplyCount, requestDTO);
       // page 값이 1로 고정되서 처리하는 부분
        responseDTO.setPage(pageNum);

        return responseDTO;
    }
    
}
