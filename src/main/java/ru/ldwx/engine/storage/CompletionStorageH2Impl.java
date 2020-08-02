package ru.ldwx.engine.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import ru.ldwx.engine.entity.Completion;
import ru.ldwx.engine.repository.CompletionRepository;

@Controller
public class CompletionStorageH2Impl implements CompletionStorage {
    private final CompletionRepository repository;

    @Autowired
    public CompletionStorageH2Impl(CompletionRepository repository) {
        this.repository = repository;
    }

    @Override
    public int save(Completion completion) {
        Completion saved = repository.save(completion);
        return saved.getId();
    }

    @Override
    public Page<Completion> getAllByUserName(Integer pageNo, String userName) {
        Pageable paging = PageRequest.of(pageNo, 10);
        return repository.getAllByUserNameOrderByCompletedAtDesc(userName, paging);
    }
}
