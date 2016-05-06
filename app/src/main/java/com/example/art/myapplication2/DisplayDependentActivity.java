package com.example.art.myapplication2;

import android.os.AsyncTask;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class DisplayDependentActivity extends AppCompatActivity {
    String getidnum;
    String getPin,    getLastName,    getFirstName,    getMiddleName,    getSex,    getDOB,    getStatus;
    String TAG = "Response";
    SoapObject response;
    private int count;
    String string;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_dependent);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //init();       put sa post
        Log.i(TAG, "dependentsActivity");


        extractIntent();
        AsyncCallWS task = new AsyncCallWS();
        task.execute();
    }

    private void extractIntent(){
        Intent intent = getIntent();
        getidnum = intent.getStringExtra(MainActivity.EXTRA_ID);
        Log.i(TAG, "Intent pin : " + getidnum);

    }

    private class AsyncCallWS extends AsyncTask<Void, Void, SoapObject> {

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
        }

        @Override
        protected SoapObject doInBackground(Void... params) {
            Log.i(TAG, "doInBackground");
            return queryDependent();    //returns a SoapObject dependents
        }

        //accepts soapobject from doinbaackground as parameter
        protected void onPostExecute(SoapObject res) {
            Log.i(TAG, "onPostExecute");
            //displayInfo();
            init(res);  //put in table dependent data
        }

    }

    public void init(SoapObject res){
        Log.i(TAG, "hello");
        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        TableRow tbrow0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        tv0.setText(" Pin ");
        tv0 = (TextView) setTextStyleHeader(tv0);
        //tv0.setTextColor(Color.WHITE);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText(" Last Name ");
        tv1 = (TextView) setTextStyleHeader(tv1);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText(" First Name ");
        tv2 = (TextView) setTextStyleHeader(tv2);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText(" Middle Name ");
        tv3 = (TextView) setTextStyleHeader(tv3);
        tbrow0.addView(tv3);
        TextView tv4 = new TextView(this);
        tv4.setText(" Status ");
        tv4 = (TextView) setTextStyleHeader(tv4);
        tbrow0.addView(tv4);
        TextView tv5 = new TextView(this);
        tv5.setText(" Sex ");
        tv5 = (TextView) setTextStyleHeader(tv5);
        tbrow0.addView(tv5);
        TextView tv6 = new TextView(this);
        tv6.setText(" Birthdate ");
        tv6 = (TextView) setTextStyleHeader(tv6);
        tbrow0.addView(tv6);
        stk.addView(tbrow0);

        for (int i=0; i<count; i++){
            TableRow tbrow = new TableRow(this);    //create a new row

            Object property = res.getProperty(i);
            if(property instanceof SoapObject) {
                SoapObject final_object = (SoapObject) property;
                int objectCount = final_object.getPropertyCount();
                System.out.println("countnipin: " + objectCount);
                System.out.println("dependentstuff: " + final_object.getProperty(1).toString());


                getPin = final_object.getProperty(0).toString();
                //create textviewfor pin
                TextView t1v = new TextView(this);
                t1v.setText(getPin);
                //t1v.setTextColor(Color.WHITE);
                t1v.setGravity(Gravity.CENTER);
                tbrow.addView(t1v);

                getLastName = final_object.getProperty(1).toString();
                //lastname
                TextView t2v = new TextView(this);
                t2v.setText(getLastName);
                //t2v.setTextColor(Color.WHITE);
                t2v.setGravity(Gravity.CENTER);
                tbrow.addView(t2v);


                getFirstName = final_object.getProperty(2).toString();
                //firstname
                TextView t3v = new TextView(this);
                t3v.setText(getFirstName);
                //t3v.setTextColor(Color.WHITE);
                t3v.setGravity(Gravity.CENTER);
                tbrow.addView(t3v);


                getMiddleName = final_object.getProperty(3).toString();
                //middlename
                TextView t4v = new TextView(this);
                t4v.setText(getMiddleName);
                //t4v.setTextColor(Color.WHITE);
                t4v.setGravity(Gravity.CENTER);
                tbrow.addView(t4v);

                getStatus = final_object.getProperty(5).toString();
                //status
                TextView t5v = new TextView(this);
                t5v.setText(getStatus);
                //t4v.setTextColor(Color.WHITE);
                t5v.setGravity(Gravity.CENTER);
                tbrow.addView(t5v);


                getSex = final_object.getProperty(7).toString();
                //sex
                TextView t6v = new TextView(this);
                t6v.setText(getSex);
                //t4v.setTextColor(Color.WHITE);
                t6v.setGravity(Gravity.CENTER);
                tbrow.addView(t6v);


                getDOB = final_object.getProperty(6).toString();
                //birthdate
                TextView t7v = new TextView(this);
                t7v.setText(getDOB);
                //t4v.setTextColor(Color.WHITE);
                t7v.setGravity(Gravity.CENTER);
                tbrow.addView(t7v);

                stk.addView(tbrow);
            }
        }
    }

    public TextView setTextStyleHeader(TextView t){
        t.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        t.setTextColor(Color.BLACK);
        t.setAllCaps(true);
        return t;
    }

    public SoapObject queryDependent(){
        String SOAP_ACTION = "urn:PHICWSLibrary-PHICWSService#GetMemberDependents";
        String METHOD_NAME = "GetMemberDependents";
        String NAMESPACE = "http://www.philhealth.gov.ph";
        String URL = "https://training.philhealth.gov.ph/integrated/soap/";

        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            Log.i(TAG, "before query");
            Request.addProperty("Pin", getidnum);
            //Request.addProperty("B", 232);

            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(Request);

            HttpTransportSE transport = new HttpTransportSE(URL,60000);

            transport.call(SOAP_ACTION, soapEnvelope);
            Log.i(TAG, "after query");


            response = (SoapObject) soapEnvelope.getResponse();
            count = response.getPropertyCount();
            string = response.getProperty(0).toString();
            Log.i(TAG, "Result string : " + string);
            Log.i(TAG, "Result count2 : " + count);

            //pass response to init

            return response;

        } catch (Exception ex) {
            Log.e(TAG, "Error: " + ex.getMessage());
            return null;
        }
    }

}
