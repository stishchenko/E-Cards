package com.tish.e_cards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.List;

public class GeneratorActivity extends AppCompatActivity {

    ListView generatedWordsListView;
    Button generateButton;
    Spinner numberSpinner;
    CardsConnector cardsConnector;
    List<Card> cardList;
    String[] numbers = new String[]{"3", "5", "7", "10"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generator);
        Toolbar toolbar = findViewById(R.id.toolbar_view);
        toolbar.setTitle("Генератор наборов слов");
        setSupportActionBar(toolbar);
        numberSpinner = findViewById(R.id.spinner_number);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(GeneratorActivity.this, android.R.layout.simple_spinner_item, numbers);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numberSpinner.setAdapter(adapter);
        numberSpinner.setSelection(1);
        generatedWordsListView = findViewById(R.id.list_view_generated_words);

        generateButton = findViewById(R.id.b_generate);

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardsConnector = new CardsConnector(GeneratorActivity.this);
                cardList = cardsConnector.getRandomCards(numberSpinner.getSelectedItem().toString());
                CardListAdapter generatedWordsListAdapter = new CardListAdapter(GeneratorActivity.this, cardList);
                generatedWordsListView.setAdapter(generatedWordsListAdapter);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_app_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent homeIntent = new Intent(GeneratorActivity.this, MainActivity.class);
        startActivity(homeIntent);
        return super.onOptionsItemSelected(item);
    }
}