package jp.ac.meijou.android.test1;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import jp.ac.meijou.android.test1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private PrefDataStore prefDataStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        prefDataStore = PrefDataStore.getInstance(this);
        binding.savebutton.setOnClickListener((view) -> {
            var text = binding.editTextText.getText().toString();
            prefDataStore.setString("name" , text);
        });


        binding.text.setText(R.string.text1);
        binding.button.setOnClickListener(View -> {
            var text = binding.editTextText.getText().toString();
            binding.text.setText(text);
        });
        prefDataStore.getString("name")
                .ifPresent(name -> binding.text.setText(name));
    }
}