package anonymous.bloodonation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.ArrayList;
import java.util.List;

public class SignUp extends Activity {

    Button signUp;
    EditText name,email,password, confirmPassword;
    CheckBox isDonor;
    Spinner bloodTypes, cities;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_sign_up);

        signUp = (Button)findViewById(R.id.signup);
        name = (EditText)findViewById(R.id.fullname);
        email = (EditText)findViewById(R.id.new_email);
        password = (EditText)findViewById(R.id.new_password);
        confirmPassword = (EditText)findViewById(R.id.confirm_password);
        isDonor = (CheckBox)findViewById(R.id.new_isDonor);
        bloodTypes = (Spinner)findViewById(R.id.new_bloodTypes);
        cities = (Spinner)findViewById(R.id.new_Address);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToDatabase();
            }
        });
        populateCities();
        populateBloodTypes();

    }

    private void alertError(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
        builder.setMessage(message)
                .setTitle("Error")
                .setPositiveButton(android.R.string.ok, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void populateCities(){
        // Creating adapter for spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.cities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cities.setAdapter(adapter);
    }

    private void populateBloodTypes() {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.blood_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodTypes.setAdapter(adapter);
    }



    private void addToDatabase() {

        String str_name = name.getText().toString().trim();
        String str_email = email.getText().toString().trim();
        String str_password = password.getText().toString().trim();
        String str_confirmPassword = confirmPassword.getText().toString().trim();
        String str_bloodtype = bloodTypes.getSelectedItem().toString();
        String str_city = cities.getSelectedItem().toString();

        if(str_email.isEmpty()||str_name.isEmpty()||str_password.isEmpty()){
            alertError("Please enter all fields!");
        }
        else if (!str_confirmPassword.equals(str_password)) {
            alertError("Passwords do not match!");
        }
        else {
            setProgressBarIndeterminateVisibility(true);
            ParseUser newUser = new ParseUser();

            newUser.setEmail(str_email);
            newUser.setUsername(str_email);
            newUser.setPassword(str_password);
            newUser.put("FullName", str_name);
            newUser.put("BloodType",str_bloodtype);
            newUser.put("isDonor",isDonor.isChecked());
            newUser.put("cityName", str_city);
            newUser.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    setProgressBarIndeterminateVisibility(false);
                    if(e == null){
                        Intent home = new Intent(SignUp.this, Home.class);
                        home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(home);
                    }
                    else{
                        alertError(e.getMessage());
                    }
                }
            });
        }
    }
}
