package vn.edu.usth.hehe;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class AddMoreAlbum extends AppCompatActivity {

    Button btnHome, btnLogout, btnAddToPublic, btnAddToPrivate, btnChooseImage;
    GridView gvImages;
    EditText etId, etName, etDescription;
    ArrayList<String> imagePaths = new ArrayList<>(); // List to hold image paths
    static final int PICK_IMAGE = 1;  // Request code for picking image

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_more_album); // Đảm bảo layout add_more_album.xml có sẵn

        // Ánh xạ các nút từ layout
        btnHome = findViewById(R.id.btn_home);
        btnLogout = findViewById(R.id.btn_logout);
        btnAddToPublic = findViewById(R.id.btn_add_to_public);
        btnAddToPrivate = findViewById(R.id.btn_add_to_private);
        btnChooseImage = findViewById(R.id.btn_choose_image);  // Add button to choose image
        gvImages = findViewById(R.id.gv_images);
        etId = findViewById(R.id.et_id);
        etName = findViewById(R.id.et_name);
        etDescription = findViewById(R.id.et_description);

        // Sự kiện khi nhấn nút "Trở về màn hình chính"
        btnHome.setOnClickListener(v -> {
            // Quay về MainActivity
            Intent intent = new Intent(AddMoreAlbum.this, MainActivity.class);
            startActivity(intent);
            finish(); // Đóng AddMoreAlbum Activity
        });

        // Sự kiện khi nhấn nút "Logout"
        btnLogout.setOnClickListener(v -> {
            // Quay về HomeActivity (trang chủ)
            Intent intent = new Intent(AddMoreAlbum.this, HomeActivity.class);
            startActivity(intent);
            finish(); // Đóng AddMoreAlbum Activity
        });

        // Xử lý khi nhấn nút "Chọn ảnh"
        btnChooseImage.setOnClickListener(v -> {
            // Kiểm tra quyền truy cập vào bộ nhớ
            if (ContextCompat.checkSelfPermission(AddMoreAlbum.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(AddMoreAlbum.this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            } else {
                openGallery();
            }
        });

        // Xử lý khi nhấn nút "Thêm vào Public"
        btnAddToPublic.setOnClickListener(v -> {
            // Thêm album vào danh mục Public
            saveAlbumToDatabase(true); // true = Public
        });

        // Xử lý khi nhấn nút "Thêm vào Private"
        btnAddToPrivate.setOnClickListener(v -> {
            // Thêm album vào danh mục Private
            saveAlbumToDatabase(false); // false = Private
        });
    }

    // Mở Gallery để chọn ảnh
    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE);
    }

    // Xử lý kết quả trả về từ Gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            // Lấy đường dẫn đến ảnh đã chọn
            String imagePath = getImagePath(data);
            imagePaths.add(imagePath);  // Thêm ảnh vào danh sách
            // Cập nhật GridView để hiển thị ảnh
            ImageAdapter imageAdapter = new ImageAdapter(this, imagePaths);
            gvImages.setAdapter(imageAdapter);
        }
    }

    // Lấy đường dẫn ảnh từ Intent data
    private String getImagePath(Intent data) {
        String[] projection = {MediaStore.Images.Media.DATA};
        try (Cursor cursor = getContentResolver().query(data.getData(), projection, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                return cursor.getString(columnIndex);
            }
        }
        return null;
    }

    // Lưu album vào cơ sở dữ liệu
    private void saveAlbumToDatabase(boolean isPublic) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        // Lấy thông tin từ các trường EditText
        String albumName = etName.getText().toString();
        String albumDescription = etDescription.getText().toString();

        // Kiểm tra thông tin nhập vào
        if (albumName.isEmpty() || albumDescription.isEmpty() || imagePaths.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin và chọn ảnh", Toast.LENGTH_SHORT).show();
            return;
        }

        // Thêm album vào bảng albums
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("album_name", albumName);
        values.put("album_description", albumDescription);
        values.put("is_public", isPublic ? 1 : 0);  // 1 = Public, 0 = Private
        long albumId = db.insert("albums", null, values);

        if (albumId != -1) {
            // Lưu ảnh vào bảng album_images
            for (String imagePath : imagePaths) {
                ContentValues imageValues = new ContentValues();
                imageValues.put("album_id", albumId);
                imageValues.put("image_path", imagePath);
                db.insert("album_images", null, imageValues);
            }
            Toast.makeText(this, "Album đã được thêm thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Lỗi khi thêm album", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }
}
