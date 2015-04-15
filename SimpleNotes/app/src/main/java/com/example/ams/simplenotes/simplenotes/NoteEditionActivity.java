package com.example.ams.simplenotes.simplenotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.ams.simplenotes.Constants.MAX_LEN_TEXT_IN_NOTE;
import static com.example.ams.simplenotes.DeleteDialogFragment.DeleteDialogListener;


public class NoteEditionActivity extends ActionBarActivity implements IEditView {

    private static final String TOPIC_LIST_HEADER = "Topic_header";
    private static final String TOPIC_LIST_HEADER_NAME = "Topics";
    private static final String TOPIC_GROUP_HEADER = "Topic_group";

    private ViewModel viewModel;
    private ListView notes;
    private ExpandableListView topics;
    private List<Map<String, String>> groupForTopics;
    private ArrayAdapter<String> adapterForNotes;
    private SimpleExpandableListAdapter adapterForTopics;

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

                //RELLENAR
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //RELLENAR
            }

            @Override
            public void afterTextChanged(Editable s) {

                //RELLENAR
            }
        };

        note.setFilters(new InputFilter[]
                        { new InputFilter.LengthFilter(MAX_LEN_TEXT_IN_NOTE)});
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
        List<List<Map<String,String>>> finalListOfTopics =
                new ArrayList<List<Map<String, String>>>();
        List<Map<String, String>> group = new ArrayList<Map<String, String>>();
        HashMap<String, String> item;

        for (int i = 0; i<listOfTopics.size(); i++){
            item = new HashMap<String, String>();
            item.put(TOPIC_GROUP_HEADER, listOfTopics.get(i).toString());
            group.add(item);
        }

        finalListOfTopics.add(group);

        adapterForTopics = new SimpleExpandableListAdapter(this, groupForTopics,
                R.layout.topic_group, new String[]{TOPIC_LIST_HEADER},
                new int[]{R.id.TopicListHeader}, finalListOfTopics,
                R.layout.topic_item, new String[]{TOPIC_GROUP_HEADER},
                new int[]{R.id.TopicListItem});

        topics.setAdapter(adapterForTopics);
    }

    @Override
    public void collapseTopic(int groupPos) { topics.collapseGroup(groupPos); }

    @Override
    public void setTopicName(String topicName) {
        TextView currentTopic = (TextView) findViewById(R.id.currentTopic);
        currentTopic.setText(topicName);
    }

    @Override
    public String getTopicName() {
        TextView currentTopic = (TextView) findViewById(R.id.currentTopic);
        return currentTopic.getText().toString();
    }

    @Override
    public void setTopicNameBgColor(String bgColor) {
        TextView currentTopic = (TextView) findViewById(R.id.currentTopic);
        currentTopic.setBackgroundColor(new Integer(bgColor));
    }

    @Override
    public void setTextInNote(String text) { note.setText(text); }

    @Override
    public String getTextInNote() {
        return note.getText().toString();
    }

    @Override
    public void setTextInNoteBgColor(String bgColor) {note.setBackgroundColor(new Integer(bgColor));}

    @Override
    public void disableDelete() {
        Button deleteButton = (Button)findViewById(R.id.deleteButton);
        deleteButton.setEnabled(false);
    }

    @Override
    public void disableOk() {
        Button okButton = (Button)findViewById(R.id.okButton);
        okButton.setEnabled(false);
    }

    @Override
    public void enableOk() {
        Button okButton = (Button)findViewById(R.id.okButton);
        okButton.setEnabled(false);
    }

    @Override
    public void setTextOk(boolean isUpdate) {
        Button okButton = (Button)findViewById(R.id.okButton);
        okButton.setText(isUpdate ? "Modify" : "Create");
    }

    @Override
    public void exit() {
        finish();
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
