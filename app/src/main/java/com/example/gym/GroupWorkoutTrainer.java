package com.example.gym;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class GroupWorkoutTrainer extends AppCompatActivity {
    // set toast
    Toast t;
    // set fields for data display
    EditText input;
    ImageView add;
    static GridView listView;
    static ListViewGroupTrainer adapter;
    static String nameTR;
    private static final ArrayList<String> items = new ArrayList<>();
    // get relevant trainee email
    String email = Objects.requireNonNull(AddWorkoutTrainer.nameTR);
    // get firebase instance
    protected static FirebaseFirestore db = FirebaseFirestore.getInstance();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_workout);
        // set list adapter
        adapter = new ListViewGroupTrainer(GroupWorkoutTrainer.this, items);
        listView = findViewById(R.id.grid_workout);
        // get text
        input = findViewById(R.id.Input);

        // load content from firebase
        loadContent();

        // set action for the workouts list
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Intent i = new Intent(GroupWorkoutTrainer.this, newScreenTrainer.class);
                startActivity(i);
                nameTR = items.get(pos);
            }
        });

        // set add button action
        add = findViewById(R.id.imageMenu);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String text = input.getText().toString();
                    AddNewWorkoutTrainee.addWO(email, text);
                    input.setText("");
                    makeToast(text + " Added Successfully");
                    // reload content to show the new workout
                    loadContent();
                } catch (Exception e) {
                    makeToast("Type Workout Name");
                    e.printStackTrace();
                }
            }
        });
    }

    /***
     * this function load the relevant content from the firebase
     * to the list we created in order to show it in the app screen.
     ***/
    public void loadContent() {
        db.collection("user-info").document(email)
                .collection("workouts")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        items.clear();
                        assert documentSnapshots != null;
                        for (DocumentSnapshot snapshot : documentSnapshots) {
                            items.add(snapshot.getString("name"));
                        }
                        adapter.notifyDataSetChanged();
                        listView.setAdapter(adapter);
                    }
                });
    }

    /***
     * this function removes an item from the display list and updates the firebase
     * @param remove the index of the item we clicked to remove
     */
    public static void removeItem(int remove) {
        try {
            String rem = Objects.requireNonNull(AddWorkoutTrainer.nameTR);
            db.collection("user-info").document(Objects.requireNonNull(rem))
                    .collection("workouts").document(items.get(remove)).delete();
            listView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * this function raises a massage to the screen
     * @param s the massage we want to write on the screen
     */
    private void makeToast(String s) {
        if (t != null) t.cancel();
        t = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT);
        t.show();
    }

}