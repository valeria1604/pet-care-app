package com.valeriia.beta_ver_1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class RegisterDogActivity extends AppCompatActivity {

    private EditText dogNameInput;
    private EditText dogAgeInput;
    private Spinner dogBreedSpinner;
    private RadioGroup dogGenderRadioGroup;
    private Button registerDogButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_pet_activity);

        // Ініціалізація елементів
        dogNameInput = findViewById(R.id.dog_name_input);
        dogAgeInput = findViewById(R.id.dog_age_input);
        dogBreedSpinner = findViewById(R.id.dog_breed_spinner);
        dogGenderRadioGroup = findViewById(R.id.dog_gender_radio_group);
        registerDogButton = findViewById(R.id.register_dog_button);

        // Заповнення спінера з породами собак
        List<String> dogBreeds = new ArrayList<>();
        dogBreeds.add("Лабрадор");
        dogBreeds.add("Німецька вівчарка");
        dogBreeds.add("Бульдог");
        dogBreeds.add("Пудель");
        dogBreeds.add("Боксер");
        dogBreeds.add("Хаскі");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dogBreeds);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dogBreedSpinner.setAdapter(adapter);

        // Обробка натискання кнопки реєстрації
        registerDogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerDog();
            }
        });
    }

    private void registerDog() {
        String name = dogNameInput.getText().toString().trim();
        String ageStr = dogAgeInput.getText().toString().trim();
        String breed = dogBreedSpinner.getSelectedItem().toString();
        int selectedGenderId = dogGenderRadioGroup.getCheckedRadioButtonId();

        // Перевірка введених даних
        if (name.isEmpty() || ageStr.isEmpty() || selectedGenderId == -1) {
            Toast.makeText(this, "Будь ласка, заповніть всі поля", Toast.LENGTH_SHORT).show();
            return;
        }

        int age = Integer.parseInt(ageStr);
        String gender = ((RadioButton) findViewById(selectedGenderId)).getText().toString();

        // Зберігання даних у SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("dog_name", name);
        editor.putInt("dog_age", age);
        editor.putString("dog_breed", breed);
        editor.putString("dog_gender", gender);
        editor.apply();

        Toast.makeText(this, "Собаку зареєстровано успішно!", Toast.LENGTH_SHORT).show();
        // Можливо, ви захочете закрити цю активність або перейти на іншу
        Intent intent = new Intent(RegisterDogActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Закрити активність
    }
}
