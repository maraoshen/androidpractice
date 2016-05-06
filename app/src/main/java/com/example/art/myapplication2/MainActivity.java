package com.example.art.myapplication2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_ID = "com.example.art.myapplication2.ID";
    public final static String EXTRA_PASS = "com.example.art.myapplication2.Pass";
    public final static String EXTRA_LNAME = "com.example.art.myapplication2.LNAME";
    public final static String EXTRA_FNAME = "com.example.art.myapplication2.FNAME";
    public final static String EXTRA_MNAME = "com.example.art.myapplication2.MNAME";
    public final static String EXTRA_ENAME = "com.example.art.myapplication2.ENAME";
    public final static String EXTRA_BDAY = "com.example.art.myapplication2.BDAY";
    public final static String EXTRA_SEX = "com.example.art.myapplication2.SEX";

    public static boolean isLoggedIn = false;


    String TAG = "Response";
    Button bt;
    EditText phidnum, lname, fname, mname, ename, bday, password;
    String getidnum, getLname, getFname, getMname, getEname, getBday, getSex, getpass;
    RadioButton radioSexButton, radioBtnUser;
    RadioGroup radioSexGroup, radioGroupUserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
  //      Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
  //      setSupportActionBar(myToolbar);

        //get the object for the textfields
        phidnum = (EditText) findViewById(R.id.editTextIDNum);
        password = (EditText) findViewById(R.id.editTextPassword);
        //lname = (EditText) findViewById(R.id.editTextLastName);
        //fname = (EditText) findViewById(R.id.editTextFirstName);
        //mname = (EditText) findViewById(R.id.editTextMidName);
        //ename = (EditText) findViewById(R.id.editTextExtName);
        //bday = (EditText) findViewById(R.id.editTextBday);
        //radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
        //tvsum = (TextView) findViewById(R.id.mac);

    }

    //pag pinindot ang search button
    public void searchMember(View view){

        // get selected radio button from radioGroup
        //int selectedId = radioSexGroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        //radioSexButton = (RadioButton) findViewById(selectedId);

        //get text inside textfields
        getidnum = phidnum.getText().toString();
        getpass = password.getText().toString();
        //getLname = lname.getText().toString();
        //getFname = fname.getText().toString();
        //getMname = mname.getText().toString();
        //getEname = ename.getText().toString();
        //getBday = bday.getText().toString();
        //getSex = radioSexButton.getText().toString();

        //if inputs are valid, send data to displaymemberactivity
        if (!isInputValid()){

        }
        else {

            Intent intent = new Intent(this, DisplayMemberActivity.class);

            //ADD TO INTENT
            intent.putExtra(EXTRA_ID, getidnum);
            intent.putExtra(EXTRA_PASS, getpass);
            //intent.putExtra(EXTRA_LNAME, getLname);
            //intent.putExtra(EXTRA_FNAME, getFname);
            //intent.putExtra(EXTRA_MNAME, getMname);
            //intent.putExtra(EXTRA_ENAME, getEname);
            //intent.putExtra(EXTRA_BDAY, getBday);
            //intent.putExtra(EXTRA_SEX, getSex);
            startActivity(intent);

        }

    }

    private boolean isInputValid(){
        if (getidnum.length() == 12 && getpass.length() != 0) { //mali id num
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
}