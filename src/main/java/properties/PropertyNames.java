package properties;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PropertyNames {
    TEST_RAIL_SERVER("testrail.server"),
    TEST_RAIL_TOKEN("testrail.token"),
    TEST_RAIL_USER("testrail.user"),
    TEST_RAIL_PASSWORD("testrail.password");

    private String property;
}
