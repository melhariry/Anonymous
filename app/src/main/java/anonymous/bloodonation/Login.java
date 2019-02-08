package anonymous.bloodonation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class Login extends Activity {

    Button login;
    EditText email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // loading in action bar
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_login);
        login = (Button)findViewById(R.id.login);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check credentials in the database
                checkCredentials();
            }

        });
    }

    private void checkCredentials() {

        String str_email = email.getText().toString().trim();
        String str_password = password.getText().toString().trim();

        if(str_email.isEmpty()||str_password.isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
            builder.setMessage("Enter your e-mail and password!")
                    .setTitle("Error")
                    .setPositiveButton(android.R.string.ok, null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else {
            // loading
            setProgressBarIndeterminateVisibility(true);
            ParseUser.logInInBackground(str_email, str_password, new LogInCallback() {
                @Override
                public void done(ParseUser parseUser, ParseException e) {
                    setProgressBarIndeterminateVisibility(false);
                    if(e == null){
                        Intent home = new Intent(Login.this, Home.class);
                        home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(home);
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                        builder.setMessage(e.getMessage())
                                .setTitle("Error")
                                .setPositiveButton(android.R.string.ok, null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            });
        }
    }
}
