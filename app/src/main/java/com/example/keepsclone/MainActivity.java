package com.example.keepsclone;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.keepsclone.adapter.NotesAdapter;
import com.example.keepsclone.controller.ApiController;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton mButton;
    public final static int REQUEST_CODE = 1;

    View Refresh;


    private RecyclerView notesRecyclerView;
    private List<keeps_data> keepsDataList;
    private NotesAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = findViewById(R.id.floatButton);
        Refresh = findViewById(R.id.refresh);
        notesRecyclerView = findViewById(R.id.noteRecyclerView);

        mButton.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Create a note..", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, createNote.class);
            startActivityForResult(intent, REQUEST_CODE);
        });

//        notesRecyclerView.setHasFixedSize(true);
        notesRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        );

        getNotes();
        // refresh button if all the notes are showing up or any other problem
        Refresh.setOnClickListener(view -> getNotes());
    }

    //  Getting all the notes from the API service
    public void getNotes() {

        @SuppressLint("StaticFieldLeak")
        class GetNoteTask extends AsyncTask<Void, Void, List<keeps_data>> {

            @Override
            protected List<keeps_data> doInBackground(Void... voids) {

                keepsAPI keepsApiInterface = ApiController.getJson().create(keepsAPI.class);
                Call<keep> call = keepsApiInterface.getNote();

                call.enqueue(new Callback<keep>() {
                    @Override
                    public void onResponse(Call<keep> call, Response<keep> response) {
                        if (!response.isSuccessful()) {
                            Log.d("getRequest", String.valueOf(response.code()));
                            return;
                        }
                        keep responseText = response.body();

                        keepsDataList = new ArrayList<>(responseText.getKeeps());
                        notesAdapter = new NotesAdapter((ArrayList<keeps_data>) keepsDataList);
                        notesRecyclerView.setAdapter(notesAdapter);

                    }

                    @Override
                    public void onFailure(Call<keep> call, Throwable t) {
                        Log.e("response failure", t.getMessage());
                    }
                });
                return null;
            }
        }
        new GetNoteTask().execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            getNotes();
        }
    }
}