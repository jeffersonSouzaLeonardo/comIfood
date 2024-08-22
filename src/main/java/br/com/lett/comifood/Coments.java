package br.com.lett.comifood;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Coments {
    private Integer postId;
    private Integer id;
    private String name;
    private String email;
    private String body;
}
