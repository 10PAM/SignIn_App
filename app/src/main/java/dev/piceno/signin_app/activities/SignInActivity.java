package dev.piceno.signin_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import dev.piceno.signin_app.R;
import dev.piceno.signin_app.databinding.ActivitySignInBinding;

public class SignInActivity extends AppCompatActivity {

    private ActivitySignInBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
    }

    private void setListeners() {
        binding.textCreateNewAccount.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), SignUpActivity.class)));
        binding.buttonSignIn.setOnClickListener(v -> addDataToFireStore());
    }

    /**
     * Data addition
     */
    private void addDataToFireStore() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String, String> data = new HashMap<>();

        data.put("first_name", "Mario");
        data.put("last_name", "Aguilera Piceno");

        database.collection("Users").add(data).addOnSuccessListener(documentReference -> {
            Toast.makeText(getApplicationContext(), "Data Inserted", Toast.LENGTH_SHORT).show();

        }).addOnFailureListener(exception -> {
            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }
}