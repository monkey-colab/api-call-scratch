import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type",
  visible = true
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = ApiDataSource.class, name = "api")
    // add more types like rdbms here later
})
public abstract class DataSource {
    protected String type;
    protected String name;

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    // The "execute" method runs this data source and returns the response data
    public abstract Response execute(Context context) throws Exception;
}
