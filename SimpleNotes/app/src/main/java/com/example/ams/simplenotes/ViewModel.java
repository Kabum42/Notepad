package com.example.ams.simplenotes;

import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by AMS on 13/04/2015.
 */
public class ViewModel {

    ExpandableListView topicGroup;

    private static ViewModel instance;
    private INotesModel model;

    private INotesView notesView;
    private IEditView editView;
    private ActionBarActivity currentView;

    private TextView currentTopic;
    private String previousText = "";
    private String newText = "";

    private ArrayList<String> listOfTopics = new ArrayList<>();
    private ArrayList<String> listOfNotes = new ArrayList<>();
    private ArrayList<String> listOfNotesIDs = new ArrayList<>();

    private int currentNotePos = 0;
    private boolean isUpdate = false;

    private boolean okButtonEnable = true;

    private int nChars = 36;
    private String bgColor = "Yellow";

    public static ViewModel getInstance(NotesModel notesModel) {
        if (instance == null)
            instance = new ViewModel(notesModel);
        return instance;
    }

    public void setNotesView(INotesView newView) {
        model.getNotesFromTopic(currentTopic, listOfNotes, listOfNotesID);
        notesView = newView;
        notesView.fillListOfTopics(listOfTopics);
        notesView.fillListOfNotes(listOfNotes);
        currentView = (ActionBarActivity) notesView;
    }

    public void setEditView(IEditView newView) {
        editView = newView;
        currentView = (ActionBarActivity) editView;
        editView.setTextInNote(currentText);
    }

    public void setCharsToShow(int charsToShow) {
        nChars = charsToShow;
    }

    public void setBgNotesColor(String bgNotesColor){
        bgColor = bgNotesColor;
    }

    public void onTopicsListPressed(int groupPosition, String topicItemName) {
        if (topicItemName.equals("New...")){
            notesView.askForATopic();
        }
        else
        {
            //currentTopicPos = groupPosition;
            notesView.setTopic(topicItemName);
            setNotesView(notesView);
        }
    }

    public void onTopicsListPressedFromEdition(int g_position, String name) {

        currentTopic = (TextView) currentView.findViewById(R.id.currentTopic);
        currentTopic.setText(currentTopic);

        currentTopicPos = g_position;

    }

    public void onNotesListPressed(int position, String noteItem) {
        previousText = noteItem;
        newText = noteItem;
        currentNotePos = position;

        notesView.switchToNoteEdition();

        setEditView(editView);
    }

    public void onNewTopicCreated(String name){
        if(!name.equals("New...") && !name.equals("") && !name.equals(" ")) {
            model.insertNewTopic(name);
            setNotesView(notesView);
            notesView.setTopic(name);
        }
    }

    private ViewModel(INotesModel model) {
        this.model = model;
    }

    public void onTextInNoteChanged(String noteContents) {
        previousText = noteContents;
        //...
    }

    public void onOkPressed() {
        String topicName = editView.getTopicName();
        currentTopic = editView.getTopicName();
        previousText = editView.getTextInNote();
        if (isUpdate){
            model.updateNote(getNoteIdFromPos(currentNotePos), model.getIdTopic(currentTopic.getText().toString()), newText);
        }
        else{
            model.insertNewNote(model.getIdTopic(currentTopic.getText().toString()), newText);
        }
        onExitEdition(true);
        editView.exit();
    }

    public void onCancelPressed() {
        onExitEdition(false);
        editView.exit();
    }

    public void onConfirmDelete() {
        model.deleteNote(getNoteIdFromPos(currentNotePos));
        onExitEdition(false);
        editView.exit();
    }

    private int getNoteIdFromPos(int pos){
        ArrayList<Integer> idArray = new ArrayList<>();
        model.getNotesFromTopic(currentTopic.getText().toString(), new ArrayList<String>(), idArray);
        return idArray.get(pos);
    }

    private void onExitEdition(boolean isEdit)
    {
        //currentText = "";
        //currentNotePos = 0;
    }
}
