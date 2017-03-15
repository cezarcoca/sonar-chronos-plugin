package org.sonarsource.plugins.parser;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.Response;
import org.eclipse.jetty.client.util.InputStreamResponseListener;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.http.HttpStatus;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class ChronosParser {

    private static final Logger LOGGER = Loggers.get(ChronosParser.class);

    public ChronosAnalysisOutput parse(URL url) {

        LOGGER.info("Trying to connect to {} ...", url);
        HttpClient client = new HttpClient();
        try {
            client.start();
            InputStreamResponseListener listener = new InputStreamResponseListener();
            client.newRequest(url.toURI())
                    .method(HttpMethod.GET)
                    .accept("application/json")
                    .send(listener);
            Response response = listener.get(120, TimeUnit.SECONDS);
            if (response.getStatus() == HttpStatus.OK_200) {
                return parseResponse(listener);
            } else {
                LOGGER.error("Failed to fetch the data the server. Response status is {}", response.getStatus());
            }
        } catch (Exception e) {
            LOGGER.error("Failed to connect", e);
        } finally {
            try {
                client.stop();
            } catch (Exception e) {
                LOGGER.error("Failed to stop the client and release the resources", e);
            }
        }
        return new ChronosAnalysisOutput();
    }

    private ChronosAnalysisOutput parseResponse(InputStreamResponseListener listener) {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = listener.getInputStream()) {
            return mapper.readValue(is, ChronosAnalysisOutput.class);
        } catch (IOException e) {
            LOGGER.error("Failed to parse the object", e);
        }
        return new ChronosAnalysisOutput();
    }
}
