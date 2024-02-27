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

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class AddFolderDialog extends DialogFragment {

    private EditText etNewFolder;
    private FragmentSendStringDataListener fragmentSendStringDataListener;
    FoldersConnector foldersConnector;
    LinearLayout errorLinearLayout;

    public AddFolderDialog(Context mainContext) {
        foldersConnector = new FoldersConnector(mainContext);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            fragmentSendStringDataListener = (FragmentSendStringDataListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " должен реализовывать интерфейс OnFragmentInteractionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Введите параметры новой папки");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View addFolderView = inflater.inflate(R.layout.add_folder_dialog_view, null);
        etNewFolder = addFolderView.findViewById(R.id.et_new_folder_name);
        builder.setView(addFolderView);
        builder.setPositiveButton("Сохранить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
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
                        String sendNewFolder = etNewFolder.getText().toString().trim();
                        if (!sendNewFolder.isEmpty()) {
                            long insertResult = foldersConnector.insertNewFolder(sendNewFolder);
                            if (insertResult > -1)
                                fragmentSendStringDataListener.onSendData(sendNewFolder);
                            else
                                fragmentSendStringDataListener.onSendData(String.valueOf(insertResult));
                            thisDialog.dismiss();
                        } else {
                            errorLinearLayout = addFolderView.findViewById(R.id.ll_new_folder_error);
                            errorLinearLayout.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });
        return thisDialog;
    }


}
