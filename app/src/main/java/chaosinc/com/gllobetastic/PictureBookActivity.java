package chaosinc.com.gllobetastic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import chaosinc.com.gllobetastic.utility.DatabaseConnection;
import chaosinc.com.gllobetastic.utility.Picture;
import chaosinc.com.gllobetastic.utility.PictureListItemAdapter;

public class PictureBookActivity extends AppCompatActivity {

    private RecyclerView pictureListView;
    private PictureListItemAdapter mAdapter;
    private List<Picture> mPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_book);
        pictureListView = findViewById(R.id.pictureList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        pictureListView.setLayoutManager(mLayoutManager);
        pictureListView.setHasFixedSize(true);
        getPictures();
    }

    /**
     * gets picture from database
     */
    public void getPictures() {

        DatabaseReference mPictureDatabase = FirebaseDatabase.getInstance().getReference().child("pictures").child("pictures");
        mPictureDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mPicture = new ArrayList<>();

                Map<String, Object> pictures = (Map<String, Object>) dataSnapshot.getValue();
                for (Map.Entry<String, Object> entry : pictures.entrySet()) {
                    Map pictureRow = (Map) entry.getValue();

                    Picture picture = new Picture((String) pictureRow.get("name"), (String) pictureRow.get("pictureBitmap"), (String) pictureRow.get("city"));
                    mPicture.add(picture);
                    DatabaseConnection.getInstance().setArrayList(mPicture);
                }
                mAdapter = new PictureListItemAdapter(getApplicationContext(), mPicture);
                pictureListView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

