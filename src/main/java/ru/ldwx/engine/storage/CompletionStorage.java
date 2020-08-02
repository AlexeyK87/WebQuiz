package ru.ldwx.engine.storage;

import org.springframework.data.domain.Page;
import ru.ldwx.engine.entity.Completion;

public interface CompletionStorage {

    int save(Completion completion);

    Page<Completion> getAllByUserName(Integer pageNo, String userName);
}
