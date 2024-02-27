package com.tish.e_cards;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;


public class TrainSettingsDialog extends DialogFragment {

    CardsConnector cardsConnector;
    FoldersConnector foldersConnector;
    Spinner folderSpinner;
    Spinner tagSpinner;
    String trainName;

    public TrainSettingsDialog(Context context, String trainName) {
        cardsConnector = new CardsConnector(context);
        foldersConnector = new FoldersConnector(context);
        this.trainName = trainName;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Выберите папку и/или тег для тренировки");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View setParamsView = inflater.inflate(R.layout.set_training_params_dialog_view, null);
        folderSpinner = setParamsView.findViewById(R.id.spinner_set_folder);
        tagSpinner = setParamsView.findViewById(R.id.spinner_set_tag);
        List<String> folderNamesList = foldersConnector.getAllFolderNames();
        ArrayAdapter<String> folderAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, folderNamesList);
        folderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        folderSpinner.setAdapter(folderAdapter);
        List<String> tagList = cardsConnector.getCardTags();
        ArrayAdapter<String> tagAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, tagList);
        tagAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tagSpinner.setAdapter(tagAdapter);
        builder.setView(setParamsView);
        builder.setPositiveButton("Начать", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent trainIntent = new Intent(getContext(), TrainModelActivity.class);
                String folderName = folderSpinner.getSelectedItem().toString();
                String tag = tagSpinner.getSelectedItem().toString();
                if (!folderName.isEmpty() && !tag.isEmpty()) {
                    trainIntent.putExtra("folder", folderName);
                    trainIntent.putExtra("tag", tag);
                } else if (!folderName.isEmpty() && tag.isEmpty()) {
                    trainIntent.putExtra("folder", folderName);
                    trainIntent.putExtra("tag", "");
                } else if (folderName.isEmpty() && !tag.isEmpty()) {
                    trainIntent.putExtra("folder", "");
                    trainIntent.putExtra("tag", tag);
                } else if (folderName.isEmpty() && tag.isEmpty()) {
                    trainIntent.putExtra("folder", "");
                    trainIntent.putExtra("tag", "");
                }
                trainIntent.putExtra("trainingType", trainName);
                startActivity(trainIntent);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        return builder.create();
    }
}
