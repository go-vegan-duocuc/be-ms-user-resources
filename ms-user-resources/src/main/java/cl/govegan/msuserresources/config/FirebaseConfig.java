package cl.govegan.msuserresources.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp firebaseApp(ResourceLoader resourceLoader) throws IOException {
        if (FirebaseApp.getApps().isEmpty()) {
            // Cargar las credenciales desde el secreto de Google Secret Manager
            GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();

            // Configurar opciones de Firebase
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .setStorageBucket("go-vegan-422700.appspot.com")
                    .build();

            // Inicializar FirebaseApp
            return FirebaseApp.initializeApp(options);
        } else {
            return FirebaseApp.getInstance();
        }
}

}
