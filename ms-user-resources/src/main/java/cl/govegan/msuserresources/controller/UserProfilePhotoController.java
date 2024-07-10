package cl.govegan.msuserresources.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cl.govegan.msuserresources.services.userprofilephoto.UserProfilePhotoService;
import cl.govegan.msuserresources.web.response.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/user-profile-photo")
@RequiredArgsConstructor
public class UserProfilePhotoController {

    private final UserProfilePhotoService userProfilePhotoService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<String>> uploadProfilePhoto(Authentication authentication, @RequestParam("file") MultipartFile file) {
        try {
            String photoUrl = userProfilePhotoService.uploadProfilePhoto(authentication, file);
            return ResponseEntity.ok(
                ApiResponse.<String>builder()
                    .status(HttpStatus.OK.value())
                    .message("Profile photo uploaded successfully")
                    .data(photoUrl)
                    .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                ApiResponse.<String>builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .data(null)
                    .build()
            );
        }
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<String>> getProfilePhotoUrl(Authentication authentication) {
        try {
            String photoUrl = userProfilePhotoService.getProfilePhotoUrl(authentication);
            return ResponseEntity.ok(
                ApiResponse.<String>builder()
                    .status(HttpStatus.OK.value())
                    .message("Profile photo URL retrieved")
                    .data(photoUrl)
                    .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                ApiResponse.<String>builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .data(null)
                    .build()
            );
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<String>> updateProfilePhoto(Authentication authentication, @RequestParam("file") MultipartFile file) {
        try {
            String photoUrl = userProfilePhotoService.updateProfilePhoto(authentication, file);
            return ResponseEntity.ok(
                ApiResponse.<String>builder()
                    .status(HttpStatus.OK.value())
                    .message("Profile photo updated successfully")
                    .data(photoUrl)
                    .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                ApiResponse.<String>builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .data(null)
                    .build()
            );
        }
    }

    @DeleteMapping()
    public ResponseEntity<ApiResponse<String>> deleteProfilePhoto(Authentication authentication) {
        try {
            userProfilePhotoService.deleteProfilePhoto(authentication);
            return ResponseEntity.ok(
                ApiResponse.<String>builder()
                    .status(HttpStatus.OK.value())
                    .message("Profile photo deleted successfully")
                    .data(null)
                    .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                ApiResponse.<String>builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .data(null)
                    .build()
            );
        }
    }
}