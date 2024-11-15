package jeje.work.aeatbe.dto.ocr;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ImageRequestDTO(
    @JsonProperty("model")
    String model,

    @JsonProperty("messages")
    List<GPTRequestDTO> message,

    @JsonProperty("max_tokens")
    int maxTokens
) {

}
