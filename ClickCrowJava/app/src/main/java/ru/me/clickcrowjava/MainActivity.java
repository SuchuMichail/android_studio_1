package ru.me.clickcrowjava;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private int mCount = 0;
    private Button mCrowsCounterButton;
    private TextView mInfoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        mCrowsCounterButton = findViewById(R.id.button_counter);
        mInfoTextView = findViewById(R.id.textView);

        mCrowsCounterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInfoTextView.setText("Я насчитал " + ++mCount + " ворон");
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void onClick(View view) {
        mInfoTextView.setText("Hello Kitty!");
    }
}