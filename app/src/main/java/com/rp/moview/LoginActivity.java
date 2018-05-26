package com.rp.moview;

import android.annotation.TargetApi;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.ViewFlipper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static android.Manifest.permission.READ_CONTACTS;

public class LoginActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    Context context=this;
    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and cPasswords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserAuth mAuthTask = null;

    /**widgets */
    private Button confirmBtn,loginButton;
    private EditText usernameField,passwordField;
    private ViewFlipper viewFlipper;

    /**Layout*/
    private LinearLayout page1,page2;
    private ScrollView loginForm;
    private ProgressBar progressBar;
    /**Variables*/
    private String mUsername,mPassword,mEmail;
    private Login loginTask;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /**Initialize Buttons*/
        confirmBtn=findViewById(R.id.confirmBtn);
        loginButton=findViewById(R.id.loginBtn);
        /**Initialize EditText*/
        usernameField=findViewById(R.id.email);
        passwordField=findViewById(R.id.password);
        /**Initialize viewFlipper*/
        viewFlipper=findViewById(R.id.viewFlipper);
        /**Initialize LinearLayout*/
        page1=findViewById(R.id.page1);
        page2=findViewById(R.id.page2);
        /**Initialize ScrollView*/
        loginForm=findViewById(R.id.login_form);
        /**Initialize ProgressBar*/
        progressBar=findViewById(R.id.progressBar);

        /**Populate auto-complete*/
        populateAutoComplete();

        //Initialize login
        loginTask =new Login(mUsername,mPassword,mEmail,context);


    }
    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }
    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }
    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(usernameField, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
    private void goToPasswordPage()
    {
        viewFlipper.showNext();
    }
    private void goToUsernamePage()
    {
        viewFlipper.showPrevious();
    }
    private void goToProgressPage()
    {
        progressBar.setVisibility(View.VISIBLE);
        loginForm.setVisibility(View.GONE);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin()
    {
        if(checkIfEmail())
        {
            if(checkIfEmailExist())
            {

            }

        }
    }

    private boolean checkIfEmail() {
        return false;
    }
    private boolean checkIfEmailExist() {
        return false;
    }

    //TODO:Authenticate Against network
    //TODO:Check if user exists
    public class UserAuth extends AsyncTask<Void, Void, Boolean>
    {
        private String username,password,email;
        private ArrayList<String>usernames=new ArrayList<>();
        private FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        private FirebaseDatabase fDatabase=FirebaseDatabase.getInstance();
        public UserAuth()
        {

        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return null;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }


    }

}
