package jeje.work.aeatbe.dto.ocr;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;


/**
 * 메시지의 내용을 나타내는 레코드입니다.
 * 텍스트나 이미지 URL이 될 수 있습니다.
 */
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Content(
    /**
     * 콘텐츠의 타입입니다.
     * 예시: "text" 또는 "image_url"
     */
    @JsonProperty("type")
    String type,

    /**
     * 타입이 "text"일 때 해당하는 텍스트 내용입니다.
     */
    @JsonProperty("text")
    String text,

    /**
     * 타입이 "image_url"일 때 해당하는 이미지 URL입니다.
     */
    @JsonProperty("image_url")
    ImageUrl imageUrl

//    @JsonProperty("detail")
//    String detail
) {

}
