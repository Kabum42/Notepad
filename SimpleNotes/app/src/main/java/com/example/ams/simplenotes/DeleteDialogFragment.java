package com.example.ams.simplenotes;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

/**
 * Created by AMS on 14/04/2015.
 */
public class DeleteDialogFragment extends DialogFragment {

    public interface DeleteDialogListener {
        public void onPositiveButtonClick();
    }

    private DeleteDialogListener mListener;

    @Override
    public void onAttach(Activity activity) {
        //RELLENAR
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //RELLENAR

        return null;
        //return builder.create();
    }
}
