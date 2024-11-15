package jeje.work.aeatbe.dto.ocr;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

/**
 * 이미지 URL을 나타내는 레코드입니다.
 */
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ImageUrl(

    /**
     * 이미지의 URL입니다.
     */
    @JsonProperty("url")
    String url,

    @JsonProperty("detail")
    String detail
) {

}
