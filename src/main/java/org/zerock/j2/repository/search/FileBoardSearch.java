package org.zerock.j2.repository.search;

import org.zerock.j2.dto.FileBoardListDTO;
import org.zerock.j2.dto.PageRequestDTO;
import org.zerock.j2.dto.PageResponseDTO;

public interface FileBoardSearch {

    // 목록데이터 - 검색조건
    // 연관관계가 걸려있다면 limit이 제대로 걸리는지 확인 해야 된다.
    
    PageResponseDTO<FileBoardListDTO> list(PageRequestDTO pageRequestDTO);
       
}