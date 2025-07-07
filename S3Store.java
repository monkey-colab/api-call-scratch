public class S3Store extends Store {
    private String bucket;

    public String getBucket() { return bucket; }
    public void setBucket(String bucket) { this.bucket = bucket; }

    @Override
    public void store(Response response, String storeName, Context context) throws Exception {
        System.out.println("Storing response in S3 bucket " + bucket + " with key " + storeName);
        // Real implementation: upload to AWS S3
    }
}
