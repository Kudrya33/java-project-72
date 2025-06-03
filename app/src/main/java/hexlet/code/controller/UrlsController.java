package hexlet.code.controller;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.UrlsParser;
import hexlet.code.util.NamedRoutes;
import hexlet.code.dto.urls.BuildPage;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.Unirest;
import kong.unirest.core.UnirestException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlsController {

    public static void index(Context ctx) {
        BuildPage page = new BuildPage();
        ctx.render("pages/index.jte", model("page", page));
    }

    public static void buildUrls(Context ctx) throws SQLException {
        String inputUrl = ctx.formParam("url");

        if (inputUrl == null || inputUrl.trim().isEmpty()) {
            BuildPage page = new BuildPage(inputUrl);
            ctx.sessionAttribute("flash", "Поле не должно быть пустым");
            ctx.sessionAttribute("flash-type", "danger");
            page.setFlash(ctx.consumeSessionAttribute("flash"));
            page.setFlashType(ctx.consumeSessionAttribute("flash-type"));
            ctx.render("pages/index.jte", model("page", page));
            return;
        }

        String normalizedUrl;
        try {
            normalizedUrl = UrlsParser.get(inputUrl.trim());
        } catch (MalformedURLException e) {
            BuildPage page = new BuildPage(inputUrl);
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.sessionAttribute("flash-type", "danger");
            page.setFlash(ctx.consumeSessionAttribute("flash"));
            page.setFlashType(ctx.consumeSessionAttribute("flash-type"));
            ctx.render("pages/index.jte", model("page", page));
            return;
        }

        Optional<Url> existingUrl = UrlRepository.findByName(normalizedUrl);

        if (existingUrl.isPresent()) {
            ctx.sessionAttribute("flash", "Сайт уже существует");
            ctx.sessionAttribute("flash-type", "warning");
            ctx.redirect(NamedRoutes.urlPath(existingUrl.get().getId()));
        } else {
            Url url = new Url(normalizedUrl);
            UrlRepository.save(url);
            ctx.sessionAttribute("flash", "Сайт успешно добавлен");
            ctx.sessionAttribute("flash-type", "success");
            ctx.redirect(NamedRoutes.urlsPath());
        }
    }

    public static void urls(Context ctx) throws SQLException {
        List<Url> urls = UrlRepository.getEntities();
        Map<Integer, UrlCheck> lastChecks = UrlCheckRepository.getLastChecks();
        UrlsPage page = new UrlsPage(urls, lastChecks);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flash-type"));
        ctx.render("pages/urls.jte", model("page", page));
    }

    public static void show(Context ctx) throws SQLException {
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        Url url = UrlRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("site not found"));
        UrlPage page = new UrlPage(url);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flash-type"));
        List<UrlCheck> urlChecks = UrlCheckRepository.getCheckList(id);
        page.getUrl().setUrlCheck(urlChecks);
        ctx.render("pages/url.jte", model("page", page));
    }

    public static void check(Context ctx) throws SQLException {
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        Url name = UrlRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("current id " + id + " not found"));

        try {
            HttpResponse<String> response = Unirest.get(name.getName()).asString();
            Document doc = Jsoup.parse(response.getBody());
            int statusCode = response.getStatus();
            String title = doc.title();
            String h1 = doc.select("h1").text();
            String description = doc.select("meta[name=description]").attr("content");
            UrlCheck urlCheck = new UrlCheck(statusCode, title, h1, description, id);
            UrlCheckRepository.check(urlCheck);
            ctx.sessionAttribute("flash", "Сайт успешно проверен");
            ctx.sessionAttribute("flash-type", "success");
            ctx.redirect(NamedRoutes.urlPath(id));
        } catch (SQLException | UnirestException e) {
            UrlPage page = new UrlPage(name);
            ctx.sessionAttribute("flash", "не удается установить соединение");
            ctx.sessionAttribute("flash-type", "danger");
            page.setFlash(ctx.consumeSessionAttribute("flash"));
            page.setFlashType(ctx.consumeSessionAttribute("flash-type"));
            ctx.render("pages/url.jte", model("page", page));
        }
    }
}
