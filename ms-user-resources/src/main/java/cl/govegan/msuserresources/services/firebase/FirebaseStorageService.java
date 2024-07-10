package cl.govegan.msuserresources.services.firebase;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Service;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.StorageClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FirebaseStorageService {

    private final FirebaseApp firebaseApp;

    private Storage getStorage() {
        return StorageClient.getInstance(firebaseApp).bucket().getStorage();
    }

    private String getBucketName() {
        return StorageClient.getInstance(firebaseApp).bucket().getName();
    }

    public String getPlaceholderFileUrl() {
        Storage storage = getStorage();
        String bucketName = getBucketName();

        Page<Blob> blobs = storage.list(bucketName, Storage.BlobListOption.prefix("placeholder/"));

        for (Blob blob : blobs.iterateAll()) {
            if (!blob.getName().equals("placeholder/")) {
                makeFilePublic(blob);
                return constructPublicUrl(bucketName, blob.getName());
            }
        }

        throw new RuntimeException("No files found in the placeholder folder.");
    }

    private void makeFilePublic(Blob blob) {
        blob.createAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
    }

    private String constructPublicUrl(String bucketName, String fileName) {
        try {
            String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
            return String.format("https://storage.googleapis.com/%s/%s", bucketName, encodedFileName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to construct public URL", e);
        }
    }

    public String uploadFile(String fileName, byte[] fileData) {
        try {
            Storage storage = getStorage();
            String bucketName = getBucketName();

            if (bucketName == null || bucketName.isEmpty()) {
                throw new IllegalStateException("Firebase Storage bucket name is not configured properly.");
            }

            BlobId blobId = BlobId.of(bucketName, fileName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/jpeg").build();
            Blob blob = storage.create(blobInfo, fileData);
            makeFilePublic(blob);
            return constructPublicUrl(bucketName, fileName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file: " + e.getMessage(), e);
        }
    }

    public void deleteFile(String fileUrl) {
        Storage storage = StorageClient.getInstance().bucket().getStorage();
        String bucketName = StorageClient.getInstance().bucket().getName();
        String fileName = fileUrl.substring(fileUrl.lastIndexOf('/') + 1);
        BlobId blobId = BlobId.of(bucketName, fileName);
        storage.delete(blobId);
    }

    public void deleteUserFolder(String userId) {
        Storage storage = StorageClient.getInstance().bucket().getStorage();
        String bucketName = StorageClient.getInstance().bucket().getName();
        String folderPath = "profile-photos/" + userId + "/";
    
        Page<Blob> blobs = storage.list(bucketName, Storage.BlobListOption.prefix(folderPath));
        for (Blob blob : blobs.iterateAll()) {
            storage.delete(blob.getBlobId());
        }
    }

}