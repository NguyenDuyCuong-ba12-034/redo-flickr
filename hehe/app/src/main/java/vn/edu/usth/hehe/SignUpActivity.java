package vn.edu.usth.hehe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    Button btnSignup, btnBackToLogin;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        edtUsername = findViewById(R.id.edt_signup_username);
        edtPassword = findViewById(R.id.edt_signup_password);
        btnSignup = findViewById(R.id.btn_signup);
        btnBackToLogin = findViewById(R.id.btn_back_to_login);

        dbHelper = new DatabaseHelper(this);

        // Sự kiện khi bấm nút "Sign Up"
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin đăng ký
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();

                if (!username.isEmpty() && !password.isEmpty()) {
                    // Thêm người dùng vào cơ sở dữ liệu
                    boolean result = dbHelper.addUser(username, password);
                    if (result) {
                        // Thông báo thành công
                        Toast.makeText(SignUpActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                        // Quay lại màn hình login
                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Thông báo lỗi nếu không thành công
                        Toast.makeText(SignUpActivity.this, "Đăng ký không thành công!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Thông báo lỗi nếu thiếu thông tin
                    Toast.makeText(SignUpActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Quay lại màn hình login
        btnBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
