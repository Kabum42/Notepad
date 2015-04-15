package main.java.com.example.ams.simplenotes;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




public class MainActivity extends ActionBarActivity implements INotesView {

    private static final String TOPIC_LIST_HEADER = "Topic_header";
    private static final String TOPIC_LIST_HEADER_NAME = "Topics";
    private static final String TOPIC_GROUP_HEADER = "Topic_group";

    private ViewModel viewModel;
    private ListView notes;
    private ExpandableListView topics;
    private List<Map<String, String>> groupForTopics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] initial_topics = this.getResources().getStringArray(R.array.initial_topics);
        NotesDatabase myDBNotes = NotesDatabase.getInstance(this);
        NotesModel notesModel = NotesModel.getInstance(myDBNotes, initial_topics);

        viewModel = viewModel.getInstance(notesModel);
        notes = (ListView) findViewById(R.id.notesForTopicsList);
        topics = (ExpandableListView) findViewById(R.id.topicsList);

        groupForTopics = new ArrayList<Map<String, String>>() {{
            add(new HashMap<String, String>() {{
                put(TOPIC_LIST_HEADER, TOPIC_LIST_HEADER_NAME);
            }});
        }};

        topics.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                HashMap<String, String> topicItem = (HashMap<String, String>)
                        parent.getExpandableListAdapter().getChild(groupPosition,
                                childPosition);
                String topicItemName = topicItem.get(TOPIC_GROUP_HEADER);
                viewModel.onTopicsListPressed(groupPosition, topicItemName);
                return true;
            }
        });

        notes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Object noteItem = notes.getItemAtPosition(position);
                viewModel.onNotesListPressed(position, noteItem.toString());
            }
        });

    }

    @Override
    public void setTopic(String topic) {
        //CAMBIA EL TEXTO DEL TEXTVIEW QUE MUESTRA EL TEMA ESCOGIDO.
    }

    @Override
    public void setTopicBgColor(String bgColor) {
        //CAMBIA EL COLOR DEL TEXTVIEW QUE MUESTRA EL TEMA ESCOGIDO.
    }

    @Override
    public void fillListOfNotes(ArrayList<String> listOfNotes){
        ArrayAdapter<String> adapterForNotes = new ArrayAdapter<String>(this, R.layout.list_item, listOfNotes);
        notes.setAdapter(adapterForNotes);
    }

    @Override
    public void fillListOfTopics(ArrayList<String> listOfTopics) {

        List<List<Map<String,String>>> finalListOfTopics =
                new ArrayList<List<Map<String, String>>>();
        List<Map<String, String>> group = new ArrayList<Map<String, String>>();
        HashMap<String, String> item;

         SimpleExpandableListAdapter adapterForTopics = new SimpleExpandableListAdapter(this, groupForTopics,
                R.layout.topic_group, new String[]{TOPIC_LIST_HEADER},
                new int[]{R.id.TopicListHeader}, finalListOfTopics,
                R.layout.topic_item, new String[]{TOPIC_GROUP_HEADER},
                new int[]{R.id.TopicListItem});
        topics.setAdapter(adapterForTopics);
    }

    @Override
    public void collapseTopic(int groupPos) {
        //COLAPSA LA EXPANDABLE TOPIC LIST
    }

    @Override
    public void switchToNoteEdition() {
        //COMIENZA LA NOTE EDITION ACTIVITY
    }

    @Override
    public void askForATopic() {
        FragmentManager fm = getFragmentManager();
        TopicDialogFragment newTopicDialog = new TopicDialogFragment();
        collapseTopic(0);
        newTopicDialog.setCancelable(false);
        newTopicDialog.show(fm, "TopicDialogFragment");
    }

    public TopicDialogListener getTopicListener() {
        return new TopicDialogListener() {
            @Override
            public void onPositiveButtonClick(String topicName) {
            }
        };
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
