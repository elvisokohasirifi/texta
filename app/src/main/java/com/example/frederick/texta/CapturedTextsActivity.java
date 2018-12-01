package com.example.frederick.texta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CapturedTextsActivity extends AppCompatActivity {

    ListView listview;
    List<Entry> entryList = new ArrayList<>();
    int num =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captured_texts);

        listview = findViewById(R.id.list);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Entry entry = entryList.get(i);
                Intent ii = new Intent(CapturedTextsActivity.this, ContentActivity.class);
                ii.putExtra("id", entry.getId());
                ii.putExtra("title", entry.getTitle());
                ii.putExtra("content", entry.getContent());
                ii.putExtra("date", entry.getDate());
                startActivity(ii);
                finish();

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }catch(Exception e){
        }
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Entries");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                entryList.clear();
                for(DataSnapshot personSnapshot: dataSnapshot.getChildren()){
                    Entry item = personSnapshot.getValue(Entry.class);
                    entryList.add(item);
                }
                ArrayAdapter adapter = new ListAdapter(CapturedTextsActivity.this, entryList);
                listview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (num == 1) {
            finish();
        } else {
            num++;
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
            Timer buttonTimer = new Timer();
            buttonTimer.schedule(new TimerTask() {

                @Override
                public void run() {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            num = 0;
                        }
                    });
                }
            }, 2300);
        }
    }


}
