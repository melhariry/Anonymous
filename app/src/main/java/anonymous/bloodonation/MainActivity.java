package anonymous.bloodonation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.parse.ParseUser;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            Intent home = new Intent(MainActivity.this,Home.class);
            home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(home);
        }
        Button goLogin = (Button)findViewById(R.id.go_login);
        Button goSignUp = (Button) findViewById(R.id.go_signup);
        goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(MainActivity.this,Login.class);
                startActivity(login);
            }
        });
        goSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(MainActivity.this,SignUp.class);
                startActivity(signup);
            }
        });
    }
}
