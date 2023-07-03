package org.zerock.j1.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
//cors 문제 해결
@CrossOrigin
@Log4j2
public class TodoController {
    
    //서비스 객체 주입
    private final TodoService todoService;

    @GetMapping("/list")
    public PageResponseDTO<TodoDTO> list(){

        return todoService.getList();

    }

    // JSON파일을 객체로 변환 => RequestBody
    @PostMapping("/")
    public TodoDTO register(@RequestBody TodoDTO todoDTO){

        log.info(todoDTO);

        return todoService.register(todoDTO);

    }

}
