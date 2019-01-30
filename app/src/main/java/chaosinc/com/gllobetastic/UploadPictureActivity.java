package chaosinc.com.gllobetastic;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import chaosinc.com.gllobetastic.utility.BitmapHelper;
import chaosinc.com.gllobetastic.utility.Picture;

public class UploadPictureActivity extends AppCompatActivity {

    public ImageView takenPicture;
    public Button uploadImageButton;
    private Bitmap bm;
    private Button cancelButton;
    private TextView locationTextView;
    private String cityName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_picture);

        takenPicture = findViewById(R.id.taken_picture_img_view);
        uploadImageButton = findViewById(R.id.upload_img_btn);
        cancelButton = findViewById(R.id.cancel_btn);
        locationTextView = findViewById(R.id.locatoin_txtView);

        try {
            getLocation();
        } catch (IOException e) {
            e.printStackTrace();
        }

        locationTextView.setText("Your picture is taken in " + cityName);
        Intent i = getIntent();
        bm = (Bitmap) i.getExtras().get("picture");

        takenPicture.setImageBitmap(bm);
        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("pictures");
                final String pictureID = UUID.randomUUID().toString();

                Picture picture = new Picture(pictureID, BitmapHelper.convert(bm), cityName);
                mDatabase.child("pictures").child(pictureID).setValue(picture);
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);

            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "You canceled, the picture is gone",
                        Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }

    /**
     * @throws IOException Gets location with location manager (sensor in phone)
     *                     determines city with longitude and latitude
     */
    public void getLocation() throws IOException {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        android.location.Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
        String city = addresses.get(0).getLocality();
        cityName = city;
    }

}
