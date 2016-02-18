package com.example.art.myapplication2;

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


    TabHost tabHost;
    String getidnum, getLname, getFname, getMname, getEname, getBday;
    String getPin,    getLastName,    getFirstName,    getMiddleName,    getSex,    getDOB,    getSuffix;
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        extractIntent();
        getTextViewId();
        //displayInfo();
        //send data to ksoap and retrieve data
        AsyncCallWS task = new AsyncCallWS();
        task.execute();
        //queryMember();
    }

    private void extractIntent(){
        Intent intent = getIntent();
        getidnum = intent.getStringExtra(MainActivity.EXTRA_ID);
        getLname = intent.getStringExtra(MainActivity.EXTRA_LNAME);
        getFname = intent.getStringExtra(MainActivity.EXTRA_FNAME);
        getMname = intent.getStringExtra(MainActivity.EXTRA_MNAME);
        getEname = intent.getStringExtra(MainActivity.EXTRA_ENAME);
        getBday = intent.getStringExtra(MainActivity.EXTRA_BDAY);
        getSex = intent.getStringExtra(MainActivity.EXTRA_SEX);
    }

    private void getTextViewId(){
        Pin = (TextView) findViewById(R.id.textView);
        LastName = (TextView) findViewById(R.id.textView2);
        FirstName = (TextView) findViewById(R.id.textView3);
        MiddleName = (TextView) findViewById(R.id.textView4);
        Sex = (TextView) findViewById(R.id.textView5);
        DOB = (TextView) findViewById(R.id.textView6);
        Suffix = (TextView) findViewById(R.id.textView7);
    }

    private void displayInfo(){
        Pin.setText(getPin);
        LastName.setText(getLastName);
        FirstName.setText(getFirstName);
        MiddleName.setText(getMiddleName);
        Sex.setText(getSex);
        DOB.setText(getDOB);
        Suffix.setText(getSuffix);
    }

    private class AsyncCallWS extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
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
            displayInfo();
        }

    }

    private void queryMember(){
        /*
        //String SOAP_ACTION = "urn:PHICWSLibrary-PHICWSService#Sum";
        String SOAP_ACTION = "urn:NewWsLibrary-NewWebService#Sum";
        String METHOD_NAME = "Sum";
        //String NAMESPACE = "http://www.philhealth.gov.ph";
        String NAMESPACE = "http://tempuri.org/";
        //String URL = "http://training.philhealth.gov.ph:443/soap/?service=PHICWSService";
        String URL = "http://localhost:8099/SOAP?service=NewWebService";
        Log.i(TAG, "query mem");

        try {
            Log.i(TAG, "try");
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            Request.addProperty("A", 12);
            Request.addProperty("B", 2);
            Log.i(TAG, "try1");

            //Request.addProperty("PhilHealthNo", getidnum);
            //Request.addProperty("LastName", getLname);
            //Request.addProperty("FirstName", getFname);
            //Request.addProperty("MiddleName", getMname);
            //Request.addProperty("Sex", getSex);
            //Request.addProperty("DOB", getBday);


            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            Log.i(TAG, "try2");

            soapEnvelope.dotNet = true;
            Log.i(TAG, "try3");

            soapEnvelope.setOutputSoapObject(Request);
            Log.i(TAG, "try4");

            HttpTransportSE transport = new HttpTransportSE(URL);
            Log.i(TAG, "try5");

            transport.call(SOAP_ACTION, soapEnvelope);

            Log.i(TAG, "print");
            resultString = (SoapPrimitive) soapEnvelope.getResponse();
            if (resultString == null){
                Log.i(TAG, "null");
            }else {
                Log.i(TAG, "not");
            }
            //response = (SoapObject) soapEnvelope.getResponse();
            //Log.d("Response", response.toString());
            //int count = response.getPropertyCount();


            Log.d(TAG, "Result : " + resultString.toString());
            tvname.setText("result: " + resultString.toString());

        } catch (Exception ex) {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
*/
        String SOAP_ACTION = "urn:PHICWSLibrary-PHICWSService#SearchMembers";
        String METHOD_NAME = "SearchMembers";
        String NAMESPACE = "http://www.philhealth.gov.ph";
        String URL = "https://training.philhealth.gov.ph/integrated/soap/";

        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            //Request.addProperty("A", 1762);
            //Request.addProperty("B", 232);
            Request.addProperty("PhilHealthNo", getidnum);
            //Request.addProperty("B", 232);

            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(Request);

            HttpTransportSE transport = new HttpTransportSE(URL);

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
                getSuffix = final_object.getProperty(6).toString();
            }
        } catch (Exception ex) {
            Log.e(TAG, "Error: " + ex.getMessage());
        }

    }

    public void viewDependent(View view){
        Intent intent = new Intent(this, DisplayDependentActivity.class);

        //ADD TO INTENT
        intent.putExtra(EXTRA_ID, getPin);
        startActivity(intent);

    }

}
