package sample.aad.aad;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonObjectMapperFactory {

    private JacksonObjectMapperFactory() {
    }

    public static ObjectMapper getInstance() {
        return SingletonHelper.INSTANCE;
    }

    private static class SingletonHelper {
        private static final ObjectMapper INSTANCE = new ObjectMapper();
    }
}
