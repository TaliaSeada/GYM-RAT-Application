package com.example.gym.workouts;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gym.R;
import com.example.gym.workouts.interfaces.I_exerciseList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExerciseList extends AppCompatActivity implements I_exerciseList {
    // set global variable
    static String nameExe;
    // set toast
    Toast t;
    // set fields for data display
    GridView listView;
    ImageView add;
    private List<String> items = new ArrayList<>();
    private final ArrayList<Map<String, exe_object>> show = new ArrayList<Map<String, exe_object>>();
    SimpleAdapter adap;
    // get firebase instances
    protected FirebaseFirestore db = FirebaseFirestore.getInstance();
    String email_trainee = FirebaseAuth.getInstance().getCurrentUser().getEmail();
    String email_trainer = getTrainee.nameTR;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);
        String role = getIntent().getStringExtra("role");
        String email;
        if(role.equals("trainee")){
            email = email_trainee;
        }
        else {
            email = email_trainer;
        }
        // load content from firebase
        loadContent(email);
        // set the exercises list
        listView = findViewById(R.id.grid_exe);
        items = new ArrayList<>();

        // set add button action
        add = findViewById(R.id.imageMenu);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddNewWorkout.class);
                i.putExtra("role", role);
                startActivity(i);
            }
        });

        // set action for the exercises list
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Intent i = new Intent(ExerciseList.this, exeUpdate.class);
                i.putExtra("role", role);
                startActivity(i);
                nameExe = items.get(pos);
            }
        });
    }

    /***
     * this function load the relevant content from the firebase
     * to the lists we created in order to show it in the app screen.
     ***/
    public void loadContent(String email) {
        db.collection("user-info").document(email)
                .collection("workouts").document(WorkoutList.nameTR)
                .collection("exercises")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        items.clear();
                        show.clear();
                        for(DocumentSnapshot snapshot : documentSnapshots){
                            final Map<String, exe_object> ex = new HashMap<>();
                            items.add(snapshot.getString("name"));
                            String name = snapshot.getString("name");
                            Long reps = snapshot.getLong("reps");
                            Long sets = snapshot.getLong("sets");
                            double weight = snapshot.getDouble("weight");
                            exe_object eo = new exe_object(name, reps, sets, weight);
                            ex.put("exercise", eo);
                            show.add(ex);
                        }
                        String[] from = {"exercise"};
                        int[] to = {R.id.exe};
                        adap = new SimpleAdapter(ExerciseList.this, show, R.layout.grid_layout, from, to);
                        listView.setAdapter(adap);
                    }
                });
    }

    /***
     * this function raises a massage to the screen
     * @param s the massage we want to write on the screen
     */
    public void makeToast(String s) {
        if (t != null) t.cancel();
        t = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT);
        t.show();
    }


}