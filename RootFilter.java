import com.fasterxml.jackson.annotation.JsonProperty;
import com.jayway.jsonpath.JsonPath;

import java.util.List;

public class RootFilter extends Filter {
    @JsonProperty("root")
    private String root;

    public String getRoot() { return root; }
    public void setRoot(String root) { this.root = root; }

    @Override
    public List<String> apply(String jsonResponse) {
        return JsonPath.read(jsonResponse, root);
    }
}
