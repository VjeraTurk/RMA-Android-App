package hr.s1.rma.fbmapa;

import android.nfc.Tag;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;

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

import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("location");
    DatabaseReference myRef2 = database.getReference("location");

    static LatLng sydney = new LatLng(45.34306, 14.40917);
    static double lat, lon;
    private GoogleMap mMap;
    private static final String TAG = "*" ;
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
    public void onMapReady(GoogleMap googleMap) {
            // Read from the database
           /*myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    String value = dataSnapshot.getValue(String.class);
                    Log.d(TAG, "Value is: " + value);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });*/

            // Add a marker in Sydney, Australia, RIJEKAAA
            // and move the map's camera to the same location.
            googleMap.addMarker(new MarkerOptions().position(sydney)
                    .title("Marker in Rijeka, Cro")
                    .draggable(true));

            //googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15.0f));

            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener()
            {
                @Override
                public void onMapClick(LatLng poz) {

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
                    //android.util.Log.i("onMapClick", toString().valueOf(lat));
                    //android.util.Log.i("onMapClick", toString().valueOf(lon));

                    Map mWaypoints = new HashMap();
                    mWaypoints.put("latitude", lat);
                    mWaypoints.put("longitude", lon);

                    //String key = myRef.push().getKey();

                    Map mWayPointsMap = new HashMap();
                    mWayPointsMap.put("poza", mWaypoints);
                    myRef.setValue(mWayPointsMap);

                    /*Map mParent = new HashMap();
                    mParent.put("routeID", "my route id");
                    mParent.put("routeName", "my route name");
                    mParent.put("Waypoints", mWayPointsMap);
                    myRef.push().setValue(mParent);*/
                }
            });
        }

}
