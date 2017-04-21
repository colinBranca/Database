package ch.epfl.database.databaseapp;

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

public class InsertDeleteActivity extends AppCompatActivity {

    private final String[] arrText = new String[]{"Text1","Text2","Text3","Text4"};

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

        Spinner tablesMenuInsert = (Spinner) findViewById(R.id.tables_menu_insert);
        Spinner tablesMenuDelete = (Spinner) findViewById(R.id.tables_menu_delete);

        // Spinner click listener
        tablesMenuInsert.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Spinner click listener
        tablesMenuDelete.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Spinner Drop down elements
        String[] tables = new String[]{"Table1", "Table2", "Table3", "Table4"};

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tables);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        tablesMenuInsert.setAdapter(dataAdapter);
        tablesMenuDelete.setAdapter(dataAdapter);

        MyListAdapter myListAdapter = new MyListAdapter();
        ListView listInsert = (ListView) findViewById(R.id.elements_to_insert);
        ListView listDelete = (ListView) findViewById(R.id.elements_to_delete);
        listInsert.setAdapter(myListAdapter);
        listDelete.setAdapter(myListAdapter);

        Button submitInsert = (Button) findViewById(R.id.submit_insert);
        submitInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
            }
        });

        Button submitDelete = (Button) findViewById(R.id.submit_delete);
        submitDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
            }
        });

    }

    private class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            if (arrText != null && arrText.length != 0) {
                return arrText.length;
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return arrText[position];
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

            holder.textView.setText(arrText[position]);

            return convertView;
        }

        private class ViewHolder {
            TextView textView;
            EditText editText;
        }

    }
}
