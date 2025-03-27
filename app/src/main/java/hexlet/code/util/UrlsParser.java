package hexlet.code.util;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class UrlsParser {
    public static String get(String url) throws MalformedURLException {
        URL parse = URI.create(url).toURL();
        String result = parse.getProtocol() + "://" + parse.getHost();
        if (parse.getPort() != -1) {
            result = result + ":" + parse.getPort();
        }
        return result;
    }
}
