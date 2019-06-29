package testrail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Test case model for TestRail test case representation.
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TestCase {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("section_id")
    private Integer sectionId;

    @JsonProperty("template_id")
    private Integer templateId;

    @JsonProperty("type_id")
    private Integer typeId;

    @JsonProperty("priority_id")
    private Integer priorityId;

    @JsonProperty("milestone_id")
    private Object milestoneId;

    @JsonProperty("refs")
    private String refs;

    @JsonProperty("created_by")
    private Integer createdBy;

    @JsonProperty("created_on")
    private Integer createdOn;

    @JsonProperty("updated_by")
    private Integer updatedBy;

    @JsonProperty("updated_on")
    private Integer updatedOn;

    @JsonProperty("estimate")
    private Object estimate;

    @JsonProperty("estimate_forecast")
    private Object estimateForecast;

    @JsonProperty("suite_id")
    private Integer suiteId;

    @JsonProperty("custom_tags")
    private String customTags;

    @JsonProperty("custom_automated")
    private Boolean customAutomated;

    @JsonProperty("custom_automated_test")
    private String customAutomatedTest;

    @JsonProperty("custom_temp_version_one_test_suites")
    private Object customTempVersionOneTestSuites;

    @JsonProperty("custom_version_one_created_by")
    private Object customVersionOneCreatedBy;

    @JsonProperty("custom_version_one_create_date")
    private Object customVersionOneCreateDate;

    @JsonProperty("custom_version_one_url")
    private Object customVersionOneUrl;

    @JsonProperty("custom_version_one_oid")
    private Object customVersionOneOid;

    @JsonProperty("custom_first_version")
    private Object customFirstVersion;

    @JsonProperty("custom_last_version")
    private Integer customLastVersion;

    @JsonProperty("custom_preconds")
    private String customPreconds;

    @JsonProperty("custom_case_description")
    private String customCaseDescription;

    @JsonProperty("custom_step")
    private List<CustomStep> customStep = null;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();
}
