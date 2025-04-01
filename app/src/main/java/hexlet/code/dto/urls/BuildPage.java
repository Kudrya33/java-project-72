package hexlet.code.dto.urls;

import hexlet.code.dto.BasePage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BuildPage extends BasePage {
    private String name;
}
