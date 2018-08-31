package tbs.thinkbiz.solutions.iloveurhome;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import tbs.thinkbiz.solutions.iloveurhome.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BuyersActivity extends AppCompatActivity {
    private Spinner spiner ,spiner1;
    EditText editTextfnm,editTextlnm,editTextmail,editTextphone,editTextstreet,editTextapt,editTextcity,editTextzip;
    EditText fnameag,lnameag,cmpnyag;
    EditText fnameowner,lnameowner;
    EditText street1,street2ad,cityhm,zipcode1;
    ProgressDialog progressDialog;
    private static String REG_URL="http://demotbs.com/dev/love/mobileapp/buyer.php";
    int success;
    int error;
    String msg;

    RadioGroup radioGroup1,radioGroup2;
    RadioButton radioButton1,radioButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyers);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.buyersbar);
        View view =getSupportActionBar().getCustomView();

        ImageButton imageButton= (ImageButton)view.findViewById(R.id.action_bar_back);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        spiner = (Spinner) findViewById(R.id.spinner);
        String[] users = new String[]{
                "Please Select State",
                "Alaska","Alabama","Arkansas","American Samoa","Arizona",
                "California","Colorado","Connecticut","District of Columbia","Delaware",
                "Florida","Georgia","Guam","Hawaii","Iowa","Idaho","Illinois","Indiana","Kansas",
                "Kentucky","Louisiana","Massachusetts","Maryland","Maine","Michigan","Minnesota",
                "Missouri","Mississippi","Montana","North Carolina","North Dakota","Nebraska",
                "New Hampshire","New Jersey","New Mexico","Nevada","New York","Ohio","Oklahoma",
                "Oregon","Pennsylvania","Puerto Rico","Rhode Island","South Carolina","South Dakota",
                "Tennessee","Texas","Utah","Virginia","Virgin Islands","Vermont","Washington",
                "Wisconsin","West Virginia","Wyoming"
        };

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.spinneritems, users
        ){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinneritems);
        spiner.setAdapter(spinnerArrayAdapter);



        spiner1 = (Spinner) findViewById(R.id.spinner1);
        String[] users1 = new String[]{
                "Please Select State",
                "Alaska","Alabama","Arkansas","American Samoa","Arizona",
                "California","Colorado","Connecticut","District of Columbia","Delaware",
                "Florida","Georgia","Guam","Hawaii","Iowa","Idaho","Illinois","Indiana","Kansas",
                "Kentucky","Louisiana","Massachusetts","Maryland","Maine","Michigan","Minnesota",
                "Missouri","Mississippi","Montana","North Carolina","North Dakota","Nebraska",
                "New Hampshire","New Jersey","New Mexico","Nevada","New York","Ohio","Oklahoma",
                "Oregon","Pennsylvania","Puerto Rico","Rhode Island","South Carolina","South Dakota",
                "Tennessee","Texas","Utah","Virginia","Virgin Islands","Vermont","Washington",
                "Wisconsin","West Virginia","Wyoming"
        };

        ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(
                this, R.layout.spinneritems, users1
        ){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter1.setDropDownViewResource(R.layout.spinneritems);
        spiner1.setAdapter(spinnerArrayAdapter1);

//        ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(
//                this, R.layout.spinneritems, users1
//        );
//        spinnerArrayAdapter1.setDropDownViewResource(R.layout.spinneritems);
//        spiner1.setAdapter(spinnerArrayAdapter1);


        final Button button=(Button)findViewById(R.id.buttonsbmt);

        editTextfnm=(EditText)findViewById(R.id.fname);
        editTextlnm=(EditText)findViewById(R.id.lname);
        editTextmail=(EditText)findViewById(R.id.editTextemail);
        editTextphone=(EditText)findViewById(R.id.phone);
        editTextstreet=(EditText)findViewById(R.id.street2);
        editTextapt=(EditText)findViewById(R.id.aptnmbr);
        editTextcity=(EditText)findViewById(R.id.city);
        editTextzip=(EditText)findViewById(R.id.zipcode);

        radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
        radioButton1 = (RadioButton) findViewById(R.id.rb1);
        radioGroup2 = (RadioGroup) findViewById(R.id.radioGroup2);
        radioButton2 = (RadioButton) findViewById(R.id.rb2);

        fnameag=(EditText)findViewById(R.id.fnameag);
        lnameag=(EditText)findViewById(R.id.lnameag);
        cmpnyag=(EditText)findViewById(R.id.compnyag);


        fnameowner=(EditText)findViewById(R.id.fnameownr);
        lnameowner=(EditText)findViewById(R.id.lnameownr);


        street1=(EditText)findViewById(R.id.street1);
        street2ad=(EditText)findViewById(R.id.street2ad);
        cityhm=(EditText)findViewById(R.id.cityhm);
        zipcode1=(EditText)findViewById(R.id.zipcode1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mail = editTextmail.getText().toString().trim();

                if (editTextfnm.getText().toString().length() == 0) {
                    editTextfnm.setError("First name not entered");
                    editTextfnm.requestFocus();
                }
                if (editTextlnm.getText().toString().length() == 0) {
                    editTextlnm.setError("Last name not entered");
                    editTextlnm.requestFocus();
                }


                if (TextUtils.isEmpty(mail)) {
                    editTextmail.setError("Please enter your email");
                    editTextmail.requestFocus();
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                    editTextmail.setError("Enter a valid email");
                    editTextmail.requestFocus();
                }
                if (editTextphone.getText().toString().length() == 0) {
                    editTextphone.setError("Phone number not entered");
                    editTextphone.requestFocus();
                }
                if (editTextstreet.getText().toString().length() == 0) {
                    editTextstreet.setError("Street not entered");
                    editTextstreet.requestFocus();
                }
                if (editTextapt.getText().toString().length() == 0) {
                    editTextapt.setError("Apt number not entered");
                    editTextapt.requestFocus();
                }
                if (editTextcity.getText().toString().length() == 0) {
                    editTextcity.setError("City not entered");
                    editTextcity.requestFocus();
                }
                if (editTextzip.getText().toString().length() == 0) {
                    editTextzip.setError("Zipcode not entered");
                    editTextzip.requestFocus();
                }
                if (radioGroup1.getCheckedRadioButtonId()==-1) {
                    radioButton1.setError("Please select one option");
                    radioGroup1.requestFocus();

                }
                if (radioGroup2.getCheckedRadioButtonId()==-1) {
                    radioButton2.setError("Please select one option");
                    radioGroup2.requestFocus();

                }
                if (fnameag.getText().toString().length() == 0) {
                    fnameag.setError("Agent first name not entered");
                    fnameag.requestFocus();
                }
                if (lnameag.getText().toString().length() == 0) {
                    lnameag.setError("Agent last name not entered");
                    lnameag.requestFocus();
                }
                if (cmpnyag.getText().toString().length() == 0) {
                    cmpnyag.setError("Real state name not entered");
                    cmpnyag.requestFocus();
                }
                if (fnameowner.getText().toString().length() == 0) {
                    fnameowner.setError("Owner first name not entered");
                    fnameowner.requestFocus();
                }
                if (lnameowner.getText().toString().length() == 0) {
                    lnameowner.setError("Owner last name not entered");
                    lnameowner.requestFocus();
                }
                if (street1.getText().toString().length() == 0) {
                    street1.setError("Street1 not entered");
                    street1.requestFocus();
                }
                if (street2ad.getText().toString().length() == 0) {
                    street2ad.setError("Street2 not entered");
                    street2ad.requestFocus();
                }
                if (cityhm.getText().toString().length() == 0) {
                    cityhm.setError("City not entered");
                    cityhm.requestFocus();
                }
                if (zipcode1.getText().toString().length() == 0) {
                    zipcode1.setError("Zipcode not entered");
                    zipcode1.requestFocus();
                }

                else {
                    SubmitData();
                }
            }
        });


    }
    private void SubmitData(){
        progressDialog = new ProgressDialog(BuyersActivity.this);
        progressDialog.setMessage("Information Sending...");
        progressDialog.show();

        final String first_name = editTextfnm.getText().toString().trim();
        final String last_name = editTextlnm.getText().toString().trim();
        final String email = editTextmail.getText().toString().trim();
        final String phone_no = editTextphone.getText().toString().trim();
        final String street = editTextstreet.getText().toString().trim();
        final String aptnumber = editTextapt.getText().toString().trim();
        final String city = editTextcity.getText().toString().trim();
        final String states = spiner.getSelectedItem().toString();

        final String zipcode = editTextzip.getText().toString().trim();

        final String homesale = ((RadioButton) findViewById(radioGroup1.getCheckedRadioButtonId())).getText().toString();
        final String realstate = ((RadioButton) findViewById(radioGroup2.getCheckedRadioButtonId())).getText().toString();

       // Log.e("homesl",homesale);
       // Log.e("homes2",realstate);

        final String fnameage = fnameag.getText().toString().trim();
        final String lnameage = lnameag.getText().toString().trim();
        final String compnyage = cmpnyag.getText().toString().trim();


        final String fnameownwer = fnameowner.getText().toString().trim();
        final String lnameownere = lnameowner.getText().toString().trim();


        final String streetad1 = street1.getText().toString().trim();
        final String streetad2 = street2ad.getText().toString().trim();
        final String cityhome = cityhm.getText().toString().trim();
        final String states1 = spiner1.getSelectedItem().toString();
        final String zipcode11 = zipcode1.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,REG_URL ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("resp",response);
                        progressDialog.dismiss();

                        try {
                            JSONObject obj = new JSONObject(response);
                            success= Integer.parseInt(obj.getString("s"));
                            error= Integer.parseInt(obj.getString("e"));
                            msg=obj.getString("m");

                            if (success==1||success==0)
                            {
                                Toast.makeText(BuyersActivity.this, msg, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                editTextfnm.setText("");
                                editTextlnm.setText("");
                                editTextmail.setText("");
                                editTextphone.setText("");
                                editTextstreet.setText("");
                                editTextapt.setText("");
                                editTextcity.setText("");
                                editTextzip.setText("");
                                radioGroup1.clearCheck();
                                radioGroup2.clearCheck();
                                fnameag.setText("");
                                lnameag.setText("");
                                cmpnyag.setText("");
                                fnameowner.setText("");
                                lnameowner.setText("");
                                street1.setText("");
                                street2ad.setText("");
                                cityhm.setText("");
                                zipcode1.setText("");


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(BuyersActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BuyersActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }) {
            @Override

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("first_name", first_name);
                params.put("last_name", last_name);
                params.put("email", email);
                params.put("phone_no", phone_no);
                params.put("street", street);
                params.put("apt_number", aptnumber);
                params.put("city", city);
                params.put("state", states);
                params.put("zipcode", zipcode);
                params.put("Home_sell", homesale);
                params.put("agent", realstate);
                params.put("agent_first_name", fnameage);
                params.put("agent_last_name", lnameage);
                params.put("real_state_agent_name", compnyage);
                params.put("owner_first_name", fnameownwer);
                params.put("owner_last_name", lnameownere);
                params.put("owner_street1", streetad1);
                params.put("owner_street2", streetad2);
                params.put("owner_city", cityhome);
                params.put("owner_state", states1);
                params.put("owner_zipcode", zipcode11);


                return params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}



