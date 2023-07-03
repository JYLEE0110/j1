package org.zerock.j1.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.j1.domain.Todo;
import org.zerock.j1.dto.PageResponseDTO;
import org.zerock.j1.dto.TodoDTO;
import org.zerock.j1.repository.TodoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Service
@Log4j2
public class TodoServiceImpl implements TodoService{

    private final TodoRepository todoRepository;

    private final ModelMapper modelMapper;

    @Override
    public PageResponseDTO<TodoDTO> getList() {

        Pageable pageable = PageRequest.of(0, 20, Sort.by("tno").descending());

        Page<Todo> result = todoRepository.findAll(pageable);
        
        // result.get().map(entity -> modelMapper.map(entity, TodoDTO.class));
        List<TodoDTO> dtoList = result.getContent().stream()
            .map(todo -> modelMapper.map(todo, TodoDTO.class))
            .collect(Collectors.toList());

        PageResponseDTO<TodoDTO> response = new PageResponseDTO<>();
        response.setDtoList(dtoList);

        return response;
    }

    @Override
    public TodoDTO register(TodoDTO dto) {

        
        /* DTO 를 Entity로 변환 tno = null*/
        Todo entity = modelMapper.map(dto,Todo.class);

        // Entity로 변환되면서 DB에 저장되면서 tno autoIncrement
        Todo result = todoRepository.save(entity);

        /* Entity를 다시 DTO로 반환 => TodoDTO(tno, title) 반환*/
        return modelMapper.map(result,TodoDTO.class);
    }
    
}
