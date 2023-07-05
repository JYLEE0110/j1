package org.zerock.j1.repository.search;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.j1.domain.Board;

public interface BoardSearch {
    
    // 검색 용도
    Page<Board> search1(String searchType, String keyword, Pageable pageable);

    // 조인 후 => bno, title, writer, 댓글 개수 뽑기 위해서 Object[]
    Page<Object[]> searchWithRcnt(String searchType, String keyword, Pageable pageable);

}
