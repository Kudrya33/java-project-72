package hexlet.code.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class Url {
    private int id;
    private String name;
    private Timestamp createdAt;
    private List<UrlCheck> urlCheck;

    public Url(String name) {
        this.name = name;
    }
}
