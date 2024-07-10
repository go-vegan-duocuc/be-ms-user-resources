package cl.govegan.msuserresources.services.userprofilephoto;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cl.govegan.msuserresources.model.UserProfile;
import cl.govegan.msuserresources.repositories.UserProfileRepository;
import cl.govegan.msuserresources.services.firebase.FirebaseStorageService;
import cl.govegan.msuserresources.services.jwt.JwtService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserProfilePhotoServiceImpl implements UserProfilePhotoService {

    private final UserProfileRepository userProfileRepository;
    private final JwtService jwtService;
    private final FirebaseStorageService firebaseStorageService;
    private static final String USER_ID = "userId";

    @Override
    public String uploadProfilePhoto(Authentication authentication, MultipartFile file) {
        Map<String, String> userData = jwtService.getClaimsFromAuthentication(authentication);
        String userId = userData.get(USER_ID);
        UserProfile userProfile = getUserProfile(userId);

        String photoUrl = uploadPhotoToFirebase(userId, file);
        userProfile.setProfilePicture(photoUrl);
        userProfileRepository.save(userProfile);

        return photoUrl;
    }

    @Override
    public String getProfilePhotoUrl(Authentication authentication) {
        Map<String, String> userData = jwtService.getClaimsFromAuthentication(authentication);
        UserProfile userProfile = getUserProfile(userData.get(USER_ID));
        return userProfile.getProfilePicture();
    }

    @Override
    public String updateProfilePhoto(Authentication authentication, MultipartFile file) {
        Map<String, String> userData = jwtService.getClaimsFromAuthentication(authentication);
        String userId = userData.get(USER_ID);
        UserProfile userProfile = getUserProfile(userId);

        // Delete old photo if exists
        if (userProfile.getProfilePicture() != null) {
            firebaseStorageService.deleteFile(userProfile.getProfilePicture());
        }

        String photoUrl = uploadPhotoToFirebase(userId, file);
        userProfile.setProfilePicture(photoUrl);
        userProfileRepository.save(userProfile);

        return photoUrl;
    }

    @Override
    public void deleteProfilePhoto(Authentication authentication) {
        Map<String, String> userData = jwtService.getClaimsFromAuthentication(authentication);
        UserProfile userProfile = getUserProfile(userData.get(USER_ID));

        if (userProfile.getProfilePicture() != null) {
            firebaseStorageService.deleteFile(userProfile.getProfilePicture());
            userProfile.setProfilePicture(null);
            userProfileRepository.save(userProfile);
        }
    }

    private UserProfile getUserProfile(String userId) {
        return userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User profile not found"));
    }

    private String uploadPhotoToFirebase(String userId, MultipartFile file) {
        try {
            String fileName = "profile-photos/" + userId + "/" + UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
            return firebaseStorageService.uploadFile(fileName, file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload profile photo", e);
        }
    }
}