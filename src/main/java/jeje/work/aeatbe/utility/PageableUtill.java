package jeje.work.aeatbe.utility;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class PageableUtill {

    /**
     * 페이지네이션 정보에 특정 속성이 있는지 확인합니다.
     *
     * @param str     확인할 속성
     * @param pageAble 페이지네이션 정보
     * @return 속성이 존재하는지 여부
     */
    public boolean isHave(String str, Pageable pageAble) {
        return pageAble.getSort().stream()
                .anyMatch(order -> order.getProperty().equals(str));
    }

    /**
     * 페이지네이션 정보에서 정렬 속성을 제거합니다.
     *
     * @param pageable 페이지네이션 정보
     * @return 속성이 제거된 페이지네이션 정보
     */
    public Pageable removeSortOption(Pageable pageable) {
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
    }
}
