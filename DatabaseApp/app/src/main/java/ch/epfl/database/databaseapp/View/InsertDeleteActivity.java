package ch.epfl.database.databaseapp.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.database.databaseapp.Model.DatabaseConnector;
import ch.epfl.database.databaseapp.R;

public class InsertDeleteActivity extends AppCompatActivity {

    private String[] listAttributes = new String[]{"Text1","Text2","Text3","Text4"};
    private String spinnerInsertValue = "";
    private String spinnerDeleteValue = "";

    // Spinner Drop down elements
    private final String[] tables = new String[]{"Artists", "Authors", "BrandGroup", "Characters",
            "Color", "Country", "Creators", "Editing",
            "Editor", "Genre", "Has_Characters", "Has_Genre",
            "Has_Serie_Type", "Has_Story_Type", "Has_Type",
            "IndiciaPublisher", "Inks", "Issue", "IssueReprint",
            "Language", "Letters", "Pencils", "Publisher",
            "Script", "Serie_Type", "Series", "Story",
            "Story_Type"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_delete);

        TabHost tabs = (TabHost) findViewById(R.id.tab_host);
        tabs.setup();

        // Insert
        TabHost.TabSpec insertTab = tabs.newTabSpec("Insert");
        insertTab.setContent(R.id.insert_tab);
        insertTab.setIndicator("Insert");
        tabs.addTab(insertTab);

        // Delete
        TabHost.TabSpec deleteTab = tabs.newTabSpec("Delete");
        deleteTab.setContent(R.id.delete_tab);
        deleteTab.setIndicator("Delete");
        tabs.addTab(deleteTab);

        final Spinner tablesMenuInsert = (Spinner) findViewById(R.id.tables_menu_insert);
        Spinner tablesMenuDelete = (Spinner) findViewById(R.id.tables_menu_delete);

