package pl.studyshare.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import pl.studyshare.domain.Category;
import pl.studyshare.repository.CategoryRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService
{
    private final CategoryRepository categoryRepository;

    public List<Category> findAll()
    {
        return categoryRepository.findAll();
    }

    public Category findById(Long id)
    {
        return categoryRepository.findById(id).orElseThrow();
    }
}
