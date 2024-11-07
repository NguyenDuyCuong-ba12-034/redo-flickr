package vn.edu.usth.hehe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Tên cơ sở dữ liệu
    private static final String DATABASE_NAME = "albumDatabase.db";
    private static final int DATABASE_VERSION = 1;

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Tạo bảng khi lần đầu sử dụng
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng users lưu trữ thông tin đăng nhập
        db.execSQL("CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT," +
                "password TEXT)");

        // Tạo bảng albums
        db.execSQL("CREATE TABLE albums (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "album_name TEXT," +
                "album_description TEXT," +
                "is_public INTEGER)");

        // Tạo bảng album_images
        db.execSQL("CREATE TABLE album_images (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "album_id INTEGER," +
                "image_path TEXT," +
                "FOREIGN KEY(album_id) REFERENCES albums(id))");

        // Thêm một người dùng mặc định (username: admin, password: admin)
        ContentValues values = new ContentValues();
        values.put("username", "admin");
        values.put("password", "admin");
        db.insert("users", null, values);
    }

    // Cập nhật cơ sở dữ liệu khi có phiên bản mới
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS albums");
        db.execSQL("DROP TABLE IF EXISTS album_images");
        onCreate(db);
    }

    // Kiểm tra thông tin đăng nhập
    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username=? AND password=?", new String[]{username, password});

        // Nếu có bản ghi tương ứng, trả về true, ngược lại trả về false
        boolean result = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return result;
    }

    // Thêm người dùng mới (optional)
    public boolean addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);

        long result = db.insert("users", null, values);
        db.close();

        return result != -1; // Trả về true nếu thêm thành công, ngược lại false
    }
}
