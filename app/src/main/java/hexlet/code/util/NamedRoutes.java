package hexlet.code.util;

public class NamedRoutes {
    public static String homePath() {
        return "/";
    }

    public static String urlsPath() {
        return "/urls";
    }

    public static String urlPath(String id) {
        return "/urls/" + id;
    }

    public static String urlPath(long id) {
        return urlPath(String.valueOf(id));
    }

    public static String urlChecks(String id) {
        return "/urls/" + id + "/checks";
    }

    public static String urlChecks(long id) {
        return urlChecks(String.valueOf(id));
    }
}
