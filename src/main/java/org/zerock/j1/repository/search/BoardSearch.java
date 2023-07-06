package org.zerock.j1.repository.search;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.j1.domain.Board;
import org.zerock.j1.dto.BoardListRcntDTO;
import org.zerock.j1.dto.PageRequestDTO;
import org.zerock.j1.dto.PageResponseDTO;

public interface BoardSearch {
    
    // Version1 검색 용도
    Page<Board> search1(String searchType, String keyword, Pageable pageable);

    // Version2 조인 후 => bno, title, writer, 댓글 개수 뽑기 위해서 Object[]
    Page<Object[]> searchWithRcnt(String searchType, String keyword, Pageable pageable);

    // Version3 검색 / 페이지 리스트 결과 => 최종 포트폴리오용 
    PageResponseDTO<BoardListRcntDTO> searchDTORcnt(PageRequestDTO requestDTO);

    // Sort조건을 파라미터로 받게하는게 유연한 코드이다.
    default Pageable makePageable(PageRequestDTO requestDTO){

        Pageable pageable = PageRequest.of(
            requestDTO.getPage()-1, 
            requestDTO.getSize(),
            Sort.by("bno").descending());

        return pageable;
    }
}
