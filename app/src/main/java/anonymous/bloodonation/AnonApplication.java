package anonymous.bloodonation;

import android.app.Application;
import android.content.Intent;

import com.parse.Parse;
import com.parse.ParseUser;

/**
 * Created by Laila Maher on 9/24/2015.
 */
public class AnonApplication extends Application {

    String APPLICATION_ID = "iVrhdYX5yzhQpaaeD496KAbU1TdYNLFGkLZUBo8m";
    String CLIENT_KEY = "jXcpQmDU310ss1fw0WJxIwF6T1k6fJqb47Hjm3vP";

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);
        ParseUser currentUser = ParseUser.getCurrentUser();
    }
}