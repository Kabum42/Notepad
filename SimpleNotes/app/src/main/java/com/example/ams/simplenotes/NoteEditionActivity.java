package com.example.ams.simplenotes;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;

import static com.example.ams.simplenotes.DeleteDialogFragment.*;


public class NoteEditionActivity extends ActionBarActivity implements IEditView {

    private static final int MAX_LEN_IN_NOTE = 50;

    private ViewModel viewModel;
    private EditText note;
    private TextWatcher textWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edition);

        note = (EditText)findViewById(R.id.textInNote);
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        note.setFilters(new InputFilter[]
                        { new InputFilter.LengthFilter(MAX_LEN_IN_NOTE)});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note_edition, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, ChangeSettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return false;
    }

    @Override
    public void fillListOfTopics(ArrayList<String> listOfTopics) {
        //IDENTICOS QUE LOS DE LA MAIN ACTIVITY
    }

    @Override
    public void collapseTopic(int groupPos) {
        //IDENTICOS QUE LOS DE LA MAIN ACTIVITY
    }

    @Override
    public void setTopicName(String topicName) {

    }

    @Override
    public String getTopicName() {
        return null;
    }

    @Override
    public void setTopicNameBgColor(String bgColor) {

    }

    @Override
    public void setTextInNote(String text) {

    }

    @Override
    public String getTextInNote() {
        return null;
    }

    @Override
    public void setTextInNoteBgColor(String bgColor) {

    }

    @Override
    public void disableDelete() {

    }

    @Override
    public void disableOk() {

    }

    @Override
    public void enableOk() {

    }

    @Override
    public void setTextOk(boolean isUpdate) {

    }

    @Override
    public void exit() {

    }

    public DeleteDialogListener getDeleteListener() {
        return new DeleteDialogListener() {
            @Override
            public void onPositiveButtonClick() {
                viewModel.onConfirmDelete();
            }
        };
    }
}
