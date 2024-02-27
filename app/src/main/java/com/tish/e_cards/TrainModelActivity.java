package com.tish.e_cards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import java.util.List;

public class TrainModelActivity extends AppCompatActivity {
    FrameLayout container;
    TrainSearchingFragment trainSearchingFragment;
    TrainEnteringFragment trainEnteringFragment;
    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_model);


        container = findViewById(R.id.train_container);
        Bundle extras = getIntent().getExtras();
        String trainingType = extras.getString("trainingType");
        String folder = extras.getString("folder");
        String tag = extras.getString("tag");
        Toolbar toolbar = findViewById(R.id.toolbar_view);
        toolbar.setTitle(trainingType);
        setSupportActionBar(toolbar);
        Bundle bundle = new Bundle();
        bundle.putString("trainType", trainingType);
        bundle.putString("folder", folder);
        bundle.putString("tag", tag);

        if (trainingType.equalsIgnoreCase("Поиск слова") ||
                trainingType.equalsIgnoreCase("Поиск перевода") ||
                trainingType.equalsIgnoreCase("Поиск слова по описанию")) {
            trainSearchingFragment = new TrainSearchingFragment();
            trainSearchingFragment.setArguments(bundle);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.train_container, trainSearchingFragment);
            transaction.commit();
        } else if (trainingType.equalsIgnoreCase("Ввод слова по переводу") ||
                trainingType.equalsIgnoreCase("Ввод слова по описанию")) {
            trainEnteringFragment = new TrainEnteringFragment();
            trainEnteringFragment.setArguments(bundle);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.train_container, trainEnteringFragment);
            transaction.commit();
        } else if (trainingType.equalsIgnoreCase("Поиск соответствий")) {

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_app_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent homeIntent = new Intent(TrainModelActivity.this, MainActivity.class);
        startActivity(homeIntent);
        return super.onOptionsItemSelected(item);
    }
}