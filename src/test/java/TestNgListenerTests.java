import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.testng.Assert.assertEquals;

import java.lang.reflect.Method;

import io.qameta.allure.Allure;
import io.qameta.allure.TmsLink;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Parameter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import testng.AllureTestRail;

public class TestNgListenerTests {

    private final static String testCaseId = "123";
    private ClientAndServer mockServer;
    private MockServerClient serverClient;

    @BeforeClass
    public void startServer() {
        mockServer = startClientAndServer(1080);
        serverClient = new MockServerClient("localhost", 1080);
    }

    @AfterClass
    public void stopServer() {
        mockServer.stop();
    }

    @Test
    public void test() {
        AllureTestRail.registerHandler((testCase, testResult) ->
                                           testResult.setDescription(testCase.getCustomCaseDescription()));

        serverClient
            .when(request().withPath("/testrail/index.php")
                           .withQueryStringParameter(Parameter.param("/api/v2/get_case/123")))
            .respond(response("{\"custom_case_description\":\"description\"}"));

        AllureTestRail.executeHandlers(getDummyMethod());

        Allure.getLifecycle().updateTestCase(s -> assertEquals(s.getDescription(), "description"));
    }

    @TmsLink("123")
    public void dummyMethod() {
    }

    private Method getDummyMethod() {
        try {
            return TestNgListenerTests.class.getMethod("dummyMethod");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }
}
