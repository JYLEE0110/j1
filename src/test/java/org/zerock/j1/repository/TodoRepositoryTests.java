package org.zerock.j1.repository;

import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.j1.domain.Todo;
import org.zerock.j1.dto.TodoDTO;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class TodoRepositoryTests {
    
    @Autowired
    private TodoRepository todoRepository;

    // entity를 DTO로 변환하기 위해서 주입
    @Autowired
    ModelMapper modelMapper;

    @Test
    public void testRead(){

        Long tno = 100L;
        Optional<Todo> result = todoRepository.findById(tno);

        Todo entity = result.orElseThrow();

        log.info("Entitiy........");
        log.info(entity);

        //DTO로 변환
        TodoDTO dto = modelMapper.map(entity, TodoDTO.class);

        log.info(dto);
    }


    @Test
    public void testInsert(){

        IntStream.rangeClosed(1, 100).forEach(i -> {

            Todo todo = Todo.builder().title("Title"+i).build();

            // 리턴타입이 todo selectKey할 필요없이 들어가있다.
           Todo result =  todoRepository.save(todo);

           //Id값이 없으므로 select를 안날린다. => 새로운 값이 들어왔다라고 인식
           log.info(result);

        });

    }

    
        @Test
        public void testPaging(){
            
            // size가 오버하면 count를 안날림
            Pageable pageable = 
                PageRequest.of(0, 10, Sort.by("tno").descending());

                Page<Todo> result = todoRepository.findAll(pageable);

                log.info(result);
            
        }

}
