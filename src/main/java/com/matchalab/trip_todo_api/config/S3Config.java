// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// @Configuration
// public class S3Config {
// @Value("${aws.s3.accessKey}")
// private String accessKey;
// @Value
// ("${aws.s3.secretKey}")private String secretKey;

// @Bean
// public AmazonS3 amazonS3Client() {
// return AmazonS3ClientBuilder.standard()
// .withCredentials(
// AWSStaticCredentialsProvider(BasicAWSCredentials(accessKey, secretKey))
// )
// .withRegion(Regions.AP_NORTHEAST_2)
// .build()
// }
// }