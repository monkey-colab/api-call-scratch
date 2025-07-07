public class OAuthAuth extends Authentication {
    private String clientId;
    private String clientSecret;

    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }

    public String getClientSecret() { return clientSecret; }
    public void setClientSecret(String clientSecret) { this.clientSecret = clientSecret; }

    @Override
    public void applyAuth() {
        System.out.println("Applying OAuthAuth with clientId: " + clientId);
        // Real implementation: fetch OAuth token and add to headers
    }
}
