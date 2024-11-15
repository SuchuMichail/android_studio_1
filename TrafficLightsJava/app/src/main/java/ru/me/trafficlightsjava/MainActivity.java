package ru.me.trafficlightsjava;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private ConstraintLayout mConstraintLayout;
    private TextView mInfoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        mConstraintLayout = findViewById(R.id.constraintLayout);
        mInfoTextView = findViewById(R.id.textViewInfo);

        Button yellowButton = (Button) findViewById(R.id.button_yellow);
        yellowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInfoTextView.setText(R.string.yellow);
                mConstraintLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.yellowColor));
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.constraintLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void onRedButtonClick(View view) {
        mInfoTextView.setText(R.string.red);
        mConstraintLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.redColor));
    }

    public void onGreenButtonClick(View view) {
        mInfoTextView.setText(R.string.green);
        mConstraintLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.greenColor));
    }
}