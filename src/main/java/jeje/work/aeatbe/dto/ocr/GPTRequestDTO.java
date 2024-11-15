package jeje.work.aeatbe.dto.ocr;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

/**
 * 역할과 내용을 가진 메시지를 나타내는 레코드입니다.
 */
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record GPTRequestDTO(
    /**
     * 메시지 전송자의 역할입니다.
     * 예시: "user"
     */
    @JsonProperty("role")
    String role,

//    @JsonProperty("model")
//    String model,

    /**
     * 메시지의 내용으로, 텍스트와 이미지 URL이 포함됩니다.
     */
    @JsonProperty("content")
    List<Content> content
) {

}
