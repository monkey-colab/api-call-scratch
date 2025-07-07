import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = RootFilter.class, name = "root")
    // add more filter types here
})
public abstract class Filter {
    public abstract List<String> apply(String jsonResponse);
}
