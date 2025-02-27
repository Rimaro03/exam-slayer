package org.project.savingsystem;

import com.google.api.gax.paging.Page;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import lombok.Getter;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * @param fileName The name of the file to get the content of
     * @return The content of the file
     */
    public String getFileContent(String fileName) {
        Blob blob = bucket.get(fileName);
        if (blob == null) return "";
        return new String(blob.getContent());
    }

    /**
     * @return A list of all the file names in the bucket
     */
    public List<String> getAllFileNames() {
        List<String> files = new ArrayList<>();
        Page<Blob> blobs = bucket.list();
        for (Blob blob : blobs.iterateAll()) {
            files.add(blob.getName());
        }
        return files;
    }

    /**
     * Uploads a file to the bucket.
     *
     * @param fileName The name of the file
     * @param content  The content of the file
     */
    public void uploadFile(String fileName, String content) {
        bucket.create(fileName, content.getBytes());
    }

    /**
     * Deletes a file from the bucket.
     *
     * @param fileName The name of the file to delete.
     */
    public void deleteFile(String fileName) {
        Blob blob = bucket.get(fileName);
        if (blob != null) blob.delete();
    }
}
