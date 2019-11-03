package com.rest.api.configurations

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import java.io.IOException
import java.io.InputStream
import javax.annotation.PostConstruct

@Configuration
class FirebaseConfig {

    @PostConstruct
    fun init() {
        try {
            FirebaseApp.getInstance()
        } catch (ie: IllegalStateException) {
            try {
                val options = buildFirebaseOptions()
                if (FirebaseApp.getApps().isEmpty()) {
                    FirebaseApp.initializeApp(options)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    @Throws(IOException::class)
    private fun buildFirebaseOptions(): FirebaseOptions {
        return FirebaseOptions.Builder()
                .setCredentials(getCredentials()).build()
    }

    @Throws(IOException::class)
    private fun getCredentials(): GoogleCredentials {
        return GoogleCredentials
                .fromStream(getGoogleCredentialsFile())
    }

    @Throws(IOException::class)
    private fun getGoogleCredentialsFile(): InputStream {
        return ClassPathResource("service-account.json").inputStream
    }

}