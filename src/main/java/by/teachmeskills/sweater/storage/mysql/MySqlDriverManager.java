package by.teachmeskills.sweater.storage.mysql;

import lombok.Cleanup;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

@Log
public class MySqlDriverManager implements AutoCloseable {
    private static final String DATABASE_PROPERTY_FILE_NAME = "database.properties";
    private static final String DATABASE_URL_PROPERTY = "database.connection.URL";
    private static final Path DATABASE_PROPERTY_PATH;
    private static final Properties databaseProperties = new Properties();

    static {
        try {
            URL databasePropertiesUrl = MySqlDriverManager.class
                    .getClassLoader().getResource(DATABASE_PROPERTY_FILE_NAME);
            assert databasePropertiesUrl != null;
            DATABASE_PROPERTY_PATH = Paths.get(databasePropertiesUrl.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
        loadDatabaseProperties();
        registerDriver();
    }

    private static MySqlDriverManager instance;

    public static MySqlDriverManager getInstance() {
        if (instance == null) instance = new MySqlDriverManager();
        return instance;
    }

    @SneakyThrows(IOException.class)
    private static void loadDatabaseProperties() {
        @Cleanup InputStream propertiesStream = Files.newInputStream(DATABASE_PROPERTY_PATH);
        databaseProperties.load(propertiesStream);
    }

    @SneakyThrows(SQLException.class)
    private static void registerDriver() {
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        log.info("JDBC MySQL Driver successfully registered.");
    }

    private Connection connection;

    private MySqlDriverManager() {
        connection = setConnection();
    }

    @SneakyThrows(SQLException.class)
    private Connection setConnection() {
        Properties databasePropertiesWithoutUrl = (Properties) MySqlDriverManager.databaseProperties.clone();
        String connectionUrl = (String) databasePropertiesWithoutUrl.remove(DATABASE_URL_PROPERTY);
        Connection establishedConnection = DriverManager.getConnection(connectionUrl, databasePropertiesWithoutUrl);
        log.info(String.format("Connected to %s", establishedConnection.getMetaData().getURL()));
        return establishedConnection;
    }

    @SneakyThrows(SQLException.class)
    public Connection getConnection() {
        if (connection == null || connection.isClosed()) connection = setConnection();
        return connection;
    }

    @SneakyThrows(SQLException.class)
    public Statement createStatement() {
        if (connection == null || connection.isClosed()) connection = setConnection();
        return connection.createStatement();
    }

    @SneakyThrows(SQLException.class)
    public PreparedStatement prepareStatement(@NonNull String query) {
        if (connection == null || connection.isClosed()) connection = setConnection();
        return connection.prepareStatement(query);
    }

    @Override
    @SneakyThrows(SQLException.class)
    public void close() {
        if (connection != null && !connection.isClosed()) {
            String connectionUrl = connection.getMetaData().getURL();
            DriverManager.deregisterDriver(new com.mysql.cj.jdbc.Driver());
            connection.close();
            log.info(String.format("Connection to %s successfully closed.", connectionUrl));
        }
    }
}