package com.example.songsplayer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;
    public static ArrayList<File> arrayList = new ArrayList<File>();
    RecyclerView recyclerViewAudioList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLayoutView();
        fetchAudioList();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            runTimePermission();
        }
    }

    private void initLayoutView() {
        recyclerViewAudioList = findViewById(R.id.recyclerViewAudioList);
    }


    private void runTimePermission() {

        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)) {

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
        } else {
            Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show();
        }


    }

    private void fetchAudioList() {
        final ArrayList<File> audioFileList = findAllAudioFiles(Environment.getExternalStorageDirectory());
        AudioAdapter adapter = new AudioAdapter(audioFileList, getApplicationContext());
        recyclerViewAudioList.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAudioList.setAdapter(adapter);

    }

    private ArrayList<File> findAllAudioFiles(File file) {
        ArrayList<File> arrayList = new ArrayList<File>();
        File[] files = file.listFiles();
        for (File singleFile : files) {
            if (singleFile.isDirectory() && !singleFile.isHidden()) {
                arrayList.addAll(findAllAudioFiles(singleFile));
            } else {
                if (singleFile.getName().endsWith(".mp3") || singleFile.getName().endsWith(".wav")) {
                    arrayList.add(singleFile);
                }
            }
        }
        return arrayList;

    }


}