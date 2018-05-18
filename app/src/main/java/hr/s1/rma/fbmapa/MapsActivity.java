package hr.s1.rma.fbmapa;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("location");

    static LatLng sydney = new LatLng(45.34306, 14.40917);
    static LatLng sydney2 = new LatLng(45.34506, 14.4053);
    static double lat, lon, lat2, lon2 ;
    private GoogleMap mMap;
    private static final String TAG = "*";
    private ArrayList<Message> messageList = new ArrayList<Message>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.pokreni:
                kreni();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void kreni(){
        View parentLayout = findViewById(android.R.id.content);

        myRef.child("poza").addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Message message = dataSnapshot.getValue(Message.class);
                //messageList.add(message);
                Log.e(TAG, "onChildAdded:" + message.latitude);
                Log.e(TAG, "onChildAdded:" + message.longitude);
                lat2=message.latitude;
                lon2=message.longitude;
                sydney2 = new LatLng(lat2,lon2);


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


       /* Snackbar.make(parentLayout, "Hi", Snackbar.LENGTH_LONG)
                .setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                })
                .show();*/
    }
    @Override
    public void onMapReady(final GoogleMap googleMap) {

            // Add a marker in Sydney, Australia, RIJEKAAA
            // and move the map's camera to the same location.
            //sydney2 = new LatLng(lat2,lon2);
            /*googleMap.addMarker(new MarkerOptions().position(sydney)
                    .title("Marker in Rijeka, Cro")
                    .draggable(true));*/
             googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Marker in Rijeka, Cro")
                .draggable(true));

            //googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15.0f));

            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener()
            {
                @Override
                public void onMapClick(LatLng poz) {

                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(sydney2);
                    markerOptions.title("lala");
                    //googleMap.clear();
                    googleMap.animateCamera(CameraUpdateFactory.newLatLng(sydney2));
                    googleMap.addMarker(markerOptions);
            }
            });

            googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDragStart(Marker marker) {
                }
                @Override
                public void onMarkerDrag(Marker marker) {
                }
                @Override
                public void onMarkerDragEnd(Marker marker) {
                    lat = marker.getPosition().latitude;
                    lon = marker.getPosition().longitude;

                    Message message = new Message("gogo", lon, lat);

                    Map<String, Object> messageValues = message.toMap();
                    Map<String, Object> childUpdates = new HashMap<>();

                    childUpdates.put("gogo", messageValues);
                    myRef.updateChildren(childUpdates);
                }
            });
        }

}
