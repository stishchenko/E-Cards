package com.tish.e_cards;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class AddCardDialog extends DialogFragment {

    private FragmentSendCardDataListener fragmentSendDataListener;
    CardsConnector cardsConnector;
    EditText etWord;
    EditText etTranslation;
    EditText etTag;
    EditText etDescription;
    private String thisFolder;
    LinearLayout errorLinearLayout;
    TextView errorTextView;


    public AddCardDialog(Context mainContext, String thisFolder) {
        cardsConnector = new CardsConnector(mainContext);
        this.thisFolder = thisFolder;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            fragmentSendDataListener = (FragmentSendCardDataListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " должен реализовывать интерфейс OnFragmentInteractionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Введите параметры слова");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View addCardView = inflater.inflate(R.layout.add_card_dialog_view, null);
        etWord = addCardView.findViewById(R.id.et_new_card_word);
        etTranslation = addCardView.findViewById(R.id.et_new_card_translation);
        etTag = addCardView.findViewById(R.id.et_new_card_tag);
        etDescription = addCardView.findViewById(R.id.et_new_card_description);
        builder.setView(addCardView);
        builder.setPositiveButton("Сохранить", null);
        builder.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog thisDialog = builder.create();
        thisDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button saveButton = thisDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String word = etWord.getText().toString().trim();
                        String translation = etTranslation.getText().toString().trim();
                        if (word.isEmpty()) {
                            errorLinearLayout = addCardView.findViewById(R.id.ll_new_card_error);
                            errorLinearLayout.setVisibility(View.VISIBLE);
                            errorTextView = addCardView.findViewById(R.id.tv_new_card_error);
                            errorTextView.setText("Поле слова не может быть пустым");
                        } else if (translation.isEmpty()) {
                            errorLinearLayout = addCardView.findViewById(R.id.ll_new_card_error);
                            errorLinearLayout.setVisibility(View.VISIBLE);
                            errorTextView = addCardView.findViewById(R.id.tv_new_card_error);
                            errorTextView.setText("Поле перевода не может быть пустым");
                        } else if (word.isEmpty() && translation.isEmpty()) {
                            errorLinearLayout = addCardView.findViewById(R.id.ll_new_card_error);
                            errorLinearLayout.setVisibility(View.VISIBLE);
                            errorTextView = addCardView.findViewById(R.id.tv_new_card_error);
                            errorTextView.setText("Поля слова и перевода не можегут быть пустыми");

                        } else {
                            Card sendNewCard = new Card(word, translation, etTag.getText().toString().trim(), etDescription.getText().toString().trim());
                            sendNewCard.setFolderName(thisFolder);
                            long insertResult = cardsConnector.insertNewCard(sendNewCard);
                            if (insertResult > -1)
                                fragmentSendDataListener.onSendData(sendNewCard);
                            else
                                fragmentSendDataListener.onSendData(insertResult);
                            thisDialog.dismiss();
                        }
                    }
                });
            }
        });

        return thisDialog;
    }

}
