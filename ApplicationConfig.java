import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ApplicationConfig {
    @JsonProperty("data-sources")
    private List<DataSource> dataSources;

    public List<DataSource> getDataSources() {
        return dataSources;
    }

    public void setDataSources(List<DataSource> dataSources) {
        this.dataSources = dataSources;
    }
}
