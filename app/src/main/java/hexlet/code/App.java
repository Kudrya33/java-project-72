package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import hexlet.code.controller.UrlsController;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import lombok.extern.slf4j.Slf4j;
import hexlet.code.repository.BaseRepository;
import hexlet.code.util.NamedRoutes;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

@Slf4j
public class App {

    public static void main(String[] args) throws SQLException {
        Javalin app = getApp();
        String port = System.getenv().getOrDefault("PORT", "7070");
        app.start(Integer.parseInt(port));
    }

    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        TemplateEngine templateEngine = TemplateEngine.create(codeResolver, ContentType.Html);
        return templateEngine;
    }

    public static Javalin getApp() throws SQLException {
        HikariConfig hikariConfig = new HikariConfig();
        String urlDataBase = System.getenv().getOrDefault("JDBC_DATABASE_URL", "jdbc:h2:mem:project;");
        hikariConfig.setJdbcUrl(urlDataBase);

        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        InputStream url = App.class.getClassLoader().getResourceAsStream("schema.sql");
        String sql = new BufferedReader(new InputStreamReader(url))
                .lines().collect(Collectors.joining("\n"));

        log.info(sql);
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
        BaseRepository.dataSource = dataSource;

        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte(createTemplateEngine()));
        });

        app.get(NamedRoutes.homePath(), UrlsController::index);
        app.post(NamedRoutes.urlsPath(), UrlsController::buildUrls);
        app.get(NamedRoutes.urlsPath(), UrlsController::urls);
        app.get(NamedRoutes.urlPath("{id}"), UrlsController::show);
        app.post(NamedRoutes.urlChecks("{id}"), UrlsController::check);

        return app;
    }
}
