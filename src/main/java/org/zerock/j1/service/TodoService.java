package org.zerock.j1.service;

import org.zerock.j1.domain.Todo;
import org.zerock.j1.dto.PageResponseDTO;
import org.zerock.j1.dto.TodoDTO;

import jakarta.transaction.Transactional;

@Transactional
public interface TodoService {
    
    // List
    PageResponseDTO<TodoDTO> getList();

    // save하면 return타입이 Todo다.
    TodoDTO register(TodoDTO dto);

    TodoDTO getOne(Long tno);

    void remove(Long tno);

    void modify(TodoDTO todoDTO);

    
}
