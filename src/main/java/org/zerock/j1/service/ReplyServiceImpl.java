package org.zerock.j1.service;

import java.util.List;
import java.util.Optional;
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

        // 댓글은 검색기능X quertDSL X
        // 댓글 마지막페이지 인지 확인
        boolean last = requestDTO.isLast();

        int pageNum = requestDTO.getPage();

        // last = true이면 => 댓글 마지막 페이지
        if(last){

            long totalCount = replyRepository.getCountBoard(requestDTO.getBno());
            pageNum = (int)(Math.ceil(totalCount / (double)requestDTO.getSize()));

            // 댓글이 없을때 오류 처리
            pageNum = pageNum <= 0 ? 1 :pageNum;

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

    @Override
    public Long register(ReplyDTO replyDTO) {

        Reply reply = modelMapper.map(replyDTO, Reply.class);

        log.info("reply...");
        log.info(reply);

        // 댓글 등록 후 몇 번째 댓글이 등록되었는지 확인
        long newRno = replyRepository.save(reply).getRno();

        return newRno;

    }

    @Override
    public ReplyDTO read(Long rno) {

        Optional<Reply> result = replyRepository.findById(rno);

        Reply reply =result.orElseThrow();

        return modelMapper.map(reply, ReplyDTO.class);

    }

    @Override
    public void remove(Long rno) {

        Optional<Reply> result = replyRepository.findById(rno);

        Reply reply = result.orElseThrow();

        reply.changeText("해당 댓글은 삭제되었습니다.");
        reply.changeFile(null);

        replyRepository.save(reply);

    }

    @Override
    public void modify(ReplyDTO replyDTO) {

        Optional<Reply> result = replyRepository.findById(replyDTO.getRno());

        Reply reply = result.orElseThrow();

        reply.changeText(replyDTO.getReplyText());
        reply.changeFile(replyDTO.getReplyFile());

        replyRepository.save(reply);

    }
    
}
