package com.tish.e_cards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class CardsWatchingActivity extends AppCompatActivity {
    Animation animation;
    TextView tvWord;
    TextView tvTranslation;
    Button know;
    Button notKnow;
    CardsConnector cardsConnector;
    List<Card> cards;
    int wordIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards_watching);
        animation = AnimationUtils.loadAnimation(this, R.anim.tv_animation);
        tvWord = findViewById(R.id.tv_word);
        tvTranslation = findViewById(R.id.tv_translation);
        know = findViewById(R.id.b_yes);
        notKnow = findViewById(R.id.b_no);

        cardsConnector = new CardsConnector(CardsWatchingActivity.this);
        String folderName = getIntent().getExtras().getString("folderName");
        cards = cardsConnector.getAllCards(folderName);
        Toolbar toolbar = findViewById(R.id.toolbar_view);
        toolbar.setTitle(folderName);
        setSupportActionBar(toolbar);
        setWordOnCard(cards.get(0).getWord(), 0);
        wordIndex = 0;
        tvWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTranOnCard(cards.get(wordIndex).getTranslation());
            }
        });

        know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cards.get(wordIndex).setMark(true);
                wordIndex += 1;
                if (wordIndex < cards.size())
                    setWordOnCard(cards.get(wordIndex).getWord(), wordIndex);
                else {
                    int result = cardsConnector.updateLearnedCards(cards);
                    if(result > 0){
                        FoldersConnector foldersConnector = new FoldersConnector(CardsWatchingActivity.this);
                        foldersConnector.updateLearnedCardAmount(folderName, result);
                    }
                    Intent mainBackIntent = new Intent(CardsWatchingActivity.this, MainActivity.class);
                    startActivity(mainBackIntent);
                }
            }
        });
        notKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordIndex += 1;
                if (wordIndex < cards.size())
                    setWordOnCard(cards.get(wordIndex).getWord(), wordIndex);
                else {
                    int result = cardsConnector.updateLearnedCards(cards);
                    if(result > 0){
                        FoldersConnector foldersConnector = new FoldersConnector(CardsWatchingActivity.this);
                        foldersConnector.updateLearnedCardAmount(folderName, result);
                    }
                    Intent mainBackIntent = new Intent(CardsWatchingActivity.this, MainActivity.class);
                    startActivity(mainBackIntent);
                }
            }
        });
    }

    private void setWordOnCard(String word, int index) {
        tvWord.setText(word);
        if (index > 0)
            tvTranslation.setVisibility(View.INVISIBLE);
    }

    private void setTranOnCard(String tran) {
        tvTranslation.setText(tran);
        tvTranslation.startAnimation(animation);
        tvTranslation.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_app_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent homeIntent = new Intent(CardsWatchingActivity.this, MainActivity.class);
        startActivity(homeIntent);
        return super.onOptionsItemSelected(item);
    }
}