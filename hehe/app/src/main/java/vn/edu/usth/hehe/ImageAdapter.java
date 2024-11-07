package vn.edu.usth.hehe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> imagePaths;

    public ImageAdapter(Context context, ArrayList<String> imagePaths) {
        this.context = context;
        this.imagePaths = imagePaths;
    }

    @Override
    public int getCount() {
        return imagePaths.size();
    }

    @Override
    public Object getItem(int position) {
        return imagePaths.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.image_item, parent, false);
            imageView = convertView.findViewById(R.id.image_view);
            convertView.setTag(imageView);
        } else {
            imageView = (ImageView) convertView.getTag();
        }

        // Sử dụng Glide để tải và hiển thị hình ảnh
        Glide.with(context)
                .load("file://" + imagePaths.get(position))  // Đảm bảo rằng đường dẫn là đường dẫn đúng
                .into(imageView);

        return convertView;
    }
}
