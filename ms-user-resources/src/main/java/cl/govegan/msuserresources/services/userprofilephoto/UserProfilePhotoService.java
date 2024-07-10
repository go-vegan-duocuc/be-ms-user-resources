package cl.govegan.msuserresources.services.userprofilephoto;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

public interface UserProfilePhotoService {
    String uploadProfilePhoto(Authentication authentication, MultipartFile file);
    String getProfilePhotoUrl(Authentication authentication);
    String updateProfilePhoto(Authentication authentication, MultipartFile file);
    void deleteProfilePhoto(Authentication authentication);
}