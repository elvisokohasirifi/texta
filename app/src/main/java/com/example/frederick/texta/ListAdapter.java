package com.example.frederick.texta;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by peterplange on 27/07/2017.
 */

public class ListAdapter extends ArrayAdapter {

    private Activity context;
    private List<Entry> itemList;
    private TextView title;
    private TextView date;
    private TextView content;


    public ListAdapter(Activity context, List<Entry> itemList){
        super(context, R.layout.list_layout, itemList);
        this.context=context;
        this.itemList = itemList;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View views = inflater.inflate(R.layout.list_layout, null, true);
        title = (TextView)views.findViewById(R.id.title);
        date = (TextView)views.findViewById(R.id.date);
        content = (TextView)views.findViewById(R.id.content);

        final Entry entry = itemList.get(position);
        title.setText(entry.getTitle());
        date.setText(entry.getDate());
        content.setText(entry.getContent());

        return views;

    }

}
