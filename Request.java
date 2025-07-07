import java.util.List;
import java.util.Map;

public class Requests {
    private String url;
    private Map<String, String> headers;
    private PageConfig page;
    private JsonResponse response;
    private List<Filter> filters;
    private List<Requests> linkedRequests;

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public Map<String, String> getHeaders() { return headers; }
    public void setHeaders(Map<String, String> headers) { this.headers = headers; }

    public PageConfig getPage() { return page; }
    public void setPage(PageConfig page) { this.page = page; }

    public JsonResponse getResponse() { return response; }
    public void setResponse(JsonResponse response) { this.response = response; }

    public List<Filter> getFilters() { return filters; }
    public void setFilters(List<Filter> filters) { this.filters = filters; }

    public List<Requests> getLinkedRequests() { return linkedRequests; }
    public void setLinkedRequests(List<Requests> linkedRequests) { this.linkedRequests = linkedRequests; }
}
