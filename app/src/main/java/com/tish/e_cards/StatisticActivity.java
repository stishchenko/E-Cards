package com.tish.e_cards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

public class StatisticActivity extends AppCompatActivity {

    Button getButton;
    Spinner typeSpinner;
    StatisticConnector statisticConnector;
    List<StatisticItem> statisticItemList;
    TableLayout table;
    String[] trainNames = {"Нет", "Поиск слова", "Поиск перевода", "Поиск слова по описанию",
            "Ввод слова по переводу", "Ввод слова по описанию", "Поиск соответствий"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        Toolbar toolbar = findViewById(R.id.toolbar_view);
        toolbar.setTitle("Статистика");
        setSupportActionBar(toolbar);

        table = findViewById(R.id.table_statistic);
        TableRow row = new TableRow(StatisticActivity.this);
        TextView typeTextView = new TextView(StatisticActivity.this);
        typeTextView.setText("Тренировка");
        typeTextView.setTextSize(20);
        row.addView(typeTextView);
        TextView fTextView = new TextView(StatisticActivity.this);
        fTextView.setText("Папка");
        fTextView.setTextSize(20);
        row.addView(fTextView);
        TextView tTextView = new TextView(StatisticActivity.this);
        tTextView.setText("Тег");
        tTextView.setTextSize(20);
        row.addView(tTextView);
        TextView resultTextView = new TextView(StatisticActivity.this);
        resultTextView.setText("Result");
        resultTextView.setTextSize(20);
        row.addView(resultTextView);
        getButton = findViewById(R.id.b_get);
        typeSpinner = findViewById(R.id.spinner_type);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(StatisticActivity.this, android.R.layout.simple_spinner_item, trainNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
        typeSpinner.setSelection(0);


        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                table.addView(row);
                statisticConnector = new StatisticConnector(StatisticActivity.this);
                statisticItemList = statisticConnector.getStatistic(typeSpinner.getSelectedItem().toString());
                for (StatisticItem item : statisticItemList) {
                    TableRow tableRow = new TableRow(StatisticActivity.this);
                    TextView typeTextView = new TextView(StatisticActivity.this);
                    TextView fTextView = new TextView(StatisticActivity.this);
                    TextView tTextView = new TextView(StatisticActivity.this);
                    TextView resultTextView = new TextView(StatisticActivity.this);
                    typeTextView.setText(item.getType());
                    typeTextView.setPadding(0, 0, 60, 0);
                    typeTextView.setTextSize(20);
                    tableRow.addView(typeTextView);
                    fTextView.setText(item.getFolder());
                    fTextView.setPadding(0, 0, 60, 0);
                    fTextView.setTextSize(20);
                    tableRow.addView(fTextView);
                    tTextView.setText(item.getTag());
                    tTextView.setPadding(0, 0, 50, 0);
                    tTextView.setTextSize(20);
                    tableRow.addView(tTextView);
                    resultTextView.setText(item.getResult());
                    resultTextView.setTextSize(20);
                    tableRow.addView(resultTextView);
                    table.addView(tableRow);
                }
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
        Intent homeIntent = new Intent(StatisticActivity.this, MainActivity.class);
        startActivity(homeIntent);
        return super.onOptionsItemSelected(item);
    }
}