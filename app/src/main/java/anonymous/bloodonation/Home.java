package anonymous.bloodonation;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.widget.DrawerLayout;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.ParseUser;

public class Home extends Activity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String[] listOptionsElements = {"View Donors","Profile"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_home);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView)findViewById(R.id.navigation_drawer_list);

        ArrayAdapter<String> listOptions = new ArrayAdapter<String>(this,R.layout.drawer_item,listOptionsElements);
        mDrawerList.setAdapter(listOptions);
        mDrawerList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });



        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);


        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.navigation_drawer_open,R.string.navigation_drawer_close){
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    private void selectItem(int position){

        // update the main content by replacing fragments
        switch (position){
            case 0:
                getFragmentManager().beginTransaction().replace(R.id.container, new DonorFragment()).commit();
                break;
            case 1:
                getFragmentManager().beginTransaction().replace(R.id.container, new ProfileFragment()).commit();
                break;
        }

        mDrawerList.setItemChecked(position, true);
        getActionBar().setTitle(listOptionsElements[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item))return true;

        int id = item.getItemId();
        switch (id){
            case R.id.action_settings:
                return true;
            case R.id.action_about_us:
                return true;
            case R.id.action_notif_on:
                return true;
            case R.id.action_logout:
                ParseUser.logOut();
                Intent main = new Intent(Home.this,MainActivity.class);
                main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(main);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

}
