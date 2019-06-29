package testrail;

import java.net.URI;
import java.net.URISyntaxException;

import properties.PropertiesProvider;
import properties.PropertyNames;

/**
 * Api for communication with TestRail server.
 */
public class TestRailApi {

    private static final String TEST_RAIL_SERVER = PropertiesProvider.getValue(PropertyNames.TEST_RAIL_SERVER);
    private static final String TEST_RAIL_API = "testrail/index.php?/api/v2/";

    /**
     * Get Uri for receiving test case from Test Rail.
     *
     * @param caseId Test case id to get.
     * @return Uri reference.
     * @throws URISyntaxException In case if Uri has invalid formatting.
     */
    public static URI getCase(String caseId) throws URISyntaxException {
        return new URI(TEST_RAIL_SERVER + TEST_RAIL_API + "get_case/" + caseId);
    }

    /**
     * Get Uri for updating test case at Test Rail.
     *
     * @param caseId Test case id to update.
     * @return Uri reference.
     * @throws URISyntaxException In case if Uri has invalid formatting.
     */
    public static URI updateCase(String caseId) throws URISyntaxException {
        return new URI(TEST_RAIL_SERVER + TEST_RAIL_API + "update_case/" + caseId);
    }
}
