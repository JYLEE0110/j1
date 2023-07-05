package org.zerock.j1.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.j1.domain.Board;
import org.zerock.j1.repository.search.BoardSearch;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearch {

    // Containint => like 기능 / query
    List<Board> findByTitleContaining(String title);

    // 파라미터 1개여도 @Param 붙여줘야한다.
    // 조인처리할때 유용하다.
    // context먼저 거쳐서 DB에 접근해야한다.
    // insert / update / delete는 DB에 바로 접근하므로 좋지않은 방법이다.
    @Query("select b from Board b where b.title like %:title%")
    List<Board> listTitle(@Param("title") String title);

    // 원하는 값만 불러올 때 return타입이 Board가 아닌 Object[]이 된다.
    @Query("select b.bno, b.title from Board b where b.title like %:title%")
    List<Object[]> listTitle2(@Param("title") String title);


    // Overoading => return타입과 파라미터 수가 다르지만 메소드 명은 같다.(다른 메소드)
    @Query("select b.bno, b.title from Board b where b.title like %:title%")
    Page<Object[]> listTitle2(@Param("title") String title, Pageable pageable);

    @Modifying
    @Query("update Board b set b.title = :title where b.bno = :bno")
    int modifyTitle(@Param("title") String title, @Param("bno") Long bno);

    // 내용 검색 Page처리까지
    Page<Board> findByContentContaining(String content, Pageable pageable);

    // nativeQuery => 긴급할때 쓰인다. 
    // JPA의 목적은 DB에 독립적이어야한다 nativeQuery는 DB에 종속적이다. => 긴급할때쓰임
    @Query(value = "select * from t_board", nativeQuery = true)
    List<Object[]> listNative();

    // bno, title, writer => Object[]
    @Query("select b.bno, b.title, b.writer, count(r) from Board b left outer join Reply r on r.board = b group by b order by b.bno desc")
    List<Object[]> getListWithRcnt();

}
