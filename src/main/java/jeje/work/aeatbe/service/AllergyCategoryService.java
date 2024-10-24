package jeje.work.aeatbe.service;

import jeje.work.aeatbe.dto.AllergyCategory.AllergyCategoryDTO;
import jeje.work.aeatbe.entity.AllergyCategory;
import jeje.work.aeatbe.exception.AllergyCategoryNotFoundException;
import jeje.work.aeatbe.repository.AllergyCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 알러지 카테고리 서비스 레이어
 *
 * @author jjh4450git@gmail.com
 */
@Service
public class AllergyCategoryService {

    private final AllergyCategoryRepository allergyCategoryRepository;

    public AllergyCategoryService(AllergyCategoryRepository allergyCategoryRepository) {
        this.allergyCategoryRepository = allergyCategoryRepository;
    }

    /**
     * id에 해당하는 알러지 카테고리를 반환합니다.
     *
     * @param id 알러지 카테고리 id
     * @return id에 해당하는 알러지 카테고리
     */
    public AllergyCategory findById(Long id) {
        return allergyCategoryRepository.findById(id).orElseThrow(
                () -> new AllergyCategoryNotFoundException("해당 id의 알러지 카테고리가 존재하지 않습니다.")
        );
    }

    /**
     * 알러지 카테고리를 전부 반환합니다.
     *
     * @return id를 포함한 알러지 카테고리
     */
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
