package com.example.art.myapplication2;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class DisplayMemberActivity extends AppCompatActivity {
    public final static String EXTRA_ID = "com.example.art.myapplication2.ID";

//Init
    TabHost tabHost;
    String getidnum, getLname, getFname, getMname, getEname, getBday;
    String getPin,    getLastName,    getFirstName,    getMiddleName,    getSex,    getDOB,    getPass;
    TextView     Pin,    LastName,    FirstName,    MiddleName,    Sex,    DOB,    Suffix;
    SoapPrimitive resultString;
    String string;
    SoapObject response;
    String TAG = "Response";
    int count;
    String[] array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_member);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        extractIntent();    //get data sent from Main Activity
        getTextViewId();    //get all objects of textviews
        //displayInfo();
        //send data to ksoap and retrieve data
        AsyncCallWS task = new AsyncCallWS();   //for multi threading needed when calling ksoap
        task.execute();     //start connection through webservice
        //queryMember();
    }



    private void extractIntent(){
        Intent intent = getIntent();
        getidnum = intent.getStringExtra(MainActivity.EXTRA_ID);
        getPass = intent.getStringExtra(MainActivity.EXTRA_PASS);
        //getLname = intent.getStringExtra(MainActivity.EXTRA_LNAME);
        //getFname = intent.getStringExtra(MainActivity.EXTRA_FNAME);
        //getMname = intent.getStringExtra(MainActivity.EXTRA_MNAME);
        //getEname = intent.getStringExtra(MainActivity.EXTRA_ENAME);
        //getBday = intent.getStringExtra(MainActivity.EXTRA_BDAY);
        //getSex = intent.getStringExtra(MainActivity.EXTRA_SEX);
    }

    private void getTextViewId(){
        Pin = (TextView) findViewById(R.id.textView);
        LastName = (TextView) findViewById(R.id.textView2);
        FirstName = (TextView) findViewById(R.id.textView3);
        MiddleName = (TextView) findViewById(R.id.textView4);
        Sex = (TextView) findViewById(R.id.textView5);
        DOB = (TextView) findViewById(R.id.textView6);
        //Suffix = (TextView) findViewById(R.id.textView7);
    }

    private void displayInfo(){
        Pin.setText(getPin);
        LastName.setText(getLastName);
        FirstName.setText(getFirstName);
        MiddleName.setText(getMiddleName);
        Sex.setText(getSex);
        DOB.setText(getDOB);
        //Suffix.setText(getSuffix);
    }

    private class AsyncCallWS extends AsyncTask<Void, Void, Void> {

        private ProgressDialog dialog = new ProgressDialog(DisplayMemberActivity.this);

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
            this.dialog.setMessage("Please wait");
            this.dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.i(TAG, "doInBackground");
            queryMember();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i(TAG, "onPostExecute");
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            displayInfo();
        }

    }

    private void queryMember(){

        Log.i(TAG, "query");
        String SOAP_ACTION = "urn:PHICWSLibrary-PHICWSService#Authenticate";
        String METHOD_NAME = "Authenticate";
        String NAMESPACE = "http://www.philhealth.gov.ph";
        String URL = "https://training.philhealth.gov.ph/integrated/soap/";

        try {
            Log.i(TAG, "try");
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            //Request.addProperty("A", 1762);
            //Request.addProperty("B", 232);
            Request.addProperty("UserID", getidnum);
            Request.addProperty("UserPassword", getPass);
            Request.addProperty("UserTag", "M");

            //Request.addProperty("B", 232);

            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL, 60000);
            transport.call(SOAP_ACTION, soapEnvelope);
            response = (SoapObject) soapEnvelope.getResponse();
            count = response.getPropertyCount();
            string = response.getProperty(0).toString();
            Log.i(TAG, "Result string : " + string);
            Log.i(TAG, "Result count : " + count);

            Object property = response.getProperty(0);
            if(property instanceof SoapObject) {
                SoapObject final_object = (SoapObject) property;
                count = final_object.getPropertyCount();
                System.out.println("countnipin: " + count);
                System.out.println("pin: " + final_object.getProperty(0).toString());


                getPin = final_object.getProperty(0).toString();
                getLastName = final_object.getProperty(1).toString();
                getFirstName = final_object.getProperty(2).toString();
                getMiddleName = final_object.getProperty(3).toString();
                getSex = final_object.getProperty(4).toString();
                getDOB = final_object.getProperty(5).toString();
                //dgetSuffix = final_object.getProperty(6).toString();
            }
        } catch (Exception ex) {
            Log.e(TAG, "Error: " + ex.getMessage());
        }

    }

    //called when ViewDependents button is pressed
    public void viewDependent(View view){
        Intent intent = new Intent(this, DisplayDependentActivity.class);

        //ADD TO INTENT
        intent.putExtra(EXTRA_ID, getPin);
        startActivity(intent);

    }

}
