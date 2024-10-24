package jeje.work.aeatbe.mapper;

public interface BaseResponseDTOMapper<D extends Record, R extends Record> {

    /**
     * 응답용 DTO로 변환
     * 오버 로딩을 통해 toEntity(dto, false)를 호출하는 메서드도 같이 구현하는 것을 권장
     * @param dto DTO
     * @param idRequired id 필드가 필요한지 여부
     * @return 엔티티
     */
    R toEntity(D dto, boolean idRequired);
}