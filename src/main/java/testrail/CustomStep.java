package testrail;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Custom test entity for test rail case.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomStep {

    @JsonProperty("content")
    private String content;
    @JsonProperty("expected")
    private String expected;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();
}


