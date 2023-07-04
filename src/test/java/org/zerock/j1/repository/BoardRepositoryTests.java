package org.zerock.j1.repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.zerock.j1.domain.Board;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void testInsert() {

        for (int i = 0; i < 100; i++) {
            Board board = Board.builder()
                    .title("Sample Title" + i)
                    .content("Sample Content" + i)
                    .writer("user00" + (i % 10))
                    .build();

            boardRepository.save(board);
        }

    }

    @Test
    public void testRead() {

        Long tno = 1L;

        // Optional => null대신
        Optional<Board> result = boardRepository.findById(tno);

        log.info("=====================================");

        Board board = result.orElseThrow();

        log.info(board);

    }

    // update시 제목만 넣으면 제목 제외 column들이 지워진다.
    @Test
    public void testUpdate() {

        Long tno = 1L;

        // 조회 후 수정
        Optional<Board> result = boardRepository.findById(tno);
        Board board = result.orElseThrow();

        board.changeTitle("Update Title");

        boardRepository.save(board);

    }

    @Test
    public void testQuery1() {

        // findByTitleContaining("1") => 1이 포함된 title like = 1
        java.util.List<Board> list = boardRepository.findByTitleContaining("1");
        log.info(list.size());
        log.info(list);

    }

    @Test
    public void testQuery1_1() {

        // findByTitleContaining("1") => 1이 포함된 title like = 1
        java.util.List<Board> list = boardRepository.listTitle("1");
        log.info(list.size());
        log.info(list);
    }

    // 원하는 값만 불러올때 bno, title만
    @Test
    public void testQuery1_2() {

        java.util.List<Object[]> list = boardRepository.listTitle2("1");
        log.info(list.size());

        list.forEach(arr -> log.info(Arrays.toString(arr)));
    }

    // Paging 처리
    @Test
    public void testQuery1_3() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page<Object[]> result = boardRepository.listTitle2("1", pageable);

        log.info(result);
    }

    @Test
    public void testQuery2() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        // findByTitleContaining("1") => 1이 포함된 title like = 1
        // 내용에 1이 포함된 게시물의 페이징 처리
        Page<Board> result = boardRepository.findByContentContaining("1", pageable);

        log.info("----------------------");
        log.info(result);
    }

    // update 테스트에서 Transactional을 붙여준다. Modify는 기본적으로 rollback이기때문에 @Commit을 붙여준다
    @Commit
    @Transactional
    @Test
    public void testModify(){

        Long bno = 100L;
        String title = "Modified Title 100";

        int count = boardRepository.modifyTitle(title, bno);

        log.info(count);

    }

    // nativeQuery
    @Test
    public void testNative(){

        List<Object[]> result =  boardRepository.listNative();

        result.forEach(arr -> log.info(Arrays.toString(arr)));

    }

    @Test
    public void testSearch1(){

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page<Board> result = boardRepository.search1("tcw","1",pageable);

        log.info(result.getTotalElements());

        result.get().forEach(b -> log.info(b));
    }
}
