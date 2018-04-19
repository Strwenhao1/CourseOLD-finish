package com.coursemis.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.coursemis.R;
import com.coursemis.model.ContentModel;

import java.util.List;

/**
 * Created by 74000 on 2018/4/14.
 */

public class ContentAdapter extends BaseAdapter {


    private Context context;
    private List<ContentModel> list;

    public ContentAdapter(Context context, List<ContentModel> list) {
        super();
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (list != null) {
            return list.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold hold;
        if (convertView == null) {
            hold = new ViewHold();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.simple_list, null);
            convertView.setTag(hold);
        } else {
            hold = (ViewHold) convertView.getTag();
        }

        hold.imageView = (ImageView) convertView
                .findViewById(R.id.item_imageview);
        hold.textView = (TextView) convertView.findViewById(R.id.item_textview);

        hold.imageView.setImageResource(list.get(position).getImageView());
        hold.textView.setText(list.get(position).getText());
        return convertView;
    }

    class ViewHold {
        public ImageView imageView;
        public TextView textView;
    }
}
