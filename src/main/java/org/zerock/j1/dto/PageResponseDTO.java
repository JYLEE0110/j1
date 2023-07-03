package org.zerock.j1.dto;


import java.util.List;

import lombok.Data;

// 제네릭은 complie하면 object로 나온다.
@Data
public class PageResponseDTO<E> {
    
    private List<E> dtoList;

    

}
