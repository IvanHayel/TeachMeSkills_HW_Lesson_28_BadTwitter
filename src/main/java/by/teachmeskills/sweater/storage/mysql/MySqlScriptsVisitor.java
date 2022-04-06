package by.teachmeskills.sweater.storage.mysql;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.Value;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

@Value
public class MySqlScriptsVisitor implements FileVisitor<Path> {
    private static final String SCRIPT_EXTENSION = ".sql";

    @NonNull Map<String, String> queries;

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        return FileVisitResult.CONTINUE;
    }

    @Override
    @SneakyThrows(IOException.class)
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        if (file.toString().endsWith(SCRIPT_EXTENSION)) {
            String fileName = file.getFileName().toString().trim().replace(SCRIPT_EXTENSION, "");
            List<String> content = Files.readAllLines(file);
            StringJoiner joiner = new StringJoiner("\n");
            content.forEach(joiner::add);
            queries.put(fileName, joiner.toString());
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        return FileVisitResult.CONTINUE;
    }
}