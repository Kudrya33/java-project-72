package hexlet.code.repository;

import hexlet.code.model.UrlCheck;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static hexlet.code.repository.BaseRepository.dataSource;

public class UrlCheckRepository {

    public static void check(UrlCheck urlCheck) throws SQLException {
        String sql = "INSERT INTO url_checks (status_code, title, h1, description, url_id, created_at)"
                + " VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, urlCheck.getStatusCode());
            preparedStatement.setString(2, urlCheck.getTitle());
            preparedStatement.setString(3, urlCheck.getH1());
            preparedStatement.setString(4, urlCheck.getDescription());
            preparedStatement.setLong(5, urlCheck.getUrlId());
            Timestamp time = new Timestamp(new Date().getTime());
            preparedStatement.setTimestamp(6, time);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            urlCheck.setCreatedAt(time);
            if (resultSet.next()) {
                urlCheck.setId(resultSet.getInt(1));
            } else {
                throw new SQLException("DB have not returned id key");
            }
        }
    }

    public static List<UrlCheck> getCheckList(int urlId) throws SQLException {
        String sql = "Select * FROM url_checks WHERE url_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, urlId);
            ResultSet resultSet = stmt.executeQuery();
            List<UrlCheck> result = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int statusCode = resultSet.getInt("status_code");
                String h1 = resultSet.getString("h1");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                Timestamp createdAt = resultSet.getTimestamp("created_at");
                UrlCheck urlCheck = new UrlCheck(statusCode, h1, title, description, urlId);
                urlCheck.setId(id);
                urlCheck.setCreatedAt(createdAt);
                result.add(urlCheck);
            }
            return result;
        }
    }

    public static List<Map<Integer, String>> getLastChecks() throws SQLException {
        String sql = "SELECT url_id, status_code, created_at FROM (SELECT DISTINCT ON (url_id) * FROM url_checks "
                + "ORDER BY url_id, created_at DESC) sub ORDER BY created_at DESC";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet resultSet = stmt.executeQuery();
            List<Map<Integer, String>> result = new ArrayList<>();
            Map<Integer, String> mapStatusCode = new HashMap<>();
            Map<Integer, String> mapCreatedAt = new HashMap<>();

            while (resultSet.next()) {
                int statusCode = resultSet.getInt("status_code");
                Timestamp createdAt = resultSet.getTimestamp("created_at");
                int urlId = resultSet.getInt("url_id");

                mapStatusCode.put(urlId, String.valueOf(statusCode));
                mapCreatedAt.put(urlId, String.valueOf(createdAt).substring(0, 16));
            }
            result.add(mapStatusCode);
            result.add(mapCreatedAt);
            return result;
        }
    }
}
