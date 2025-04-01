package hexlet.code.dto.urls;

import hexlet.code.dto.BasePage;
import hexlet.code.model.Url;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UrlsPage extends BasePage {
    private List<Url> urls;
    private List<Map<Integer, String>> checklist;

    public UrlsPage(List<Url> urls) {
        this.urls = urls;
    }
}
