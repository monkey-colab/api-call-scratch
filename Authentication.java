import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type",
  visible = true
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = KeyAuth.class, name = "key"),
    @JsonSubTypes.Type(value = OAuthAuth.class, name = "oauth")
})
public abstract class Authentication {
    protected String type;

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    // Method to apply auth to API call (stub for now)
    public abstract void applyAuth();
}
