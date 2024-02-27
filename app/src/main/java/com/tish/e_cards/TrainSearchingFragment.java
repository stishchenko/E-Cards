package com.tish.e_cards;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.graphics.Color.GREEN;

public class TrainSearchingFragment extends Fragment {

    TextView searchTextView;
    ListView answersListView;
    CardsConnector cardsConnector;
    StatisticConnector statisticConnector;
    List<Card> cards;
    int index = 0;
    String[] answerParts = new String[2];
    int learnedCounter = 0;
    String trainingType;
    String folder;
    String tag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.train_searching_fragment, null);
        searchTextView = view.findViewById(R.id.tv_search);
        answersListView = view.findViewById(R.id.list_view_search);

        cardsConnector = new CardsConnector(getContext());
        Bundle bundle = getArguments();
        trainingType = bundle.getString("trainType");
        folder = bundle.getString("folder");
        tag = bundle.getString("tag");

        if (!folder.equalsIgnoreCase("") && !tag.equalsIgnoreCase("")) {
            cards = cardsConnector.getCardsFromFolderAndTag(folder, tag);
        } else if (!folder.equalsIgnoreCase("") && tag.equalsIgnoreCase("")) {
            cards = cardsConnector.getAllCards(folder);
        } else if (folder.equalsIgnoreCase("") && !tag.equalsIgnoreCase("")) {
            cards = cardsConnector.getCardsWithTag(tag);
        } else if (folder.equalsIgnoreCase("") && tag.equalsIgnoreCase("")) {
            cards = cardsConnector.getAllCards("Все слова");
        }

        Collections.shuffle(cards);

        if (trainingType.equals("Поиск слова")) {
            answerParts = getSearchItem(1, index);
        } else if (trainingType.equals("Поиск перевода")) {
            answerParts = getSearchItem(2, index);
        } else if (trainingType.equals("Поиск слова по описанию")) {
            answerParts = getSearchItem(3, index);
        }

        answersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                index += 1;
                String selectedItem = (String) parent.getItemAtPosition(position);
                if (trainingType.equals("Поиск слова")) {
                    if (answerParts[0].equalsIgnoreCase(selectedItem)) {
                        //answersListView.getChildAt(position).setBackgroundColor(GREEN);
                        learnedCounter += 1;
                    } else {
                        answersListView.getChildAt(position).setBackgroundColor(Color.RED);
                    }
                    /*try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    //answersListView.getChildAt(position).setBackgroundColor(Color.WHITE);
                    answerParts = null;
                    if (index < cards.size())
                        answerParts = getSearchItem(1, index);
                    else {
                        dialogCreator(learnedCounter, cards.size());
                    }
                } else if (trainingType.equals("Поиск перевода")) {
                    if (answerParts[1].equalsIgnoreCase(selectedItem)) {
                        answersListView.getChildAt(position).setBackgroundColor(GREEN);
                        learnedCounter += 1;
                    } else {
                        answersListView.getChildAt(position).setBackgroundColor(Color.RED);
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    answersListView.getChildAt(position).setBackgroundColor(Color.WHITE);
                    answerParts = null;
                    if (index < cards.size())
                        answerParts = getSearchItem(2, index);
                    else {
                        dialogCreator(learnedCounter, cards.size());
                    }
                } else if (trainingType.equals("Поиск слова по описанию")) {
                    if (answerParts[0].equalsIgnoreCase(selectedItem)) {
                        answersListView.getChildAt(position).setBackgroundColor(GREEN);
                        learnedCounter += 1;
                    } else {
                        answersListView.getChildAt(position).setBackgroundColor(Color.RED);
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    answersListView.getChildAt(position).setBackgroundColor(Color.WHITE);
                    answerParts = null;
                    if (index < cards.size())
                        answerParts = getSearchItem(3, index);
                    else {
                        dialogCreator(learnedCounter, cards.size());
                    }
                }
            }
        });
        return view;
    }

    private String[] getSearchItem(int type, int index) {
        String word, translation, description;
        String[] array = new String[0];
        switch (type) {
            case 1:
                word = cards.get(index).getWord();
                translation = cards.get(index).getTranslation();
                fillCard(translation);
                fillList(word, type);
                array = new String[]{word, translation};
                break;
            case 2:
                word = cards.get(index).getWord();
                translation = cards.get(index).getTranslation();
                fillCard(word);
                fillList(translation, type);
                array = new String[]{word, translation};
                break;
            case 3:
                word = cards.get(index).getWord();
                description = cards.get(index).getDescription();
                fillCard(description);
                fillList(word, type);
                array = new String[]{word, description};
                break;
        }
        return array;
    }

    private void fillCard(String value) {
        searchTextView.setText(value);
    }

    private void fillList(String answer, int type) {
        List<String> values = new ArrayList<String>();
        values.add(answer);
        SecureRandom random = new SecureRandom();
        String item;
        if (type == 1 || type == 3) {
            for (int i = 0; i < 3; i++) {
                do {
                    item = cards.get(random.nextInt(cards.size())).getWord();
                } while (item.equalsIgnoreCase(answer));
                values.add(item);
            }
        } else if (type == 2) {
            for (int i = 0; i < 3; i++) {
                do {
                    item = cards.get(random.nextInt(cards.size())).getTranslation();
                } while (item.equalsIgnoreCase(answer));
                values.add(item);
            }
        }

        Collections.shuffle(values);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, values);
        answersListView.setAdapter(arrayAdapter);
    }

    private void dialogCreator(int result, int size) {
        statisticConnector = new StatisticConnector(getContext());
        AlertDialog resultDialog = new AlertDialog.Builder(getContext())
                .setTitle("Окно результата тренировки")
                .setMessage("Ваш результат:  "
                        + result + " из " + size)
                .setPositiveButton("Сохранить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        statisticConnector.saveStatistic(new StatisticItem(trainingType, folder, tag, result + "/" + size));
                        Intent backIntent = new Intent(getContext(), TrainingActivity.class);
                        startActivity(backIntent);
                        dialog.dismiss();
                    }
                }).create();
        resultDialog.show();
    }
}
