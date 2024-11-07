// AlbumPublicActivity.java
package vn.edu.usth.hehe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class AlbumPublicActivity extends AppCompatActivity {

    Button btnHome, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_public); // Đảm bảo có layout album_public.xml

        // Ánh xạ các nút từ layout
        btnHome = findViewById(R.id.btn_home);
        btnLogout = findViewById(R.id.btn_logout);

        // Sự kiện khi nhấn nút "Trở về màn hình chính"
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay về MainActivity
                Intent intent = new Intent(AlbumPublicActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Đóng AlbumPublicActivity
            }
        });

        // Sự kiện khi nhấn nút "Logout"
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay về HomeActivity (trang chủ)
                Intent intent = new Intent(AlbumPublicActivity.this, HomeActivity.class);
                startActivity(intent);
                finish(); // Đóng AlbumPublicActivity
            }
        });
    }
}

