package com.valeriia.beta_ver_1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);  // Подключаем XML разметку

        EditText usernameInput = findViewById(R.id.username_input);
        EditText passwordInput = findViewById(R.id.password_input);
        Button loginButton = findViewById(R.id.login_btn);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();

                if (!username.isEmpty() && !password.isEmpty()) {
                    SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    // Сохраняем значение isRegistered
                    editor.putBoolean("isRegistered", true);

                    // Сохраняем имя пользователя
                    editor.putString("username", username); // Добавлено сохранение имени пользователя
                    editor.putString("password", password);

                    editor.apply(); // Применяем изменения

                    Toast.makeText(RegistrationActivity.this, "Регистрация прошла успешно!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegistrationActivity.this, RegisterDogActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegistrationActivity.this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
