import java.util.List;

public class JsonResponse implements Response {
    private String data;
    private List<Filter> filters;

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public List<Filter> getFilters() { return filters; }
    public void setFilters(List<Filter> filters) { this.filters = filters; }

    public List<String> extractKeys() {
        if (filters == null || data == null) return List.of();
        return filters.stream()
            .flatMap(f -> f.apply(data).stream())
            .toList();
    }
}
