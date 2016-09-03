package com.androidbuts.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseRecyclerAdapter<String, QuotesHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    private static final String TAG = "MainActivity";

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Offline ON
         */
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);


        // Write a message to the database
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        databaseReference = database.getReference();
        databaseReference.keepSynced(true);

        /**
         * SETUP RecyclerView
         */
        mRecycler = (RecyclerView) findViewById(R.id.recyclerView);
        mRecycler.setHasFixedSize(true);

        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(this);

        mRecycler.setLayoutManager(mManager);

        final Query postsQuery = databaseReference.child("myquotes");

        Log.d(TAG, "onCreate: " + postsQuery);

        mAdapter = new FirebaseRecyclerAdapter<String, QuotesHolder>(String.class, R.layout.textview, QuotesHolder.class, postsQuery) {
            @Override
            protected void populateViewHolder(QuotesHolder viewHolder, String quotes, int position) {

                viewHolder.bindTo(quotes);

            }
        };

        postsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d(TAG, "onDataChange: CALLED");
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mRecycler.setAdapter(mAdapter);
    }

    public void addNewItem(View view) {

        String text = ((EditText) findViewById(R.id.editText)).getText().toString();

        databaseReference.child("myquotes").push().setValue(text);

        ((EditText) findViewById(R.id.editText)).setText("");
    }
}
