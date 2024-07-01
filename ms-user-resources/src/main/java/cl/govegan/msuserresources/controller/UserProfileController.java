package cl.govegan.msuserresources.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.govegan.msuserresources.model.UserProfile;
import cl.govegan.msuserresources.services.userprofileservice.UserProfileService;
import cl.govegan.msuserresources.web.response.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/user-resource")
@RequiredArgsConstructor
public class UserProfileController {

        private final UserProfileService userProfileService;

        @GetMapping()
        public ResponseEntity<ApiResponse<UserProfile>> getUserProfile(Authentication authentication) {

                try {
                        UserProfile userProfile = userProfileService.getUserProfile(authentication);
                        return ResponseEntity.ok(
                                ApiResponse.<UserProfile>builder()
                                .status(HttpStatus.OK.value())
                                .message("User profile retrieved")
                                .data(userProfile)
                                .build()
                        );
                } catch (Exception e) {
                        return ResponseEntity.badRequest().body(
                                ApiResponse.<UserProfile>builder()
                                .status(HttpStatus.BAD_REQUEST.value())
                                .message(e.getMessage())
                                .data(null)
                                .build()
                        );
                }

        }

}
