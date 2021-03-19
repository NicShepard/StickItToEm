package edu.neu.madcourseworkupteam.stickittoem;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MessagingService extends FirebaseMessagingService {

    private static final String SERVER_KEY = "AAAAk1Z2RoU:APA91bHx5coormb5Qv_SeCRUTUpcV9Dz1jAt7HY7pV1gMy8kWdpdRfYDNWy1P_mHVOh6jV11iftmrmaFQHFT2amr0eOIC1VYu2kXtTTml5P78c2LNWGB3GZBSYluqZ_f1gZSKRhsKCTt";

    //    https://firebase.google.com/docs/cloud-messaging/android/first-message
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        final String TAG = "FIREBASE";

        // TODO(developer): Handle FCM messages here.
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }


    /**
     * Retrieve the device key for a given username using RTDb
     * @param username the username of the key we are looking for
     * @return the key as a string
     */
    private String getKeyForUsername(String username){
        return "Here's the key!";
    }

    /**
     * Starts thread for private helper function that sends a cloud message to a given username.
     * @param view view to display feedback in.
     * @param sender username of the sender of the message
     * @param receiver username of the receiver of the message
     * @param content content of the message, in this case an emoji
     */
    public void messageUsername(View view, String sender, String receiver, String content) {
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                messageUsernameHelper(view, sender, receiver, content);
            }
        }).start();
    }

    /**
     * Send a message to a given username and provide feedback to user on the success or failure.
     * Works by retrieving device token of user from firebase.
     * Adapted from class code.
     * @param view view to display feedback in.
     * @param sender username of the sender of the message
     * @param receiver username of the receiver of the message
     * @param content content of the message, in this case an emoji
     */
    private void messageUsernameHelper(View view, String sender, String receiver, String content) {

        //Get receiver key
        String receiverDeviceKey = getKeyForUsername(receiver);
        
        //Build json objects
        JSONObject payload = new JSONObject();
        JSONObject notification = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            //Build notification
            notification.put("title", "New Message From " + sender + "!");
            notification.put("body", content);
            notification.put("sound", "default");
            notification.put("badge", "1");

            //Build data
            data.put("title","New Message From " + sender + "!");
            data.put("content",content);

            //Build payload
            payload.put("to", receiverDeviceKey);
            payload.put("priority", "high");
            payload.put("notification", notification);
            payload.put("data",data);

            //Send payload
            URL url = new URL("https://fcm.googleapis.com/fcm/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", SERVER_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(payload.toString().getBytes());
            outputStream.close();

            //Parse Response
            InputStream inputStream = conn.getInputStream();
            final String resp = convertStreamToString(inputStream);

            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Log.e("FIREBASE", "run: " + resp);
                    Toast.makeText(view.getContext() ,resp,Toast.LENGTH_LONG).show();
                }
            });
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Helper function, borrowed from class code
     * @param is
     * @return
     */
    private String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next().replace(",", ",\n") : "";
    }
}
