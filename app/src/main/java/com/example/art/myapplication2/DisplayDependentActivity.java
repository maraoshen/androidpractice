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
    String TAG = "Response";
    SoapObject response;
    private int count;
    String string;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_dependent);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //init();       put sa post

        extractIntent();
        AsyncCallWS task = new AsyncCallWS();
    }

    private void extractIntent(){
        Intent intent = getIntent();
        getidnum = intent.getStringExtra(MainActivity.EXTRA_ID);
    }

    private class AsyncCallWS extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.i(TAG, "doInBackground");
            queryDependent();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i(TAG, "onPostExecute");
            //displayInfo();
        }

    }

    public void init(){
        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        TableRow tbrow0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        tv0.setText(" Sl.No ");
        //tv0.setTextColor(Color.WHITE);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText(" Product ");
        //tv1.setTextColor(Color.WHITE);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText(" Unit Price ");
        //tv2.setTextColor(Color.WHITE);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText(" Stock Remaining ");
        //tv3.setTextColor(Color.WHITE);
        tbrow0.addView(tv3);
        stk.addView(tbrow0);
        for (int i = 0; i < 25; i++) {
            TableRow tbrow = new TableRow(this);
            TextView t1v = new TextView(this);
            t1v.setText("" + i);
            //t1v.setTextColor(Color.WHITE);
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);
            TextView t2v = new TextView(this);
            t2v.setText("Product " + i);
            //t2v.setTextColor(Color.WHITE);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);
            TextView t3v = new TextView(this);
            t3v.setText("Rs." + i);
            //t3v.setTextColor(Color.WHITE);
            t3v.setGravity(Gravity.CENTER);
            tbrow.addView(t3v);
            TextView t4v = new TextView(this);
            t4v.setText("" + i * 15 / 32 * 10);
            //t4v.setTextColor(Color.WHITE);
            t4v.setGravity(Gravity.CENTER);
            tbrow.addView(t4v);
            stk.addView(tbrow);
        }
    }

    public void queryDependent(){
        String SOAP_ACTION = "urn:PHICWSLibrary-PHICWSService#GetMemberDependents";
        String METHOD_NAME = "GetMemberDependents";
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
            }
        } catch (Exception ex) {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

}
