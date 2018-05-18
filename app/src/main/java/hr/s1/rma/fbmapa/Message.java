package hr.s1.rma.fbmapa;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Message {

    public String id;
    public double longitude;
    public double latitude;

    public Message() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Message(String id, double longitude, double latitude) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("longitude", longitude);
        result.put("latitude", latitude);

        return result;
    }
}