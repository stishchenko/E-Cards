package com.tish.e_cards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class TrainingActivity extends AppCompatActivity {

    ListView trainingListView;
    String[] trainNames = {"Поиск слова", "Поиск перевода", "Поиск слова по описанию",
            "Ввод слова по переводу", "Ввод слова по описанию", "Поиск соответствий"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        Toolbar toolbar = findViewById(R.id.toolbar_view);
        toolbar.setTitle("Тренировки");
        setSupportActionBar(toolbar);
        trainingListView = findViewById(R.id.list_view_trainings);
        TrainListAdapter trainAdapter = new TrainListAdapter(TrainingActivity.this, trainNames);
        trainingListView.setAdapter(trainAdapter);

        trainingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = trainNames[position];
                TrainSettingsDialog trainSettingsDialog = new TrainSettingsDialog(TrainingActivity.this, selectedItem);
                trainSettingsDialog.show(getSupportFragmentManager(), "tsd");
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
        Intent homeIntent = new Intent(TrainingActivity.this, MainActivity.class);
        startActivity(homeIntent);
        return super.onOptionsItemSelected(item);
    }
}