package hexlet.code.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class UrlCheck {
    private int id;
    private int statusCode;
    private String title;
    private String h1;
    private String description;
    private int urlId;
    private LocalDateTime createdAt;

    public UrlCheck(int statusCode, String title, String h1, String description, int urlId) {
        this.statusCode = statusCode;
        this.title = title;
        this.h1 = h1;
        this.description = description;
        this.urlId = urlId;
    }
}
