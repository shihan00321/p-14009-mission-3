package com.back.domain.wiseSaying.service;

import com.back.WiseSaying;
import com.back.domain.wiseSaying.repository.WiseSayingRepository;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class WiseSayingService {
    private final WiseSayingRepository repository = new WiseSayingRepository();
    private final AtomicInteger lastId;

    public WiseSayingService() {
        this.lastId = new AtomicInteger(repository.readLastId());
    }

    public WiseSaying findWiseSaying(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("%d번 명언은 존재하지 않습니다.", id)));
    }

    public int registerWiseSaying(String content, String author) {
        int newId = lastId.incrementAndGet();
        WiseSaying wiseSaying = new WiseSaying(newId, content, author);
        repository.save(wiseSaying);
        repository.writeLastId(newId);
        return newId;
    }

    public void updateWiseSaying(int id, String content, String author) {
        repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("%d번 명언은 존재하지 않습니다.", id)));
        WiseSaying wiseSaying = new WiseSaying(id, content, author);
        repository.update(wiseSaying);
    }

    public void deleteWiseSaying(int id) {
        repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("%d번 명언은 존재하지 않습니다.", id)));
        repository.deleteById(id);
    }

    public List<WiseSaying> findWiseSayingList() {
        return repository.findAll().stream()
                .sorted(Comparator.comparingInt(WiseSaying::getId).reversed())
                .toList();
    }

    public void build() {
        List<WiseSaying> sorted = repository.findAll().stream()
                .sorted(Comparator.comparingInt(WiseSaying::getId))
                .toList();
        repository.saveToJsonFile(sorted);
    }
}

