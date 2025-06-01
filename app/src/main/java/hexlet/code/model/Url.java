package hexlet.code.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Url {
    private int id;
    private String name;
    private LocalDateTime createdAt;
    private List<UrlCheck> urlCheck;

    public Url(String name) {
        this.name = name;
    }
}
