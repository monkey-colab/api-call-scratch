import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type",
  visible = true
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = LocalStore.class, name = "local"),
    @JsonSubTypes.Type(value = S3Store.class, name = "s3")
})
public abstract class Store {
    protected String type;

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    // Store the response somewhere
    public abstract void store(Response response, String storeName, Context context) throws Exception;
}
