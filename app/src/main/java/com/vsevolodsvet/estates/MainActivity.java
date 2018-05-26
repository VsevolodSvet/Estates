package com.vsevolodsvet.estates;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.vsevolodsvet.estates.Adapters.MainActivityEstateAdapter;
import com.vsevolodsvet.estates.DB.SQLiteHelper;
import com.vsevolodsvet.estates.DialogHelpers.ListEstateDialog;
import com.vsevolodsvet.estates.DialogHelpers.OpenFileDialog;
import com.vsevolodsvet.estates.DialogHelpers.StatisticsDialog;
import com.vsevolodsvet.estates.ExtraHelpers.StatHelper;
import com.vsevolodsvet.estates.Objects.Estate;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

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

        View view = getLayoutInflater().inflate(R.layout.list_header_task, null);

        ListView listView = findViewById(R.id.mainTaskList);
        listView.addHeaderView(view);
        listView.setOnItemClickListener(mListViewItemClickListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        List<Estate> estates = dbHelper.getEstates();
        ListView listView = findViewById(R.id.mainTaskList);

        MainActivityEstateAdapter adapter = new MainActivityEstateAdapter(this, estates);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_xls_download) {
            OpenFileDialog fileDialog = new OpenFileDialog(this);
            fileDialog.setFolderIcon(this.getResources().getDrawable(R.drawable.ic_folder_open_black_48dp))
            .setFileIcon(this.getResources().getDrawable(R.drawable.ic_attachment_black_48dp))
            .setAccessDeniedMessage("Нет доступа!")
            // в качестве фильтра задаем регулярное выражение для отбора файлов только с расширениями .xls и .xlsx
            .setFilter(".*\\.xlsx?")
            .setOpenDialogListener(new OpenFileDialog.OpenDialogListener() {
                @Override
                public void OnSelectedFile(String filePath) {
                    if (!filePath.isEmpty()) {
                        try {
                            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filePath));
                            String message = dbHelper.AddXLSData(workbook, dbHelper.getWritableDatabase());
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            })
            .show();
            List<Estate> estates = dbHelper.getEstates();
            ListView listView = findViewById(R.id.mainTaskList);

            MainActivityEstateAdapter adapter = new MainActivityEstateAdapter(this, estates);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

        if (item.getItemId() == R.id.action_show_statistics) {
            StatisticsDialog dialog = new StatisticsDialog();
            Bundle bundle = new Bundle();
            List<Estate> estates = dbHelper.getEstates();
            // задание параметров для диалогового окна
            //region
            bundle.putInt("estateCount", estates.size());
            // подсчет количества объектов по комнатам
            //region
            int count1 = 0;
            int count2 = 0;
            int count3 = 0;
            int countMore = 0;
            int countUndefined = 0;
            for (Estate estate : estates) {
                if (estate.getRooms() == null) {
                    countUndefined++;
                    continue;
                }
                switch (estate.getRooms()) {
                    case 1 :
                        count1++;
                        break;
                    case 2 :
                        count2++;
                        break;
                    case 3 :
                        count3++;
                        break;
                    default:
                        countMore++;
                        break;
                }
            }
            //endregion
            bundle.putInt("estate1RoomCount", count1);
            bundle.putInt("estate2RoomCount", count2);
            bundle.putInt("estate3RoomCount", count3);
            bundle.putInt("estateMoreRoomCount", countMore);
            bundle.putInt("estateNoRoomCount", countUndefined);

            HashMap<String, List<Double>> estateRegionMPrices = StatHelper.getRegionPriceMValues(estates);
            bundle.putSerializable("estateRegionMPrices", estateRegionMPrices);
            //endregion
            dialog.setArguments(bundle);
            dialog.show(getSupportFragmentManager(), dialog.getClass()
                    .getSimpleName());
        }

        return true;
    }

    private AdapterView.OnItemClickListener mListViewItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapter, View view,
                                int position, long currentEstateId) {
            if (position != 0) {
                selectedPosition = position;
                Estate estate = (Estate) adapter.getItemAtPosition(position);
                Bundle bundle = new Bundle();
                // задание параметров bundle для конструктора диалогового окна
                //region
                bundle.putLong("estateId", estate.getId());
                bundle.putString("estateAdress", estate.getAdress());
                bundle.putDouble("estateXcoord", estate.getX_coord());
                bundle.putDouble("estateYcoord", estate.getY_coord());
                bundle.putDouble("estatePriceM", estate.getPrice_m());
                bundle.putDouble("estatePriceR", estate.getPrice_r());
                bundle.putString("estateRegion", estate.getRegion());
                bundle.putInt("estateRooms", estate.getRooms());
                bundle.putInt("estateLevel", estate.getLevel());
                bundle.putInt("estateLevelAmount", estate.getLevel_amount());
                bundle.putDouble("estateSLive", estate.getS_live());
                bundle.putDouble("estateSAll", estate.getS_all());
                bundle.putDouble("estateSR", estate.getS_r());
                bundle.putInt("estateBalcony", estate.getBalcony());
                bundle.putString("estateYear", estate.getYear().toString());
                bundle.putInt("position", position);
                //endregion
                ListEstateDialog dialog = new ListEstateDialog();
                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(), dialog.getClass()
                        .getSimpleName());

            }
        }
    };
}
