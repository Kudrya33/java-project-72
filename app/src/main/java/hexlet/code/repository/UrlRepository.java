package hexlet.code.repository;

import hexlet.code.model.Url;
import org.postgresql.util.PGTimestamp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class UrlRepository extends BaseRepository {

    public static void save(Url url) throws SQLException {
        String sql = "INSERT INTO urls (name, created_at) VALUES (?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, url.getName());
            PGTimestamp time = new PGTimestamp(new Date().getTime());
            preparedStatement.setTimestamp(2, time);
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            url.setCreatedAt(time);
            if (generatedKeys.next()) {
                url.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("BD have not returned id key");
            }
        }
    }

    public static List<Url> getEntities() throws SQLException {
        String sql = "SELECT * FROM urls";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet resultSet = stmt.executeQuery();
            List<Url> result = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Url url = new Url(name);
                url.setId(id);
                result.add(url);
            }
            return result;
        }
    }

    public static Optional<Url> findByName(String url) throws SQLException {
        String sql = "SELECT * FROM urls WHERE name = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, url);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                Timestamp createdAt = resultSet.getTimestamp("created_at");
                Url result = new Url(url);
                result.setCreatedAt(createdAt);
                result.setId(id);
                return Optional.of(result);
            }
            return Optional.empty();
        }
    }

    public static Optional<Url> find(int id) throws SQLException {
        String sql = "SELECT * FROM urls WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                Timestamp createdAt = resultSet.getTimestamp("created_at");
                Url url = new Url(name);
                url.setCreatedAt(createdAt);
                url.setId(id);
                return Optional.of(url);
            }
            return Optional.empty();
        }
    }
}
