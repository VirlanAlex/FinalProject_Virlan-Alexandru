package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import modelObject.TestDataModel;

import java.io.InputStream;

public class TestDataLoader {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static volatile TestDataModel cachedData = null;

    private TestDataLoader() {
    }

    public static <T> T load(String resourceName, Class<T> clazz) {
        try (InputStream is = TestDataLoader.class.getClassLoader().getResourceAsStream(resourceName)) {
            if (is == null) {
                throw new IllegalArgumentException("Resource not found in src/test/resources: " + resourceName);
            }
            return MAPPER.readValue(is, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load test data from: " + resourceName, e);
        }
    }

    public static TestDataModel getTestData() {
        if (cachedData == null) {
            synchronized (TestDataLoader.class) {
                if (cachedData == null) {
                    cachedData = load("testdata.json", TestDataModel.class);
                }
            }
        }
        return cachedData;
    }
}
