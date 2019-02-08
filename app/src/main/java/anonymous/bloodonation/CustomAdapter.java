package anonymous.bloodonation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Laila Maher on 9/26/2015.
 */
public class CustomAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<Donor> donors;

    public class Holder
    {
        TextView donorName, donorBloodType, donorResidence;
        ImageButton sendRequest;
    }
    public CustomAdapter(Context context, ArrayList<Donor> donors){
        super(context, R.layout.donor_row, donors);
        this.context = context;
        this.donors = donors;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = convertView;

        if (rowView == null) {
            rowView = inflater.inflate(R.layout.donor_row, parent, false);
            Holder holder = new Holder();
            holder.donorName = (TextView) rowView.findViewById(R.id.donor_name);
            holder.donorBloodType = (TextView) rowView.findViewById(R.id.donor_bloodtype);
            holder.donorResidence = (TextView) rowView.findViewById(R.id.donor_residence);
            holder.sendRequest = (ImageButton) rowView.findViewById(R.id.send_request);
            holder.sendRequest.setTag(position);
            rowView.setTag(holder);
        }

        Holder holder = (Holder) rowView.getTag();

        holder.donorName.setText(donors.get(position).name);
        holder.donorBloodType.setText(donors.get(position).bloodType);
        holder.donorResidence.setText(donors.get(position).residence);

        holder.sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // send request
                String tag = v.getTag().toString();
                if (tag != null) {
                    int position = Integer.parseInt(tag);

                    Intent msg = new Intent(context, SetAppointment.class);
                    msg.putExtra("DONOR_ID",donors.get(position).objectId);
                    context.startActivity(msg);
                }
            }
        });

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // view profile
            }
        });
        return rowView;
    }

}
