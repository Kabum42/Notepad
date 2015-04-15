package com.example.ams.simplenotes;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.ams.simplenotes.MainActivity;

/**
 * Created by AMS on 13/04/2015.
 */

public class TopicDialogFragment extends DialogFragment {
    public interface TopicDialogListener {
        public void onPositiveButtonClick(String topicName);
    }

    private TopicDialogListener mListener;
    private EditText dialogEditText;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = ((MainActivity)activity).getTopicListener();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity(),
                AlertDialog.THEME_HOLO_DARK);
        LayoutInflater inflater = this.getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_newtopic, null);

        builder.setView(view);

        dialogEditText = (EditText) view.findViewById(R.id.dialogEditText);

        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String topicName = dialogEditText.getText().toString();
                mListener.onPositiveButtonClick(topicName);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        return builder.create();
    }
}
