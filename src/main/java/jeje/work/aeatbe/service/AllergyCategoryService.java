package jeje.work.aeatbe.service;

import jeje.work.aeatbe.dto.allergyCategory.AllergyCategoryDTO;
import jeje.work.aeatbe.entity.AllergyCategory;
import jeje.work.aeatbe.exception.AllergyCategoryNotFoundException;
import jeje.work.aeatbe.mapper.allergyCategory.AllergyCategoryMapper;
import jeje.work.aeatbe.repository.AllergyCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 알러지 카테고리 서비스 레이어
 *
 */
@Service
@RequiredArgsConstructor
public class AllergyCategoryService {

    private final AllergyCategoryRepository allergyCategoryRepository;
    private final AllergyCategoryMapper allergyCategoryMapper;

    /**
     * id에 해당하는 알러지 카테고리를 반환합니다.
     *
     * @param id 알러지 카테고리 id
     * @return id에 해당하는 알러지 카테고리
     */
    @Transactional(readOnly = true)
    protected AllergyCategory findById(Long id) {
        return allergyCategoryRepository.findById(id).orElseThrow(
                () -> new AllergyCategoryNotFoundException("해당 id의 알러지 카테고리가 존재하지 않습니다.")
        );
    }

    /**
     * 테이블에서 태그를 기반으로 알러지 카테고리를 조회하는 메서드
     *
     * @param allergyType 조회할 알러지 카테고리의 이름
     * @return 조회된 알러지 카테고리
     * @throws AllergyCategoryNotFoundException 조회된 알러지 카테고리가 없을 경우 예외 발생
     */
    @Transactional(readOnly = true)
    public AllergyCategoryDTO getProductAllergyByType(String allergyType) {
        AllergyCategory allergyCategory = allergyCategoryRepository.findByAllergyType(allergyType)
                .orElseThrow(() -> new AllergyCategoryNotFoundException("해당 알러지 카테고리가 존재하지 않습니다."));
        return allergyCategoryMapper.toDTO(allergyCategory);
    }

    /**
     * 알러지 카테고리를 전부 반환합니다.
     *
     * @return id를 포함한 알러지 카테고리
     */
    @Transactional(readOnly = true)
    public List<AllergyCategoryDTO> getAllAllergyCategory() {
        List<AllergyCategory> allergyCategories = allergyCategoryRepository.findAll();
        return allergyCategories.stream()
                .map(data -> AllergyCategoryDTO.builder()
                        .id(data.getId())
                        .allergyType(data.getAllergyType())
                        .build())
                .collect(Collectors.toList());
    }
}
