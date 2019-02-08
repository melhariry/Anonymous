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
import android.widget.DatePicker;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.Calendar;


public class SetAppointment extends Activity {

    Button sendRequest;
    EditText phoneNum, hospitalName;
    DatePicker appDate;
    ParseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUser = ParseUser.getCurrentUser();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_set_appointment);

        sendRequest = (Button)findViewById(R.id.r_submit);
        phoneNum = (EditText)findViewById(R.id.phoneNum);
        hospitalName = (EditText)findViewById(R.id.hospitalName);
        appDate = (DatePicker)findViewById(R.id.appDate);

        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRequest();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_set_appointment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addRequest() {

        String str_hospName = hospitalName.getText().toString().trim();
        String str_phNum = phoneNum.getText().toString().trim();
        String str_donor = getIntent().getExtras().getString("DONOR_ID");
        String str_needy = currentUser.getObjectId();
        int day = appDate.getDayOfMonth();
        int month = appDate.getMonth();
        int year = appDate.getYear();

        Calendar c = Calendar.getInstance();
        int currDay = c.get(Calendar.DAY_OF_MONTH);
        int currMonth = c.get(Calendar.YEAR);
        int currYear = c.get(Calendar.MONTH);


        if (str_hospName.isEmpty()||str_phNum.isEmpty())
            alertError("Please enter all fields!");

        else if (!isValidMobile(str_phNum))
            alertError("Invalid phone number!");



        else{
            setProgressBarIndeterminateVisibility(true);

            ParseObject bloodRequest = new ParseObject("Request");

            bloodRequest.put("Donor", str_donor);
            bloodRequest.put("Needy",str_needy);
            bloodRequest.put("HospitalName",str_hospName);
            bloodRequest.put("Day",day);
            bloodRequest.put("Month",month);
            bloodRequest.put("Year",year);
            bloodRequest.put("RequestPhone",str_phNum);
            bloodRequest.put("Status","pending");
            bloodRequest.saveInBackground(new SaveCallback() {
                                              @Override
                                              public void done(ParseException e) {
                                                  setProgressBarIndeterminateVisibility(false);
                                                  if(e == null){
                                                      alertError("Request sent successfully");
                                                      Intent home = new Intent(SetAppointment.this, Home.class);
                                                      home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                      home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                      startActivity(home);
                                                  }
                                                  else{
                                                      alertError(e.getMessage());
                                                  }
                                              }
                                          }
            );
        }
    }

    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }


    private void alertError(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(SetAppointment.this);
        builder.setMessage(message)
                .setTitle("Error")
                .setPositiveButton(android.R.string.ok, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
