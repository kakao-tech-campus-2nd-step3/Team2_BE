package jeje.work.aeatbe.service;

import jeje.work.aeatbe.dto.AllergyCategory.AllergyCategoryDTO;
import jeje.work.aeatbe.entity.AllergyCategory;
import jeje.work.aeatbe.repository.AllergyCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * db 시딩을 테스트 하기 위해서 구축된 서비스 입니다.
 * 조회 이외의 목적을 가지고 개발되지 않았으니, 이를 사용하고지 한다면 문의 바랍니다.
 *
 * @author jjh4450git@gmail.com
 */
@Service
public class AllergyCategoryService {

    @Autowired
    private AllergyCategoryRepository allergyCategoryRepository;

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
