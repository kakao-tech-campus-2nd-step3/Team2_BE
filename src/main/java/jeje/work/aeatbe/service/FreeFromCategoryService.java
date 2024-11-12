package jeje.work.aeatbe.service;

import jeje.work.aeatbe.dto.freeFromCategory.FreeFromCategoryDTO;
import jeje.work.aeatbe.entity.FreeFromCategory;
import jeje.work.aeatbe.exception.FreeFromCategoryNotFoundException;
import jeje.work.aeatbe.mapper.freeFromCategory.FreeFromCategoryMapper;
import jeje.work.aeatbe.repository.FreeFromCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 상품 알레르기 서비스 레이어
 *
 * @see ProductAllergyService
 */
@Service
@RequiredArgsConstructor
public class FreeFromCategoryService {
    final private FreeFromCategoryRepository freeFromCategoryRepository;
    final private FreeFromCategoryMapper freeFromCategoryMapper;

    /**
     * id에 해당하는 상품 알레르기 엔티티를 DB에서 조회합니다.
     *
     * @param id 조회할 상품 알레르기 엔티티의 ID
     * @return 조회된 상품 알레르기 엔티티
     * @throws FreeFromCategoryNotFoundException 조회된 상품 알레르기 엔티티가 없을 경우 예외 발생
     */
    @Transactional(readOnly = true)
    protected FreeFromCategory findById(Long id) {
        return freeFromCategoryRepository.findById(id).orElseThrow(
                () -> new FreeFromCategoryNotFoundException("해당 id의 상품 알레르기가 존재하지 않습니다." + id)
        );
    }

    /**
     * 테이블에서 태그를 기반으로 상품 알레르기 카테고리를 조회하는 메서드
     *
     * @param s 조회할 상품 알레르기 카테고리의 이름
     * @return 조회된 상품 알레르기 카테고리
     * @throws FreeFromCategoryNotFoundException 조회된 상품 알레르기 카테고리가 없을 경우 예외 발생
     */
    @Transactional(readOnly = true)
    public FreeFromCategoryDTO getProductFreeFromByType(String s) {
        FreeFromCategory freeFromCategory = freeFromCategoryRepository.findByFreeFromType(s)
                .orElseThrow(() -> new FreeFromCategoryNotFoundException("해당 알레르기 카테고리가 존재하지 않습니다."));
        return freeFromCategoryMapper.toDTO(freeFromCategory);
    }
}
