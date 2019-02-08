package anonymous.bloodonation;



import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;


import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class ProfileFragment extends Fragment {

    ParseUser currentUser;
    EditText name, email;
    Spinner bloodTypes, cities;
    Button submit;
    CheckBox isDonor;
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        currentUser = ParseUser.getCurrentUser();
        View fragmentView = inflater.inflate(R.layout.fragment_profile, container, false);
        submit = (Button)fragmentView.findViewById(R.id.p_submit);
        name = (EditText)fragmentView.findViewById(R.id.profile_name);
        email = (EditText)fragmentView.findViewById(R.id.profile_email);
        isDonor = (CheckBox)fragmentView.findViewById(R.id.p_isDonor);
        name.setText(currentUser.get("FullName").toString());
        email.setText(currentUser.getEmail().toString());
        bloodTypes = (Spinner)fragmentView.findViewById(R.id.p_bloodTypes);
        cities = (Spinner)fragmentView.findViewById(R.id.p_Cities);
        isDonor.setChecked((boolean)currentUser.get("isDonor"));

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile();
            }
        });

        populateCities();
        populateBloodTypes();

        return fragmentView;
    }




    private void alertError(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message)
                .setTitle("Error")
                .setPositiveButton(android.R.string.ok, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void populateCities(){
        // Creating adapter for spinner
        String city = currentUser.get("cityName").toString();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.cities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cities.setAdapter(adapter);

        int spinnerPosition = adapter.getPosition(city);
        cities.setSelection(spinnerPosition);
    }

    private void populateBloodTypes() {
        String bloodType = currentUser.get("BloodType").toString();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.blood_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodTypes.setAdapter(adapter);

        int spinnerPosition = adapter.getPosition(bloodType);
        bloodTypes.setSelection(spinnerPosition);
    }

    private void editProfile() {

        String str_name = name.getText().toString().trim();
        String str_email = email.getText().toString().trim();
        String str_bloodtype = bloodTypes.getSelectedItem().toString();
        String str_city = cities.getSelectedItem().toString();

        if(str_email.isEmpty()||str_name.isEmpty()){
            alertError("Please enter all fields!");
        }
        else{
            getActivity().setProgressBarIndeterminateVisibility(true);
            currentUser.put("username", str_email);
            currentUser.put("email", str_email);
            currentUser.put("FullName", str_name);
            currentUser.put("BloodType", str_bloodtype);
            currentUser.put("cityName",str_city);
            currentUser.put("isDonor", isDonor.isChecked());

            currentUser.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    getActivity().setProgressBarIndeterminateVisibility(false);
                    if(e == null){
                        Intent home = new Intent(getActivity(), Home.class);
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