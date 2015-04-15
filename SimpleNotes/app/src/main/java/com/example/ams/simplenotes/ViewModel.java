package com.example.ams.simplenotes;

import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by AMS on 13/04/2015.
 */
public class ViewModel {

    private static ViewModel instance;
    private INotesModel model;

    private INotesView notesView;
    private IEditView editView;
    private ActionBarActivity currentView;

    private String currentTopic = "";
    private String currentText = "";
    private String newText = "";

    private TextView currentTopicView;

    private ArrayList<String> listOfTopics = new ArrayList<>();
    private ArrayList<String> listOfNotes = new ArrayList<>();
    private ArrayList<Integer> listOfNotesIDs = new ArrayList<>();

    private int currentNotePos = 0;
    private int currentTopicPos = 0;
    private boolean isUpdate = false;

    private boolean okButtonEnable = true;

    private int nChars = 36;
    private String bgColor = "Yellow";

    private ViewModel(INotesModel model) {
        this.model = model;
    }

    public static ViewModel getInstance(NotesModel notesModel) {
        if (instance == null)
            instance = new ViewModel(notesModel);
        return instance;
    }

    public void setNotesView(INotesView newView) {
        listOfNotes = new ArrayList<String>();
        model.getNotesFromTopic(currentTopic, listOfNotes, listOfNotesIDs);

        notesView = newView;
        notesView.fillListOfNotes(listOfNotes);
        notesView.fillListOfTopics(new ArrayList<String>(Arrays.asList(model.getListOfTopics())));
        currentView = (ActionBarActivity) notesView;
    }

    public void setEditView(IEditView newView) {

        editView = newView;
        currentView = (ActionBarActivity) editView;
        notesView.fillListOfTopics(new ArrayList<String>(Arrays.asList(model.getListOfTopics())));
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
            currentTopicPos = groupPosition;
            notesView.setTopic(topicItemName);
            setNotesView(notesView);
        }
    }

    public void onTopicsListPressedFromEdition(int g_position, String name) {

        currentTopicView = (TextView) currentView.findViewById(R.id.currentTopic);
        currentTopicView.setText(currentTopic);

        currentTopicPos = g_position;

    }

    public void onNotesListPressed(int position, String noteItem) {
        currentText = noteItem;
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

    public void onTextInNoteChanged(String noteContents) {
        currentText = noteContents;
        //...
    }

    public void onOkPressed() {
        String topicName = editView.getTopicName();
        //currentTopic = editView.getTopicName();
        currentText = editView.getTextInNote();
        if (isUpdate){
            model.updateNote(getNoteIdFromPos(currentNotePos), model.getIdTopic(currentTopic), newText);
        }
        else{
            model.insertNewNote(model.getIdTopic(currentTopic), newText);
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
        model.getNotesFromTopic(currentTopic, new ArrayList<String>(), idArray);
        return idArray.get(pos);
    }

    private void onExitEdition(boolean isEdit)
    {
        //currentText = "";
        //currentNotePos = 0;
    }
}
