package com.nsl.beejtantra.advancebooking;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.nsl.beejtantra.Constants;
import com.nsl.beejtantra.DatabaseHandler;
import com.nsl.beejtantra.R;
import com.nsl.beejtantra.SchemesActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.nsl.beejtantra.orderindent.FragmentOrderIndent.REQUEST_TYPE;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAdvanceboking extends Fragment {
    // JSON parser class
    private JSONObject JSONObj;
    private JSONArray JSONArr=null;
    ProgressDialog progressDialog;
    DatabaseHandler db;

    private ListView listView;
    private ItemfavitemAdapter adapter;

    String jsonData;
    String userId;
    String checkdivisions;
    int role;
    String customerscount;
    String team;
    ArrayList<HashMap<String, String>> favouriteItem = new ArrayList<HashMap<String, String>>();
    String[] Name = {"View all Advance Bookings","New Advance Booking"};//
    String[] Names = {"View My Advance Bookings","My Team Advance Bookings","New Advance Booking"};//
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view          = inflater.inflate(R.layout.fragment_advancebooking, container, false);
        sharedpreferences  =  getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        userId             = sharedpreferences.getString("userId", "");
        team               = sharedpreferences.getString("team", "");
        role      = sharedpreferences.getInt(Constants.SharedPrefrancesKey.ROLE, 0);
        customerscount     = sharedpreferences.getString("customerscount", "");
        db                 = new DatabaseHandler(getActivity());
        checkdivisions     = sharedpreferences.getString("SOSYNC", "");
        final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinatorLayout);
        listView           = (ListView) view.findViewById(R.id.listView);

        RelativeLayout rl  = (RelativeLayout)view.findViewById(R.id.rl_schemes);
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent schemes = new Intent(getActivity(),SchemesActivity.class);
                startActivity(schemes);
            }
        });
        Button btn_schemes = (Button)view.findViewById(R.id.btn_schemes);
        btn_schemes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent schemes = new Intent(getActivity(),SchemesActivity.class);
                startActivity(schemes);
            }
        });

        if(role==Constants.Roles.ROLE_7 || role==Constants.Roles.ROLE_12 ){

            // Each row in the list stores country name, currency and flag
            List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

            for(int i=0;i<2;i++){
                HashMap<String, String> hm = new HashMap<String,String>();
                hm.put("txt",  Name[i]);
                aList.add(hm);
            }

            // Keys used in Hashmap
            String[] from = { "txt" };

            // Ids of views in listview_layout
            int[] to = { R.id.tv_type};

            // Instantiating an adapter to store each items
            // R.layout.listview_layout defines the layout of each item
            SimpleAdapter adapter = new SimpleAdapter(getActivity(), aList, R.layout.row_advancebooking, from, to);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    view.setSelected(true);
                    if (i==1){
                        Intent newbooking = new Intent(getActivity(),NewAdvancebokingChooseActivity.class);
                        newbooking.putExtra("selection","adv");
                        startActivity(newbooking);
                    }
                    else{

                        Intent viewbooking = new Intent(getActivity(),ViewBookings1Activity.class);
                        viewbooking.putExtra(REQUEST_TYPE,2);
                        startActivity(viewbooking);
                    }
                }
            });

        }
        else{

            // Each row in the list stores country name, currency and flag
            List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

            for(int i=0;i<3;i++){
                HashMap<String, String> hm = new HashMap<String,String>();
                hm.put("txt",  Names[i]);
                aList.add(hm);
            }

            // Keys used in Hashmap
            String[] from = { "txt" };

            // Ids of views in listview_layout
            int[] to = { R.id.tv_type};

            // Instantiating an adapter to store each items
            // R.layout.listview_layout defines the layout of each item
            SimpleAdapter adapter = new SimpleAdapter(getActivity(), aList, R.layout.row_advancebooking, from, to);
            listView.setAdapter(adapter);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    view.setSelected(true);

                    if (i==0){

                        Intent viewbooking = new Intent(getActivity(),ViewBookings1Activity.class);
                        viewbooking.putExtra(REQUEST_TYPE,2);
                        startActivity(viewbooking);
                       // Toast.makeText(getActivity(), ""+i, Toast.LENGTH_SHORT).show();

                    }
                    else if (i==1){
                        Intent newbooking = new Intent(getActivity(),ViewMOTeamBookingsActivity.class);
                        newbooking.putExtra(REQUEST_TYPE,2);
                        startActivity(newbooking);
                       // Toast.makeText(getActivity(), ""+i, Toast.LENGTH_SHORT).show();
                    }

                    else if (i==2) {

                        if (customerscount.equalsIgnoreCase("1")) {
                            Intent viewbooking = new Intent(getActivity(), NewAdvancebokingChooseActivity.class);
                            viewbooking.putExtra(REQUEST_TYPE, 2);
                            startActivity(viewbooking);
                        }
                        else{
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                            alertDialogBuilder.setMessage("Sorry ! You Don't have customers to do this operation")
                                    .setCancelable(false)
                                    .setPositiveButton("Ok",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {

                                                    dialog.cancel();
                                                }
                                            });

                            AlertDialog alert = alertDialogBuilder.create();
                            alert.show();
                        }

                        // Toast.makeText(getActivity(), ""+i, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }





        return view;
    }

    class ItemfavitemAdapter extends BaseAdapter {

        Context context;
        ArrayList<HashMap<String,String>> results = new ArrayList<HashMap<String,String>>();


        public ItemfavitemAdapter(Context context,ArrayList<HashMap<String, String>> results) {
            this.context = context;
            this.results = results;

        }
        @Override
        public int getCount() {
            return results.size();
        }

        @Override
        public Object getItem(int position) {
            return results;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = new ViewHolder();
            if(convertView == null)
            {
                sharedpreferences = context.getSharedPreferences(mypreference, Context.MODE_PRIVATE);

                LayoutInflater inflater     = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView                 = inflater.inflate(R.layout.row_advancebooking, parent, false);
                holder.itemname             = (TextView)convertView.findViewById(R.id.tv_company);
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder)convertView.getTag();
            }
           //Glide.with(context).load(results.get(position).get("image")).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.pic);
            holder.itemname.setText(results.get(position).get("name"+"-"+"company_code"));

            return convertView;
        }


        public class ViewHolder
        {
            public TextView  itemname;

        }
        public void updateResults(ArrayList<HashMap<String, String>> results) {

            this.results = results;
            notifyDataSetChanged();
        }
    }




}
