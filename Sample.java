package com.example.apiframework;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.jayway.jsonpath.JsonPath;

import java.io.File;
import java.util.*;

// Root config
class ApplicationConfig {
    @JsonProperty("application")
    private AppInnerConfig application;

    public AppInnerConfig getApplication() { return application; }
    public void setApplication(AppInnerConfig application) { this.application = application; }

    public List<DataSource> getDataSources() {
        return application.getDataSources();
    }

    static class AppInnerConfig {
        @JsonProperty("data-sources")
        private List<DataSource> dataSources;

        public List<DataSource> getDataSources() { return dataSources; }
        public void setDataSources(List<DataSource> dataSources) { this.dataSources = dataSources; }
    }
}

// -----------------------------
// DataSource Hierarchy
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ApiDataSource.class, name = "api")
})
abstract class DataSource {
    protected String name;
    protected String url;
    protected Authentication authentication;
    protected Storage storage;
    protected Requests requests;
    protected String store;

    public abstract void execute(Context context);

    // getters and setters omitted for brevity
}

class ApiDataSource extends DataSource {
    @Override
    public void execute(Context context) {
        // Call primary URL, extract keys using filters, handle linked requests
        String response = HttpUtils.get(url, requests.getHeaders(), authentication);
        if (requests.getResponse() instanceof JsonResponse jsonResp) {
            jsonResp.setData(response);
            List<String> keys = jsonResp.extractKeys();

            if (requests.getLinkedRequests() != null) {
                for (Requests linked : requests.getLinkedRequests()) {
                    for (String key : keys) {
                        String resolvedUrl = linked.getUrl().replace("{question_id}", key);
                        String linkedResponse = HttpUtils.get(resolvedUrl, linked.getHeaders(), authentication);
                        // process/store linkedResponse as needed
                    }
                }
            }
        }
    }

    // setters/getters omitted for brevity
}

// -----------------------------
// Authentication Hierarchy
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = KeyAuth.class, name = "key")
})
abstract class Authentication {
    public abstract void applyToUrlOrHeaders(Map<String, String> headers, StringBuilder urlBuilder);
}

class KeyAuth extends Authentication {
    private String key;
    @JsonProperty("query-param")
    private String queryParam;

    public void applyToUrlOrHeaders(Map<String, String> headers, StringBuilder urlBuilder) {
        if (!urlBuilder.toString().contains("?")) urlBuilder.append("?");
        else urlBuilder.append("&");
        urlBuilder.append(queryParam).append("=").append(key);
    }

    // setters/getters omitted
}

// -----------------------------
// Storage Hierarchy
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = LocalStorage.class, name = "local")
})
abstract class Storage {
    protected String path;
    // Save logic etc.
}

class LocalStorage extends Storage {
    // setters/getters omitted
}

// -----------------------------
// Requests, Filters, Responses
class Requests {
    private String url;
    private Map<String, String> headers;
    private PageConfig page;
    private Response response;
    private List<Filter> filters;
    @JsonProperty("linkedRequests")
    private List<Requests> linkedRequests;

    // setters/getters omitted
}

class PageConfig {
    private int size;
    @JsonProperty("page-param") private String pageParam;
    @JsonProperty("size-param") private String sizeParam;
    // setters/getters
}

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = JsonResponse.class, name = "json")
})
abstract class Response {
    // marker base class
}

class JsonResponse extends Response {
    private String data;
    private List<Filter> filters;

    public List<String> extractKeys() {
        if (filters == null || data == null) return List.of();
        return filters.stream()
                .flatMap(f -> f.apply(data).stream())
                .toList();
    }

    // setters/getters omitted
}

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = RootFilter.class, name = "root")
})
abstract class Filter {
    public abstract List<String> apply(String json);
}

class RootFilter extends Filter {
    private String root;
    @Override
    public List<String> apply(String json) {
        return JsonPath.read(json, root);
    }
    // setters/getters
}

// -----------------------------
// Context & Http Utils
class Context {
    private Map<String, String> params;
    private Map<String, String> secrets;
    public Context(Map<String, String> params, Map<String, String> secrets) {
        this.params = params;
        this.secrets = secrets;
    }
    // Getters
}

class HttpUtils {
    public static String get(String url, Map<String, String> headers, Authentication auth) {
        StringBuilder urlBuilder = new StringBuilder(url);
        Map<String, String> hdrs = new HashMap<>(headers);
        if (auth != null) auth.applyToUrlOrHeaders(hdrs, urlBuilder);
        // Simulated GET call
        return "{ \"items\": [{\"question_id\": 12345}] }";
    }
}

// -----------------------------
// Boot Main
public class ApiFrameworkRunner {
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        ApplicationConfig config = mapper.readValue(new File("config.yaml"), ApplicationConfig.class);
        Context context = new Context(Map.of(), Map.of("key", "abc123"));

        for (DataSource ds : config.getDataSources()) {
            ds.execute(context);
        }
    }
}
