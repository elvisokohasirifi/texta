package com.example.frederick.texta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ContentActivity extends AppCompatActivity {

    String id;
    String title;
    String content;
    String date;
    EditText title_et;
    TextView date_et;
    EditText content_et;
    Button save;
    Button cancel;
    int num =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        title_et = findViewById(R.id.title);
        date_et = findViewById(R.id.date);
        content_et = findViewById(R.id.content);
        save = findViewById(R.id.save);
        cancel = findViewById(R.id.cancel);


        Intent i = getIntent();
        id = i.getStringExtra("id");
        title = i.getStringExtra("title");
        content = i.getStringExtra("content");
        date = i.getStringExtra("date");

        title_et.setText(title);
        date_et.setText(date);
        content_et.setText(content);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save(id);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ContentActivity.this, CapturedTextsActivity.class);
                startActivity(i);
                finish();
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.content_tabs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.delete) {
            DatabaseReference db = FirebaseDatabase.getInstance().getReference("Entries").child(id);
            db.removeValue();
            Toast.makeText(this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(ContentActivity.this, CapturedTextsActivity.class);
            startActivity(i);
            finish();


        }

        if (item.getItemId() == R.id.share) {
            Intent i = new Intent();
            i.setAction(Intent.ACTION_SEND);
            i.putExtra(Intent.EXTRA_TEXT, content_et.getText().toString());
            i.setType("text/plain");
            startActivity(i);
        }

        return true;
    }

    public void save(String id){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Entries");
        Entry entry = new Entry(id, date_et.getText().toString(), title_et.getText().toString(), content_et.getText().toString());
        db.child(id).setValue(entry);
        Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show();

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
