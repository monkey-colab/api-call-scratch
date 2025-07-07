import com.fasterxml.jackson.annotation.JsonProperty;

public class KeyAuth extends Authentication {
    private String key;

    @JsonProperty("query-param")
    private String queryParam;

    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }

    public String getQueryParam() { return queryParam; }
    public void setQueryParam(String queryParam) { this.queryParam = queryParam; }

    @Override
    public void applyAuth() {
        System.out.println("Applying KeyAuth with key: " + key + " and query param: " + queryParam);
        // Real implementation would add key to URL query params or headers
    }
}
