package vn.edu.usth.hehe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnAddToAlbum, btnAlbumPublic, btnAlbumPrivate, btnAlbumEveryone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ các nút từ layout
        btnAddToAlbum = findViewById(R.id.btn_add_to_album);
        btnAlbumPublic = findViewById(R.id.btn_album_public);
        btnAlbumPrivate = findViewById(R.id.btn_album_private);
        btnAlbumEveryone = findViewById(R.id.btn_album_everyone);

        // Xử lý sự kiện bấm nút "Thêm vào Album"
        btnAlbumEveryone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến trang SearchActivity
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        btnAddToAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {// Kiểm tra xem sự kiện có được gọi hay không
                Intent intent = new Intent(MainActivity.this, AddMoreAlbum.class);
                startActivity(intent);
            }
        });

        // Xử lý sự kiện bấm nút "Album Public"
        btnAlbumPublic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến trang AlbumPublicActivity
                Intent intent = new Intent(MainActivity.this, AlbumPublicActivity.class);
                startActivity(intent);
            }
        });

        // Xử lý sự kiện bấm nút "Album Private"
        btnAlbumPrivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến trang AlbumPrivateActivity
                Intent intent = new Intent(MainActivity.this, AlbumPrivateActivity.class);
                startActivity(intent);
            }
        });

    }
}
