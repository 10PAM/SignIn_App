package dev.piceno.signin_app.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import dev.piceno.signin_app.R;
import dev.piceno.signin_app.databinding.ActivitySignInBinding;
import dev.piceno.signin_app.utilities.SafeLocal;

/**
 * This class contains all the methods for the non sign-in and sign-out in-app functionality.
 */
public class MainActivity extends AppCompatActivity {

    // To retrieve binding and the Preference Manager
    private ActivitySignInBinding binding;
    private FirebaseAuth mAuth;

    /**
     * Binds the View upon creation.
     *
     * @param savedInstanceState The reference to the Bundle object for state restoration.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ImageView image = findViewById(R.id.profileImage);
        RoundedBitmapDrawable d = RoundedBitmapDrawableFactory.create(getResources(), decodeImage(SafeLocal.getImage()));
        d.setCircular(true);
        image.setImageDrawable(d);

        TextView userName = findViewById(R.id.userName);
        userName.setText(SafeLocal.getUserName());
    }

    FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser == null) {
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(authStateListener);
    }

    /**
     * Decodes Base64 encoded Bitmap image.
     *
     * @param imageData The encoded image data.
     * @return Returns the decoded image data.
     */
    public Bitmap decodeImage(String imageData) {
        byte[] decodedBytes = Base64.decode(imageData, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}