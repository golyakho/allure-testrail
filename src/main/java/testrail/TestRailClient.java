package testrail;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;

/**
 * @author holiakho.
 */
public class TestRailClient {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient httpClient;

    public TestRailClient(final String userToken) {
        val defaultHeaders = createDefaultHeaders(userToken);
        val sslConnectionSocketFactory = createSslConnectionSocketFactory();

        httpClient = HttpClientBuilder.create()
                                      .setDefaultHeaders(defaultHeaders)
                                      .setSSLSocketFactory(sslConnectionSocketFactory)
                                      .build();
    }

    public TestRailClient(final String user, final String password) {
        this(getAuthorization(user, password));
    }

    /**
     * Get test case from TestRail with associated test case Id.
     *
     * @param caseId Test case Id to receive.
     * @return Test case entity.
     */
    public TestCase getTestCase(String caseId) {
        URI uri;
        try {
            uri = TestRailApi.getCase(caseId);

            val response = httpClient.execute(new HttpGet(uri));
            val statusCode = response.getStatusLine().getStatusCode();

            switch (statusCode) {
                case 200:
                    return objectMapper.readValue(response.getEntity().getContent(), TestCase.class);
                case 400:
                    throw new HttpResponseException(statusCode, "Invalid or unknown project, suite or section");
                case 403:
                    throw new HttpResponseException(statusCode, "No access to the project");
                default:
                    throw new HttpResponseException(statusCode, "Unexpected status code");
            }

        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private List<Header> createDefaultHeaders(final String userToken) {
        return Arrays.asList(new BasicHeader("Content-Type", "application/json"),
                             new BasicHeader("Authorization", "Basic " + userToken));
    }

    private SSLConnectionSocketFactory createSslConnectionSocketFactory() {

        val allowAllHosts = new NoopHostnameVerifier();
        try {
            val sslContext = SSLContextBuilder.create()
                                              .loadTrustMaterial(new TrustSelfSignedStrategy())
                                              .build();

            return new SSLConnectionSocketFactory(sslContext, allowAllHosts);
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String getAuthorization(String user, String password) {
        return new String(Base64.getEncoder().encode((user + ":" + password).getBytes()));
    }
}
