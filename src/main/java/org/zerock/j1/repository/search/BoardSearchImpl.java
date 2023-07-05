package org.zerock.j1.repository.search;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.j1.domain.Board;
import org.zerock.j1.domain.QBoard;
import org.zerock.j1.domain.QReply;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {

    // 부모생성자가 존재해서
    public BoardSearchImpl() {
        super(Board.class);
    }

    // searchType, keyWord, pageing
    @Override
    public Page<Board> search1(String searchType, String keyword, Pageable pageable) {

        // QBoard가 존재해야 쓸 수 있다.
        QBoard board = QBoard.board;

        // sql문을 객체화
        JPQLQuery<Board> query = from(board);

        if (keyword != null && searchType != null) {

            // tc->[t,c]
            String[] searchArr = searchType.split("");

            // 연산자 우선순위( ) 쓰려고 BooleanBuilder
            BooleanBuilder searchBuilder = new BooleanBuilder();

            for (String type : searchArr) {

                switch (type) {
                    case "t" -> searchBuilder.or(board.title.contains(keyword));
                    case "c" -> searchBuilder.or(board.content.contains(keyword));
                    case "w" -> searchBuilder.or(board.writer.contains(keyword));

                }// end for
                query.where(searchBuilder);

            }
            // query.where(board.bno.goe(0L));
        }

        query.where(board.title.contains("1"));

        // 페이지Query를 만드는 코드
        this.getQuerydsl().applyPagination(pageable, query);

        // 목록데이터 실제로 가져옴 => fetch
        List<Board> list = query.fetch();

        long count = query.fetchCount();

        log.info(list);
        log.info(count);

        return new PageImpl<>(list, pageable, count);
    }

    @Override
    public Page<Object[]> searchWithRcnt(String searchType, String keyword, Pageable pageable) {

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;

        // Board와 Reply Join 처리
        JPQLQuery<Board> query = from(board);
        query.leftJoin(reply).on(reply.board.eq(board));

        // 검색 조건
        if (keyword != null && searchType != null) {

            // tc->[t,c]
            String[] searchArr = searchType.split("");

            // 연산자 우선순위( ) 쓰려고 BooleanBuilder
            BooleanBuilder searchBuilder = new BooleanBuilder();

            for (String type : searchArr) {

                switch (type) {
                    case "t" -> searchBuilder.or(board.title.contains(keyword));
                    case "c" -> searchBuilder.or(board.content.contains(keyword));
                    case "w" -> searchBuilder.or(board.writer.contains(keyword));

                }// end for
                query.where(searchBuilder);

            }
        }

        // join결과를 board(객체)로 grouping
        query.groupBy(board);

        // Join한 결과(query) return 값 중에 추출할 컬럼이 여러개일때 Tuple로 처리 => 데이터의 집합
        // 또 다른 테이블과 조인 시 reply가 배로 늘어날 수 있기에 Distinct로 중복 불가능하게 걸어준다.
        JPQLQuery<Tuple> tupleQuery = query.select(board.bno, board.title, board.writer, reply.countDistinct());

        // 페이징 처리
        this.getQuerydsl().applyPagination(pageable, tupleQuery);

        log.info("1--------------");
        // 튜플은 Entity가 관리 하지않기 때문에 비효율적이다.
        // fetch() => 결과 값을 반환.
        List<Tuple> tuples = tupleQuery.fetch();

        log.info("2--------------");
        log.info(tuples);

        // tuple을 Object로 변환 => 람다식
        List<Object[]> arrList = tuples.stream().map(tuple -> tuple.toArray()).collect(Collectors.toList());

        log.info("3--------------");
        long count = tupleQuery.fetchCount();
        log.info("count: " + count);

        return new PageImpl<>(arrList, pageable, count);

    }

}
