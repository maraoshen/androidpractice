package com.example.art.myapplication2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_ID = "com.example.art.myapplication2.ID";
    public final static String EXTRA_LNAME = "com.example.art.myapplication2.LNAME";
    public final static String EXTRA_FNAME = "com.example.art.myapplication2.FNAME";
    public final static String EXTRA_MNAME = "com.example.art.myapplication2.MNAME";
    public final static String EXTRA_ENAME = "com.example.art.myapplication2.ENAME";
    public final static String EXTRA_BDAY = "com.example.art.myapplication2.BDAY";
    public final static String EXTRA_SEX = "com.example.art.myapplication2.SEX";


    String TAG = "Response";
    Button bt;
    EditText phidnum, lname, fname, mname, ename, bday;
    String getidnum, getLname, getFname, getMname, getEname, getBday, getSex;
    SoapPrimitive resultString;
    TextView tvidnum, tvname, tvbday, tvsex, tvsum;
    RadioButton radioSexButton;
    RadioGroup radioSexGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        bt = (Button) findViewById(R.id.button);
        phidnum = (EditText) findViewById(R.id.editTextIDNum);
        lname = (EditText) findViewById(R.id.editTextLastName);
        fname = (EditText) findViewById(R.id.editTextFirstName);
        mname = (EditText) findViewById(R.id.editTextMidName);
        ename = (EditText) findViewById(R.id.editTextExtName);
        bday = (EditText) findViewById(R.id.editTextBday);
        radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);

    }





    //pag pinindot ang search button
    public void searchMember(View view){

        // get selected radio button from radioGroup
        int selectedId = radioSexGroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        radioSexButton = (RadioButton) findViewById(selectedId);

        getidnum = phidnum.getText().toString();
        getLname = lname.getText().toString();
        getFname = fname.getText().toString();
        getMname = mname.getText().toString();
        getEname = ename.getText().toString();
        getBday = bday.getText().toString();
        getSex = radioSexButton.getText().toString();

        if (!isInputValid()){

        }
        else {

            Intent intent = new Intent(this, DisplayMemberActivity.class);

            //ADD TO INTENT
            intent.putExtra(EXTRA_ID, getidnum);
            intent.putExtra(EXTRA_LNAME, getLname);
            intent.putExtra(EXTRA_FNAME, getFname);
            intent.putExtra(EXTRA_MNAME, getMname);
            intent.putExtra(EXTRA_ENAME, getEname);
            intent.putExtra(EXTRA_BDAY, getBday);
            intent.putExtra(EXTRA_SEX, getSex);
            startActivity(intent);

            /*

            String SOAP_ACTION = "urn:PHICWSLibrary-PHICWSService#Sum";
            String METHOD_NAME = "Sum";
            String NAMESPACE = "http://www.philhealth.gov.ph";
            String URL = "https://training.philhealth.gov.ph/integrated/soap/";

            try {
                SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
                Request.addProperty("A", 1762);
                Request.addProperty("B", 232);

                SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                soapEnvelope.dotNet = true;
                soapEnvelope.setOutputSoapObject(Request);

                HttpTransportSE transport = new HttpTransportSE(URL);

                transport.call(SOAP_ACTION, soapEnvelope);
                resultString = (SoapPrimitive) soapEnvelope.getResponse();

                Log.i(TAG, "Result Celsius: " + resultString);
            } catch (Exception ex) {
                Log.e(TAG, "Error: " + ex.getMessage());
            }
            */
        }

    }

    private boolean isInputValid(){
        if (getidnum.length() == 12) { //mali id num
            return true;
        }else if (getidnum.length() == 0){
            if (!isValidLname(getLname)) {
                lname.setError("Invalid Last Name");
                return false;
            }
            if (!isValidFname(getFname)) {
                fname.setError("Invalid First Name");
                return false;
            }
            if (!isValidBday(getBday)) {
                bday.setError("Invalid Birthday");
                return false;
            }
            return true;
        }else{
            phidnum.setError("Invalid ID Number");
            return false;
        }
    }

    private boolean isValidLname(String a){
        if (!a.equals("")) {
            return true;
        }
        return false;
    }
    private boolean isValidFname(String a){
        if (!a.equals("")) {
            return true;
        }
        return false;
    }
    private boolean isValidBday(String a){
        if (!a.equals("")) {
            return true;
        }
        return false;
    }
/*
        //pag pinindot ang search button
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get selected radio button from radioGroup
                int selectedId = radioSexGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioSexButton = (RadioButton) findViewById(selectedId);

                getidnum = phidnum.getText().toString();
                getLname = lname.getText().toString();
                getFname = fname.getText().toString();
                getMname = mname.getText().toString();
                getEname = ename.getText().toString();
                getBday = bday.getText().toString();
                getSex = radioSexButton.getText().toString();

                AsyncCallWS task = new AsyncCallWS();
                task.execute();
            }
        });
    }

    private class AsyncCallWS extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.i(TAG, "doInBackground");
            //calculate();
            //computeSum();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i(TAG, "onPostExecute");
            //Toast.makeText(MainActivity.this, "Response: " + resultString.toString(), Toast.LENGTH_LONG).show();
            tvidnum.setText(getidnum);
            tvname.setText(getLname + ", " + getFname + ", " + getMname + " " + getEname);
            tvsex.setText(getSex);
            tvbday.setText(getBday);

            tvsum.setText(resultString.toString());
        }

    }

    public void computeSum() {
        String SOAP_ACTION = "urn:PHICWSLibrary-PHICWSService#GetServerTime";
        String METHOD_NAME = "GetServerTime";
        String NAMESPACE = "http://www.philhealth.gov.ph";
        String URL = "http://10.0.2.2:8099/SOAP?service=PHICWSService";

        try {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            //Request.addProperty("A", 12);
            //Request.addProperty("B", 2);

            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(Request);

            HttpTransportSE transport = new HttpTransportSE(URL);

            transport.call(SOAP_ACTION, soapEnvelope);

            resultString = (SoapPrimitive) soapEnvelope.getResponse();

            Log.i(TAG, "Result : " + resultString);
        } catch (Exception ex) {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }*/
}