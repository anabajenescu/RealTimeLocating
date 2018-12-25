package com.tutorial.athina.pethood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterDogActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    public static final String TAG = "Register Dog Activity";
    private String switchResponse;
    private String checkboxResponse;
    private EditText dogName, dogOwner, dogBreed, dogColor, dogAge;
    private Button registerDogButton;
    private CheckBox dogSmall, dogMedium, dogLarge;
    private Switch mateSwitch;

    DatabaseReference databaseDog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerdog_activity);

        dogName = (EditText) findViewById(R.id.dogNameText);
        dogOwner = (EditText) findViewById(R.id.dogOwnerEmailText);
        dogBreed = (EditText) findViewById(R.id.dogBreedText);
        dogColor = (EditText) findViewById(R.id.dogColorText);
        dogAge = (EditText) findViewById(R.id.dogAgeText);

        dogSmall = (CheckBox) findViewById(R.id.dogSizeSmall);
        dogMedium = (CheckBox) findViewById(R.id.dogSizeMedium);
        dogLarge = (CheckBox) findViewById(R.id.dogSizeBig);

        mateSwitch = (Switch) findViewById(R.id.matingSwitch);
        mateSwitch.setOnCheckedChangeListener(this);

        databaseDog = FirebaseDatabase.getInstance().getReference("dog");

        registerDogButton = (Button) findViewById(R.id.registerButton);
        registerDogButton.setOnClickListener(this);


    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();


        switch (view.getId()) {
            case R.id.dogSizeSmall:
                if (checked) {
                    checkboxResponse = "Small";                }
                break;
            case R.id.dogSizeMedium:
                if (checked) {
                    checkboxResponse = "Medium";
                }

                break;
            case R.id.dogSizeBig:
                if (checked) {
                    checkboxResponse = "Big";
                }

                break;
            default:
                break;
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            switchResponse = "Y";
        } else {
            switchResponse = "N";
        }
    }

    public void addDog(){

        String name = dogName.getText().toString().trim();
        String emailOwner = dogOwner.getText().toString().trim();
        String breed = dogBreed.getText().toString().trim();
        String color = dogColor.getText().toString().trim();
        String age = dogAge.getText().toString().trim();

        if (!TextUtils.isEmpty(name)) {

            String id = databaseDog.push().getKey();
            Dog dog = new Dog(name,emailOwner,breed,age,color,checkboxResponse,switchResponse);
            databaseDog.child(id).setValue(dog);
            Toast.makeText(this, "Dog Added!", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this, "Enter dog details!", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onClick(View v) {

        addDog();
        startActivity(new Intent(this,LoginActivity.class));
    }
}