        // Spinner click listener
        tablesMenuInsert.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    spinnerInsertValue = tables[i];
                    switch (i){
                        case 0:case 1:case 3:case 6:case 8:
                        case 9:case 24:case 27: listAttributes = new String[]{"ID", "Name"};
                                        break;

                        case 2: listAttributes = new String[]{"ID", "Name", "Publisher_id",
                                                                "Year_began", "Year_ended",
                                                                "Notes", "URL"};
                                break;

                        case 4:case 21: listAttributes = new String[]{"Story_id", "Artist_id"};
                                break;

                        case 5:case 19: listAttributes = new String[]{"ID", "Code", "Name"};
                                break;

                        case 7: listAttributes = new String[]{"Story_id", "Editor_id"};
                                break;

                        case 10: listAttributes = new String[]{"Story_id", "Character_id"};
                                break;

                        case 11: listAttributes = new String[]{"Story_id", "Genre_id"};
                                break;

                        case 12:case 14: listAttributes = new String[]{"Serie_id", "Type_id"};
                                break;

                        case 13: listAttributes = new String[]{"Story_id", "Type_id"};
                                break;

                        case 15: listAttributes = new String[]{"ID", "Name", "Year_began",
                                                                "Year_ended", "Notes", "URL",
                                                                "Country_id", "Publisher_id",
                                                                "IS_Surrogate"};
                                break;

                        case 16: listAttributes = new String[]{"Story_id", "Artist_id"};
                                break;

                        case 17: listAttributes = new String[]{"ID", "Issue_Number", "Indicia_publisher_ID",
                                                                "Publication_Date", "Price", "Page_Count",
                                                                "Indicia_Frequency", "Editing", "Notes",
                                                                "ISBN", "Valid_ISBN", "BarCode", "Title",
                                                                "On_Sale_Date", "Rating"};
                                break;

                        case 18: listAttributes = new String[]{"ID", "Origin_Issue_ID", "Target_Issue_ID"};
                                break;

                        case 20: listAttributes = new String[]{"Story_id", "Creators_id"};
                                break;

                        case 22: listAttributes = new String[]{"ID", "Name", "Year_began", "Year_ended",
                                                                "Notes", "URL", "Country_ID"};
                                break;

                        case 23: listAttributes = new String[]{"Story_id", "Author_id"};
                                break;

                        case 25: listAttributes = new String[]{"ID", "Name", "Format", "Publication_Dates",
                                                                "Year_began", "Year_ended", "First_Issue_ID",
                                                                "Last_issue_ID", "Publisher_ID", "Country_ID",
                                                                "Language_ID", "Notes", "Color", "Dimensions",
                                                                "Paper_Stock", "Binding", "Publishing_Format"};
                                break;

                        case 26: listAttributes = new String[]{"ID", "Title", "Issue_ID", "Feature",
                                                                "Characters_ID", "Synopsis", "Reprint_Notes",
                                                                "Notes"};
                                break;

                        default :
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Spinner click listener
        tablesMenuDelete.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerDeleteValue = tables[i];
                switch (i) {
                    case 0:
                    case 1:
                    case 3:
                    case 6:
                    case 8:
                    case 9:
                    case 24:
                    case 27:
                        listAttributes = new String[]{"ID", "Name"};
                        break;

                    case 2:
                        listAttributes = new String[]{"ID", "Name", "Publisher_id",
                                "Year_began", "Year_ended",
                                "Notes", "URL"};
                        break;

                    case 4:
                    case 21:
                        listAttributes = new String[]{"Story_id", "Artist_id"};
                        break;

                    case 5:
                    case 19:
                        listAttributes = new String[]{"ID", "Code", "Name"};
                        break;

                    case 7:
                        listAttributes = new String[]{"Story_id", "Editor_id"};
                        break;

                    case 10:
                        listAttributes = new String[]{"Story_id", "Character_id"};
                        break;

                    case 11:
                        listAttributes = new String[]{"Story_id", "Genre_id"};
                        break;

                    case 12:
                    case 14:
                        listAttributes = new String[]{"Serie_id", "Type_id"};
                        break;

                    case 13:
                        listAttributes = new String[]{"Story_id", "Type_id"};
                        break;

                    case 15:
                        listAttributes = new String[]{"ID", "Name", "Year_began",
                                "Year_ended", "Notes", "URL",
                                "Country_id", "Publisher_id",
                                "IS_Surrogate"};
                        break;

                    case 16:
                        listAttributes = new String[]{"Story_id", "Artist_id"};
                        break;

                    case 17:
                        listAttributes = new String[]{"ID", "Issue_Number", "Indicia_publisher_ID",
                                "Publication_Date", "Price", "Page_Count",
                                "Indicia_Frequency", "Editing", "Notes",
                                "ISBN", "Valid_ISBN", "BarCode", "Title",
                                "On_Sale_Date", "Rating"};
                        break;

                    case 18:
                        listAttributes = new String[]{"ID", "Origin_Issue_ID", "Target_Issue_ID"};
                        break;

                    case 20:
                        listAttributes = new String[]{"Story_id", "Creators_id"};
                        break;

                    case 22:
                        listAttributes = new String[]{"ID", "Name", "Year_began", "Year_ended",
                                "Notes", "URL", "Country_ID"};
                        break;

                    case 23:
                        listAttributes = new String[]{"Story_id", "Author_id"};
                        break;

                    case 25:
                        listAttributes = new String[]{"ID", "Name", "Format", "Publication_Dates",
                                "Year_began", "Year_ended", "First_Issue_ID",
                                "Last_issue_ID", "Publisher_ID", "Country_ID",
                                "Language_ID", "Notes", "Color", "Dimensions",
                                "Paper_Stock", "Binding", "Publishing_Format"};
                        break;

                    case 26:
                        listAttributes = new String[]{"ID", "Title", "Issue_ID", "Feature",
                                "Characters_ID", "Synopsis", "Reprint_Notes",
                                "Notes"};
                        break;

                    default:
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tables);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        tablesMenuInsert.setAdapter(dataAdapter);
        tablesMenuDelete.setAdapter(dataAdapter);

        MyListAdapter myListAdapter = new MyListAdapter();
        final ListView listInsert = (ListView) findViewById(R.id.elements_to_insert);
        final ListView listDelete = (ListView) findViewById(R.id.elements_to_delete);
        listInsert.setAdapter(myListAdapter);
        listDelete.setAdapter(myListAdapter);

        Button submitInsert = (Button) findViewById(R.id.submit_insert);
        submitInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> userEdition = new ArrayList<>();
                for(int i = 0; i < listInsert.getAdapter().getCount(); i++){
                    userEdition.add(listInsert.getChildAt(i).toString());
                }

                String query = "INSERT INTO " + spinnerInsertValue + "VALUES" + userEdition.toString();

                DatabaseConnector db = new DatabaseConnector();
                db.insertElement(query);
                db.closeConnection();
            }
        });

        Button submitDelete = (Button) findViewById(R.id.submit_delete);
        submitDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> userEdition = new ArrayList<>();
                for(int i = 0; i < listDelete.getAdapter().getCount(); i++){
                    userEdition.add(listDelete.getChildAt(i).toString());
                }

                String query = "DELETE FROM " + spinnerDeleteValue + "WHERE" + userEdition.toString();

                DatabaseConnector db = new DatabaseConnector();
                db.insertElement(query);
                db.closeConnection();
            }
        });

    }

    private class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            if (listAttributes != null && listAttributes.length != 0) {
                return listAttributes.length;
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return listAttributes[position];
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            //ViewHolder holder = null;
            final ViewHolder holder;
            if (convertView == null) {

                holder = new ViewHolder();
                LayoutInflater inflater = InsertDeleteActivity.this.getLayoutInflater();
                convertView = inflater.inflate(R.layout.insert_delete_row, null);
                holder.textView = (TextView) convertView.findViewById(R.id.row_name);
                holder.editText = (EditText) convertView.findViewById(R.id.edit_value);

                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.textView.setText(listAttributes[position]);

            return convertView;
        }

        private class ViewHolder {
            TextView textView;
            EditText editText;
        }

    }
}
