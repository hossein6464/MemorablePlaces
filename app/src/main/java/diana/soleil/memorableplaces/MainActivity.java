package diana.soleil.memorableplaces;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.service.autofill.OnClickAction;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    ListView listView;
    String newName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        newName = null;
        arrayList = new ArrayList<String>();
        arrayList.add("Add a new Place");
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,arrayList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("placeNumber",i);
                startActivity(intent);
                Log.i("ItemClicked", arrayList.toString());

            }
        });
        Intent newIntend = getIntent();
        if (newIntend.getExtras() != null) {
            Log.i("Extra", Objects.requireNonNull(newIntend.getStringExtra("addresses")));
            newName = (newIntend.getStringExtra("addresses"));
        }
        if (newName != null) {
           arrayList.add(newName);
            Log.i("MapClicked", arrayList.toString());
            arrayAdapter.notifyDataSetChanged();
        }



    }


}