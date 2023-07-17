package org.zerock.j2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member {

    // ID 역활
    @Id
    private String email;

    private String pw;

    private String nickname;

    private boolean admin;
}
