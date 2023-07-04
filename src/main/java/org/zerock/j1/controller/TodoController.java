package org.zerock.j1.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.j1.dto.PageResponseDTO;
import org.zerock.j1.dto.TodoDTO;
import org.zerock.j1.service.TodoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
// cors 문제 해결
@CrossOrigin
@Log4j2
public class TodoController {

    // 서비스 객체 주입
    private final TodoService todoService;

    @GetMapping("/list")
    public PageResponseDTO<TodoDTO> list() {

        return todoService.getList();

    }

    @GetMapping("/{tno}")
    public TodoDTO get(@PathVariable Long tno) {

        return todoService.getOne(tno);

    }

    // JSON파일을 객체로 변환 => RequestBody
    @PostMapping("/")
    public TodoDTO register(@RequestBody TodoDTO todoDTO) {

        log.info(todoDTO);

        return todoService.register(todoDTO);
    }

    // delete는 payload가 안 생기므로 PathValiable로 전달
    @DeleteMapping("/{tno}")
    public Map<String, String> delete(@PathVariable("tno") Long tno) {

        todoService.remove(tno);

        return Map.of("result", "success");
    }

    @PutMapping("/{tno}")
    public Map<String,String> update(
        @PathVariable("tno") Long tno,
        @RequestBody TodoDTO todoDTO){

            // 안전하게 하기 위해서
            todoDTO.setTno(tno);

            todoService.modify(todoDTO);

        return Map.of("result","success");
    }

}
