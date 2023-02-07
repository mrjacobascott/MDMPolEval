package com.lazydad.mdmpoleval;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lazydad.mdmpoleval.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
   // TextView policyList;
   // MDMPolEval.context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("MDM Policy Evaluator");

        TextView viewPolicy = (TextView) findViewById(R.id.viewPolicy);
        viewPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("mdmpoleval","launching mdm policy viewer");
                Intent i = new Intent(MainActivity.this, PolicyList.class);
                startActivity(i);
            }
        });

        /*
        TextView viewCerts = (TextView) findViewById(R.id.viewCerts);
        viewCerts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("mdmpoleval","launching cert viewer");
                Intent i = new Intent(MainActivity.this, certViewer.class);
                startActivity(i);
            }
        });
        */

    }


    private void createToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}