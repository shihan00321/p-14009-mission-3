package com.back.domain.wiseSaying.repository;


import com.back.JsonParser;
import com.back.WiseSaying;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WiseSayingRepository {
    private final Path directory = Paths.get("db/wiseSaying");
    private final Path lastIdTextFile = directory.resolve("lastId.txt");

    public WiseSayingRepository() {
        try {
            Files.createDirectories(directory);
        } catch (IOException e) {
            throw new RuntimeException("디렉터리 생성 실패", e);
        }
    }

    public void save(WiseSaying wiseSaying) {
        try {
            Path file = directory.resolve(wiseSaying.getId() + ".json");
            String json = JsonParser.wiseSayingToJson(wiseSaying);
            Files.writeString(file, json);
        } catch (IOException e) {
            throw new RuntimeException("명언 저장 중 오류 발생", e);
        }
    }

    public void update(WiseSaying wiseSaying) {
        save(wiseSaying);
    }

    public Optional<WiseSaying> findById(int id) {
        Path file = directory.resolve(id + ".json");
        if (!Files.exists(file)) return Optional.empty();
        try {
            String json = Files.readString(file);
            return Optional.of(JsonParser.jsonToWiseSaying(json));
        } catch (IOException e) {
            throw new RuntimeException("명언 읽기 중 오류 발생", e);
        }
    }

    public List<WiseSaying> findAll() {
        try (Stream<Path> pathStream = Files.list(directory)) {
            return pathStream
                    .filter(path -> path.getFileName().toString().matches("\\d+\\.json"))
                    .map(path -> {
                        try {
                            return JsonParser.jsonToWiseSaying(Files.readString(path));
                        } catch (IOException e) {
                            throw new RuntimeException("파일 읽기 오류: " + path, e);
                        }
                    })
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException("디렉터리 목록 조회 오류", e);
        }
    }

    public void deleteById(int id) {
        Path file = directory.resolve(id + ".json");
        try {
            Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("명언 삭제 실패", e);
        }
    }

    public void saveToJsonFile(List<WiseSaying> list) {
        String json = list.stream()
                .map(JsonParser::wiseSayingToJson)
                .map(String::trim)
                .collect(Collectors.joining(",\n", "[\n  ", "\n]"));
        Path file = directory.resolve("data.json");
        try {
            Files.writeString(file, json);
        } catch (IOException e) {
            throw new RuntimeException("data.json 저장 실패", e);
        }
    }

    public int readLastId() {
        try {
            if (!Files.exists(lastIdTextFile)) return 0;
            return Integer.parseInt(Files.readString(lastIdTextFile));
        } catch (IOException e) {
            throw new RuntimeException("lastId.txt 읽기 실패", e);
        }
    }

    public void writeLastId(int lastId) {
        try {
            Files.writeString(lastIdTextFile, String.valueOf(lastId));
        } catch (IOException e) {
            throw new RuntimeException("lastId.txt 저장 실패", e);
        }
    }
}
