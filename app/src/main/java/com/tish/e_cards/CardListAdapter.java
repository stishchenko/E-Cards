package com.tish.e_cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class CardListAdapter extends ArrayAdapter<Card> {
    private Context context;
    private List<Card> cardList;

    public CardListAdapter(Context context, List<Card> cards) {
        super(context, R.layout.item_card, cards);
        this.context = context;
        this.cardList = cards;
    }

    @NonNull
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        CardViewHolder viewHolder;
        if (view == null) {
            viewHolder = new CardViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_card, parent, false);
            viewHolder.textViewWord = view.findViewById(R.id.tv_word);
            viewHolder.textViewTranslation = view.findViewById(R.id.tv_translation);
            viewHolder.textViewTag = view.findViewById(R.id.tv_tag);
            viewHolder.imageViewLearnedMark = view.findViewById(R.id.iv_learned_mark);
            view.setTag(viewHolder);
        } else {
            viewHolder = (CardViewHolder) view.getTag();
        }
        Card card = cardList.get(position);
        viewHolder.textViewWord.setText(card.getWord());
        viewHolder.textViewTranslation.setText(card.getTranslation());
        viewHolder.textViewTag.setText(card.getTag());
        if (!card.getMark())
            viewHolder.imageViewLearnedMark.setImageResource(R.drawable.not_learned_mark);
        else
            viewHolder.imageViewLearnedMark.setImageResource(R.drawable.learned_mark);
        return view;
    }

    private static class CardViewHolder {
        public static TextView textViewWord;
        public static TextView textViewTranslation;
        public static TextView textViewTag;
        public static ImageView imageViewLearnedMark;
    }
}
