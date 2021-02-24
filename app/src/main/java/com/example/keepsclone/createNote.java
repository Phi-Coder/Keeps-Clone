package com.example.keepsclone;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class createNote extends AppCompatActivity {

    TextView noteDate;
    EditText noteTitle, noteBody;
    ExtendedFloatingActionButton submitNote;
    private String selectedNoteColor;

    public static final String BASE_URL = "https://appsbyyuvraj.pythonanywhere.com/keeps/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        noteTitle = findViewById(R.id.noteTitle);
        noteBody = findViewById(R.id.noteBody);
        submitNote = findViewById(R.id.noteDone);
        noteDate = findViewById(R.id.dateTime);


        noteDate.setText(
                new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.getDefault())
                        .format(new Date())
        );

        selectedNoteColor = "#22303C";

        // post task in background
        @SuppressLint("StaticFieldLeak")
        class SaveNoteTask extends AsyncTask<Void, Void, Void> {

            // getting text from the text views
            final String title = noteTitle.getText().toString();
            final String body = noteBody.getText().toString();
            final Object dateTime = noteDate.getText().toString();

            @Override
            protected Void doInBackground(Void... voids) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                com.example.keepsclone.keepsAPI keepsApi = retrofit.create(com.example.keepsclone.keepsAPI.class);

                Log.i("date time info: ", String.valueOf(dateTime)); //

                com.example.keepsclone.keeps_data keepsData = new com.example.keepsclone.keeps_data(body,
                        "#ff6ec7", dateTime, 1, title);

                Call<com.example.keepsclone.keeps_data> keepCall = keepsApi.createNote(keepsData);

                keepCall.enqueue(new Callback<com.example.keepsclone.keeps_data>() {
                    @Override
                    public void onResponse(Call<com.example.keepsclone.keeps_data> call, Response<com.example.keepsclone.keeps_data> response) {

                        if (!response.isSuccessful()) {
                            Log.d("ResponsePacketFailed", String.valueOf(response.code()));
                            return;
                        }

                        com.example.keepsclone.keeps_data postResponse = response.body();
                        String content = "";
                        content += "code: " + response.code();
                        content += "\nbody: " + postResponse.getBody();
                        content += "\ncolor: " + postResponse.getColor();
                        content += "\ndate_time: " + postResponse.getDate_time();
                        content += "\nimportant: " + postResponse.getImportant();
                        content += "\n\ntitle: " + postResponse.getTitle();

                        Log.d("ResponsePacket", content);
                    }

                    @Override
                    public void onFailure(Call<com.example.keepsclone.keeps_data> call, Throwable t) {
                        Log.d("responseFailed", Objects.requireNonNull(t.getMessage()));
                    }
                });
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        }

        // post request on pressing the submit button
        submitNote.setOnClickListener(v -> {
            if (saveNote()) {
                new SaveNoteTask().execute();
            }
        });

        initMiscellaneous();
    }

    // function to save the note when clicked on floating action button
    private boolean saveNote() {
        if (noteTitle.getText().toString().trim().isEmpty()) {
            Toast.makeText(com.example.keepsclone.createNote.this, "title box can't be empty..", Toast.LENGTH_SHORT).show();
            return false;

        } else {
            Toast.makeText(com.example.keepsclone.createNote.this, "note added...", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    private void initMiscellaneous() {
        final LinearLayout layoutMiscellaneous = findViewById(R.id.layout_miscellaneous);

        final ImageView imageColor1 = layoutMiscellaneous.findViewById(R.id.iamgeColor1);
        final ImageView imageColor2 = layoutMiscellaneous.findViewById(R.id.iamgeColor2);
        final ImageView imageColor3 = layoutMiscellaneous.findViewById(R.id.iamgeColor3);
        final ImageView imageColor4 = layoutMiscellaneous.findViewById(R.id.iamgeColor4);

        layoutMiscellaneous.findViewById(R.id.viewColor1).setOnClickListener(view -> {
            selectedNoteColor = "#22303C";
            imageColor1.setImageResource(R.drawable.ic_done);
            imageColor2.setImageResource(0);
            imageColor3.setImageResource(0);
            imageColor4.setImageResource(0);
        });
        layoutMiscellaneous.findViewById(R.id.viewColor2).setOnClickListener(view -> {
            selectedNoteColor = "#9CC6FF00";
            imageColor1.setImageResource(0);
            imageColor2.setImageResource(R.drawable.ic_done);
            imageColor3.setImageResource(0);
            imageColor4.setImageResource(0);
        });
        layoutMiscellaneous.findViewById(R.id.viewColor3).setOnClickListener(view -> {
            selectedNoteColor = "#9500B0FF";
            imageColor1.setImageResource(0);
            imageColor2.setImageResource(0);
            imageColor3.setImageResource(R.drawable.ic_done);
            imageColor4.setImageResource(0);
        });
        layoutMiscellaneous.findViewById(R.id.viewColor4).setOnClickListener(view -> {
            selectedNoteColor = "#00FF83";
            imageColor1.setImageResource(0);
            imageColor2.setImageResource(0);
            imageColor3.setImageResource(0);
            imageColor4.setImageResource(R.drawable.ic_done);
        });
    }

}