package com.tish.e_cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TrainListAdapter extends BaseAdapter {
    int[] trainIcons = {R.drawable.train_translation_word, R.drawable.train_word_translation,
            R.drawable.train_description_word, R.drawable.train_translation_enter_word,
            R.drawable.train_description_enter_word, R.drawable.train_accordig};
    String[] trainNames;
    Context context;

    public TrainListAdapter(Context context, String[] namesArray) {
        this.context = context;
        this.trainNames = namesArray;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TrainViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new TrainViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_training, parent, false);
            viewHolder.textViewTraining = convertView.findViewById(R.id.tv_train);
            viewHolder.imageViewTraining = convertView.findViewById(R.id.iv_train);
        } else
            viewHolder = (TrainViewHolder) convertView.getTag();
        viewHolder.textViewTraining.setText(trainNames[position]);
        viewHolder.imageViewTraining.setImageResource(trainIcons[position]);
        return convertView;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int position) {
        return trainNames[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private static class TrainViewHolder {
        public static TextView textViewTraining;
        public static ImageView imageViewTraining;
    }
}
