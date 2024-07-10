package cl.govegan.msuserresources.config;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Configuration
public class FirebaseConfig {
    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        if (FirebaseApp.getApps().isEmpty()) {
            GoogleCredentials credentials;
            try {
                // Intenta cargar las credenciales desde el archivo local
                InputStream serviceAccount = new ClassPathResource("serviceAccountKey.json").getInputStream();
                credentials = GoogleCredentials.fromStream(serviceAccount);
            } catch (IOException e) {
                // Si falla, usa las credenciales por defecto (útil para Cloud Run)
                credentials = GoogleCredentials.getApplicationDefault();
            }

            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .setStorageBucket("go-vegan-422700.appspot.com")
                .build();
            
            return FirebaseApp.initializeApp(options);
        } else {
            return FirebaseApp.getInstance();
        }
    }
}