package ru.ldwx.engine.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Controller;
import ru.ldwx.engine.entity.Completion;

@Controller
public interface CompletionRepository extends PagingAndSortingRepository<Completion, Integer> {
    Page<Completion> getAllByUserNameOrderByCompletedAtDesc(String userName, Pageable paging);
}
