package jeje.work.aeatbe.service;

import jeje.work.aeatbe.entity.FreeFromCategory;
import jeje.work.aeatbe.exception.FreeFromCategoryNotFoundException;
import jeje.work.aeatbe.repository.FreeFromCategoryRepository;

public class FreeFromCategoryService {
    final private FreeFromCategoryRepository freeFromCategoryRepository;

    public FreeFromCategoryService(FreeFromCategoryRepository freeFromCategoryRepository) {
        this.freeFromCategoryRepository = freeFromCategoryRepository;
    }

    public FreeFromCategory findById(Long id) {
        return freeFromCategoryRepository.findById(id).orElseThrow(
                () -> new FreeFromCategoryNotFoundException("해당 id의 상품 알레르기가 존재하지 않습니다." + id)
        );
    }
}
