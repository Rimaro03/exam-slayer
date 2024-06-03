package org.project.savingsystem;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import lombok.Getter;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;

@Getter
public class BucketManager {

    private final Storage storage;
    private final Bucket bucket;

    @SneakyThrows
    public BucketManager(String bucketName, String projectId, String apiKeyPath) {
        Credentials credentials = GoogleCredentials.fromStream(
                Files.newInputStream(Paths.get(apiKeyPath))
        );

        storage = StorageOptions.newBuilder()
                .setCredentials(credentials)
                .setProjectId(projectId)
                .build()
                .getService();

        Bucket tmpBucket;
        try {
            tmpBucket = storage.get(bucketName);
        } catch (Exception e) {
            tmpBucket = storage.create(BucketInfo.of(bucketName));
        }
        bucket = tmpBucket;
    }

    public String getFileContent(String fileName) {
        Blob blob = bucket.get(fileName);
        if (blob == null) return "";
        return new String(blob.getContent());
    }

    public void uploadFile(String fileName, String content) {
        bucket.create(fileName, content.getBytes());
    }

    public void deleteFile(String fileName) {
        Blob blob = bucket.get(fileName);
        if (blob != null) blob.delete();
    }
}
