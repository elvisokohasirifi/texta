package com.example.frederick.texta;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.cloudinary.Cloudinary;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.cloudinary.utils.ObjectUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button upload;
    Button capture;
    Uri filePath;
    EditText tv;
    ProgressBar pb;
    Button save;
    Button cancel;
    EditText title;
    int num =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        upload = findViewById(R.id.upload);
        capture = findViewById(R.id.capture);
        tv = findViewById(R.id.tv);
        pb = findViewById(R.id.pb);
        save = findViewById(R.id.but);
        cancel = findViewById(R.id.cancel);
        title = findViewById(R.id.title);

        title.setVisibility(View.GONE);

        Map config = new HashMap();
        config.put("cloud_name", "wuyeh");
        config.put("api_key", "653752973914919");
        config.put("api_secret", "eaym6NvMmGadchJAfyUGHCYQfjM");
        MediaManager.init(this, config);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), 1001);
            }
        });

        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(i);

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
                save.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);
                tv.getText().clear();
                title.getText().clear();
                title.setVisibility(View.GONE);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title.getText().clear();
                tv.getText().clear();
                save.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);
                title.setVisibility(View.GONE);

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1001 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();



            String requestid = MediaManager.get().upload(filePath).unsigned("texta_ocr").callback(new UploadCallback() {
                @Override
                public void onStart(String requestId) {
                    Toast.makeText(MainActivity.this, "onstart", Toast.LENGTH_SHORT).show();
                    pb.setVisibility(View.VISIBLE);

                }

                @Override
                public void onProgress(String requestId, long bytes, long totalBytes) {
                    Toast.makeText(MainActivity.this, "onProgress", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onSuccess(String requestId, Map resultData) {

                    Toast.makeText(MainActivity.this, "It has worked", Toast.LENGTH_SHORT).show();

                    Map aa = (Map) resultData.get("info");
                    Map bb = (Map) aa.get("ocr");
                    Map cc = (Map) bb.get("adv_ocr");
                    List dd = (List) cc.get("data");
                    Map ee = (Map) dd.get(0);
                    List ff = (List) ee.get("textAnnotations");
                    Map gg = (Map) ff.get(0);
                    String hh = (String) gg.get("description");
                    //System.out.print(ee.toString());
                    Log.d("peter", hh.toString());
                   // List ff = (List) ee.get("testAnnotations");
                    //Map ee = (Map) dd.get("textAnnotations");
                    tv.setText(hh.toString());
                   // Toast.makeText(MainActivity.this, cc.toString(), Toast.LENGTH_LONG).show();
                    pb.setVisibility(View.GONE);
                    save.setVisibility(View.VISIBLE);
                    cancel.setVisibility(View.VISIBLE);
                    title.setVisibility(View.VISIBLE);



                }

                @Override
                public void onError(String requestId, ErrorInfo error) {
                    Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    tv.setText(error.toString());

                    pb.setVisibility(View.GONE);

                }

                @Override
                public void onReschedule(String requestId, ErrorInfo error) {

                }
            }).dispatch();


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tabs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.texts) {
            Intent i = new Intent(MainActivity.this, CapturedTextsActivity.class);
            startActivity(i);

        }
        return true;
    }

    public void save(){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Entries");
        String id = db.push().getKey();
        Entry entry = new Entry(id, "1, Dec 2018", title.getText().toString(), tv.getText().toString());
        db.child(id).setValue(entry);
        Toast.makeText(this, "Saved Successfully", Toast.LENGTH_SHORT).show();

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
