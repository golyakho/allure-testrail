package testng;

import static java.util.Objects.isNull;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

import io.qameta.allure.Allure;
import io.qameta.allure.TmsLink;
import io.qameta.allure.TmsLinks;
import io.qameta.allure.model.TestResult;
import lombok.val;
import properties.PropertiesProvider;
import properties.PropertyNames;
import testrail.TestCase;
import testrail.TestRailClient;

/**
 * @author holiakho.
 */
public class AllureTestRail {

    private static final List<BiConsumer<TestCase, TestResult>> handlers = new ArrayList<>();
    private static TestRailClient testRailController;

    static {
        testRailController = initializeTestRailClient();
    }

    public static void registerHandler(BiConsumer<TestCase, TestResult> testCaseConsumer) {
        handlers.add(testCaseConsumer);
    }

    public static void executeHandlers(Method testMethod) {
        getAssociatedTestRailCase(testMethod)
            .ifPresent(s -> handlers
                .forEach(handler -> Allure.getLifecycle().updateTestCase(testResult -> handler.accept(s, testResult))));

    }

    private static Optional<TestCase> getAssociatedTestRailCase(final Method method) {
        if (method.isAnnotationPresent(TmsLink.class)) {
            return ofNullable(testRailController.getTestCase(method.getAnnotation(TmsLink.class).value()));
        } else if (method.isAnnotationPresent(TmsLinks.class)) {
            val links = method.getAnnotation(TmsLinks.class).value();
            if (links.length != 0) {
                return ofNullable(testRailController.getTestCase(links[0].value()));
            }
        }

        return empty();
    }

    private static TestRailClient initializeTestRailClient() {
        val userToken = PropertiesProvider.getValue(PropertyNames.TEST_RAIL_TOKEN);
        val user = PropertiesProvider.getValue(PropertyNames.TEST_RAIL_USER);
        val password = PropertiesProvider.getValue(PropertyNames.TEST_RAIL_PASSWORD);

        if (!isNull(userToken)) {
            return new TestRailClient(userToken);
        } else if (!isNull(user) && !isNull(password)) {
            return new TestRailClient(user, password);
        } else {
            throw new RuntimeException("Cannot initialize test rail client.");
        }
    }
}
