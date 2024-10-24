package jeje.work.aeatbe.mapper;

import jeje.work.aeatbe.dto.product.ProductAllergyDTO;
import jeje.work.aeatbe.entity.BaseEntity;
import jeje.work.aeatbe.entity.ProductAllergy;

public interface BaseEntityMapper<D extends Record, E extends BaseEntity> {
    /**
     * 엔티티를 DTO로 변환
     * @param entity
     * @return DTO
     */
    D toDTO(E entity);

    /**
     * DTO를 엔티티로 변환
     * 오버 로딩을 통해 toEntity(dto, false)를 호출하는 메서드도 같이 구현하는 것을 권장
     * @param dto DTO
     * @param idRequired id 필드가 필요한지 여부
     * @return 엔티티
     */
    E toEntity(D dto, boolean idRequired);
}
