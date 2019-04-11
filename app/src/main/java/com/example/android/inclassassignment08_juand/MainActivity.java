package com.example.android.inclassassignment08_juand;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;
    EditText valueText;
    EditText keyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Messages");
        valueText = findViewById(R.id.value);
        keyText = findViewById(R.id.key);
    }

    public void writeData(View view){
        String keyString = keyText.getText().toString();

        String valueString = valueText.getText().toString();
        myRef.child(keyString).setValue(valueString);

    }

    public void readData(View view) {
        DatabaseReference childRef = myRef.child(keyText.getText().toString());
        childRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String readData = dataSnapshot.getValue(String.class);
                    valueText.setText(readData);

                } else {
                    valueText.setText("");
                    Toast.makeText(MainActivity.this,  "Can't find the key", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error loading Firebase", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
