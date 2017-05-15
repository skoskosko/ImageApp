package skosko.imageapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by skosko on 21/04/2017.
 */

public class ImageAdapter extends BaseAdapter{

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<ImageItem> mDataSource;

    public ImageAdapter(Context context, ArrayList<ImageItem> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //System.out.println(mDataSource.toString());
    }

    //1
    @Override
    public int getCount() {

        return mDataSource.size();
    }

    //2
    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    //3
    @Override
    public long getItemId(int position) {
        return position;
    }

    //4
    @Override
        public View getView(int position, View convertView, ViewGroup parent) {
        // Get view for row item
        //System.out.println("Käy täälläkin?");
        View rowView = mInflater.inflate(R.layout.image_tile, parent, false);

        ImageItem item = (ImageItem) getItem(position);

        ImageView picture = (ImageView) rowView.findViewById(R.id.PictureHolder);
        Picasso.with(mContext).load(item.url).placeholder(R.mipmap.ic_launcher).into(picture);

        return rowView;
    }




}
