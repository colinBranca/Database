package ch.epfl.database.databaseapp.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import ch.epfl.database.databaseapp.Model.DatabaseConnector;
import ch.epfl.database.databaseapp.R;

public class SearchActivity extends AppCompatActivity {

    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final EditText searchText = (EditText) findViewById(R.id.search_text);

        Button advancedOptions = (Button) findViewById(R.id.button_advanced);

        advancedInvisible();

        advancedOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(counter == 0) {
                    advancedVisible();
                    counter++;
                } else {
                    advancedInvisible();
                    counter--;
                }
                //TODO
            }
        });

        final ListView tablesList = (ListView) findViewById(R.id.tables_list);
        // Spinner Drop down elements
        final String[] tables = new String[]{"Artists", "Authors", "BrandGroup", "Characters",
                "Color", "Country", "Creators", "Editing",
                "Editor", "Genre", "Has_Characters", "Has_Genre",
                "Has_Serie_Type", "Has_Story_Type", "Has_Type",
                "IndiciaPublisher", "Inks", "Issue", "IssueReprint",
                "Language", "Letters", "Pencils", "Publisher",
                "Script", "Serie_Type", "Series", "Story",
                "Story_Type"};
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, tables);

        // attaching data adapter to spinner
        tablesList.setAdapter(dataAdapter);

        // Fill box when element clicked
        tablesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // change the checkbox state
                CheckedTextView checkedTextView = ((CheckedTextView)view);
                checkedTextView.setChecked(!checkedTextView.isChecked());
            }
        });

        Button submitButton = (Button) findViewById(R.id.submit_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userQuery = searchText.getText().toString();
                SparseBooleanArray positions = tablesList.getCheckedItemPositions();
                DatabaseConnector connector = new DatabaseConnector();
                //TODO
            }
        });

    }

    private void advancedInvisible(){
        TextView advancedText = (TextView) findViewById(R.id.tables_advanced);
        ListView listTables = (ListView) findViewById(R.id.tables_list);
        advancedText.setVisibility(TextView.INVISIBLE);
        listTables.setVisibility(TextView.INVISIBLE);
    }

    private void advancedVisible(){
        TextView advancedText = (TextView) findViewById(R.id.tables_advanced);
        ListView listTables = (ListView) findViewById(R.id.tables_list);
        advancedText.setVisibility(TextView.VISIBLE);
        listTables.setVisibility(TextView.VISIBLE);
    }
}
