package by.teachmeskills.sweater.storage.mysql;

import lombok.NonNull;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class MySqlScriptManager {
    private static final String SCRIPTS_FOLDER_NAME = "sql";
    private static final Path MYSQL_SCRIPTS_PATH;

    static {
        URL sqlUrl = MySqlScriptManager.class.getClassLoader().getResource(SCRIPTS_FOLDER_NAME);
        try {
            assert sqlUrl != null;
            MYSQL_SCRIPTS_PATH = Paths.get(sqlUrl.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

    private static MySqlScriptManager instance;

    public static MySqlScriptManager getInstance() {
        if (instance == null) instance = new MySqlScriptManager();
        return instance;
    }

    private final Map<String, String> queries = new HashMap<>();

    @SneakyThrows(IOException.class)
    private MySqlScriptManager() {
        MySqlScriptsVisitor visitor = new MySqlScriptsVisitor(queries);
        Files.walkFileTree(MYSQL_SCRIPTS_PATH, visitor);
    }

    public String getQuery(@NonNull String queryName) {
        return queries.getOrDefault(queryName, null);
    }
}