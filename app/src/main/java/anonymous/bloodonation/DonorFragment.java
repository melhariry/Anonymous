package anonymous.bloodonation;


import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class DonorFragment extends ListFragment {

    ArrayList<Donor> donors;
    CustomAdapter adapter;

    public DonorFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        donors = new ArrayList<Donor>();
        adapter = new CustomAdapter(getActivity(), donors);
        setListAdapter(adapter);
        loadDonors();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.view_donors_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.refresh_list:
                loadDonors();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadDonors(){

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("isDonor",true);
        getActivity().setProgressBarIndeterminateVisibility(true);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> donorsList, ParseException e) {
                getActivity().setProgressBarIndeterminateVisibility(false);
                if(e == null){
                    donors.clear();
                    for (ParseUser donor : donorsList) {
                        if(donor.getObjectId().equals(ParseUser.getCurrentUser().getObjectId()))
                            continue;

                        donors.add(new Donor(donor.get("FullName").toString(), donor.get("BloodType").toString(),
                                donor.get("cityName").toString(), donor.getObjectId()));
                    }
                    adapter.notifyDataSetChanged();
                }
                else{
                    Log.d("Load Donors",e.getMessage());
                }

            }
        });

    }
}
