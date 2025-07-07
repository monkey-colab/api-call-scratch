public class LocalStore extends Store {
    private String path;

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    @Override
    public void store(Response response, String storeName, Context context) throws Exception {
        System.out.println("Storing response locally at " + path + "/" + storeName);
        // Real implementation: write to local file system
    }
}
