package com.vsevolodsvet.estates;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vsevolodsvet.estates.DB.SQLiteHelper;
import com.vsevolodsvet.estates.Helpers.ListEstateDialog;
import com.vsevolodsvet.estates.Objects.Estate;

public class MainActivity extends AppCompatActivity {

    private static SQLiteHelper dbHelper;

    private static int selectedPosition;

    public static SQLiteHelper getDBHelper() {
        return dbHelper;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbHelper = new SQLiteHelper(this);

        ListView listView = findViewById(R.id.mainTaskList);
        listView.setOnItemClickListener(mListViewItemClickListener);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private AdapterView.OnItemClickListener mListViewItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapter, View view,
                                int position, long currentEstateId) {

            if (position != 0) {

                selectedPosition = position;

                Estate estate = (Estate) adapter.getItemAtPosition(position);

                Bundle bundle = new Bundle();
                bundle.putLong("estateId", currentEstateId);
                bundle.putInt("position", position);
                if (true){

                }

                ListEstateDialog dialog = new ListEstateDialog();
                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(), dialog.getClass()
                        .getSimpleName());

            }
        }
    };
}
