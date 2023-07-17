package org.zerock.j2.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// JSON으로 데이터를 보내기위한 DTO
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

    private  String email;
    private  String pw;
    private  String nickname;
    private  boolean admin;
}
