package vn.edu.usth.hehe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    Button btnLogin, btnBackHome;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login); // Đảm bảo layout login.xml có sẵn

        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
        btnBackHome = findViewById(R.id.btn_back_to_home);

        dbHelper = new DatabaseHelper(this);

        // Sự kiện khi bấm nút "Login"
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kiểm tra thông tin đăng nhập từ cơ sở dữ liệu
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();

                if (dbHelper.checkLogin(username, password)) {
                    // Nếu đăng nhập thành công, chuyển đến MainActivity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Thông báo lỗi nếu không đúng
                    Toast.makeText(LoginActivity.this, "Sai thông tin đăng nhập!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Quay về màn hình chính (Home)
        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
