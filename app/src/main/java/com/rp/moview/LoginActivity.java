package com.rp.moview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

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
    private EditText passwordField;
    private AutoCompleteTextView usernameField;
    private ViewFlipper viewFlipper;
    private TextView signUp;

    /**Layout*/
    private LinearLayout page1,page2,mLoginForm,login_background;
    private ScrollView loginForm;
    private ProgressBar progressBar;
    private Toolbar toolbar;
    /**Variables*/
    private String mUsername="",mPassword="",mEmail="";
    private Login loginTask;
    private ArrayList<String>usernames=new ArrayList<>();
    private ArrayList<String>emails=new ArrayList<>();
    private FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    private FirebaseDatabase fDatabase=FirebaseDatabase.getInstance();

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
        /**Initialize TextView*/
        signUp=findViewById(R.id.notMember);
        /**Initialize viewFlipper*/
        viewFlipper=findViewById(R.id.viewFlipper);
        /**Initialize LinearLayout*/
        page1=findViewById(R.id.page1);
        page2=findViewById(R.id.page2);
        mLoginForm=findViewById(R.id.email_login_form);
        login_background=findViewById(R.id.login_background);
        /**Initialize ScrollView*/
        loginForm=findViewById(R.id.login_form);
        /**Initialize ProgressBar*/
        progressBar=findViewById(R.id.login_progress);
        /**Initialize toolbar*/
        toolbar=findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);


        /**Start background animation*/

       /* System.out.println("BG: "+login_background.getHeight()+", "+login_background.getWidth());
        final ViewTreeObserver observer= login_background.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                Background_Animations.BlurBackground(context);
                //observer.removeOnGlobalLayoutListener(this);
            }
        });*/

        Background_Animations.background=login_background;
        Background_Animations.AnimateLoginBackGroundGradient();
        /**Populate auto-complete*/
        populateAutoComplete();

        signUp.setVisibility(View.GONE);
        //Initialize login
        loginTask =new Login(mUsername,mPassword,mEmail,context);
        handleButtons();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menus, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.reg) {
           //TODO: create register Activity
            return true;
        }
        else if (id == R.id.home) {

          //TODO: MainActivity
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v("Onstart","retrieving items from database...");
        searchDatabaseForEmailAndUsernames();
       }

    /**Start of Auto-generated code*/

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


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginForm.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginForm.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginForm.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            //mPasswordView.setText(getString(R.string.wait));
            progressBar.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginForm.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        usernameField.setAdapter(adapter);
    }



    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**end of auto-generated code*/
    /**All Event handlers for buttons*/
    public void handleButtons()
    {
        confirmBtn.setOnClickListener(e->{mUsername=usernameField.getText().toString();isEmail(); attemptLogin();});
        loginButton.setOnClickListener(e->{
            mPassword=passwordField.getText().toString();
        if(passwordField.getText().toString().isEmpty())
            passwordField.setError(loginTask.isEmptyField(passwordField.getText().toString()));
        else
        {
            passwordField.setError(null);
            showProgress(true);
            mAuthTask = new UserAuth();
            mAuthTask.execute((Void) null);
        }
        });
    }

    private void isEmail() {
        if(usernameField.getText().toString().contains("@"))
        {
            mEmail=usernameField.getText().toString();
        }
        else {
            int index=usernames.indexOf(usernameField.getText().toString());
            if(index!=-1)
            {
                mEmail=emails.get(index);
            }
            mUsername = usernameField.getText().toString();
        }
    }

    private void goToPasswordPage()
    {
        viewFlipper.showNext();
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_w));
        toolbar.setNavigationOnClickListener(e->{
            goToUsernamePage();
        });

    }
    private void goToUsernamePage()
    {
        toolbar.setNavigationIcon(null);
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
        //Empty field

        if(usernameField.getText().toString().isEmpty())
            usernameField.setError(loginTask.isEmptyField(usernameField.getText().toString()));
        else
        {
            usernameField.setError(null);
            /**If the entered username or email is in the accomodated arraylist then go to pass word*/
            if(loginTask.isMatch(emails,mEmail))
            {
                snackMessage(page1,getString(R.string.userFound)+usernameField.getText().toString(),Snackbar.LENGTH_SHORT);
                goToPasswordPage();
            }
            else  if(loginTask.isMatch(usernames,mUsername))
            {
                snackMessage(page1,getString(R.string.userFound)+usernameField.getText().toString(),Snackbar.LENGTH_SHORT);
                goToPasswordPage();
            }
            else
            {
                snackMessage(page1,getString(R.string.userNotFound,usernameField.getText().toString()),Snackbar.LENGTH_INDEFINITE);
            }

        }
    }

    @Override
    public void onBackPressed() {
        if(page2.getVisibility()==View.VISIBLE)
        {
            goToUsernamePage();
        }
        else
        {
            finish();
        }
    }

    private void toastMessage(String s) {
    Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
    private void snackMessage(View v,String s,int length)
    {
        Snackbar.make(v,s,length).show();
    }

    public static class UserKeys
    {
        public static  ArrayList<String> users=new ArrayList<>();

        public UserKeys(ArrayList<String> users) {
            this.users = users;
        }

        public ArrayList<String> getUserInformation() {
            return users;
        }

        public void setUserInformation(ArrayList<String> users) {
            this.users = users;
        }
    }


    public void searchDatabaseForEmailAndUsernames() {
        /**Load emails and usernames ArrayLists*/



        /**Go through all values at the
         * Node UserInformation*/
        DatabaseReference db = fDatabase.getReference().child("User Information");
        /**We Created a class called UserInformation that has values which could be
         * mapped to the UserInformation branch in FireBase
         * make sure every variable name here matches that of the one we created
         * at fireBase real-time database
         * otherwise there will be a problem trying to get the desired values*/

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    System.out.println("DS: "+ ds);
                    UserInformation users = ds.getValue(UserInformation.class);

                    assert users != null;
                    emails.add(users.getEmail());
                    usernames.add(users.getUsername());
                    UserKeys.users.add(ds.getKey());
                }
                StringBuilder emailResults= new StringBuilder();
                StringBuilder usernamesResults= new StringBuilder();
                for(String s:emails)
                    emailResults.append(s).append("\n");
                for (String s:usernames)
                    usernamesResults.append(s).append("\n");


                Log.wtf("Retrieved: ","Email: \n"+emailResults+"\nUsername: \n"+usernamesResults);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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

        public UserAuth()
        {
            username=mUsername;
            password=mPassword;
            email=mEmail;
            Log.wtf("Credentials: ","Username: "+username+"\nPassword: "+password+"\nEmail: "+email);
        }


        @Override
        protected Boolean doInBackground(Void... voids)
        {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                return false;
            }

            // TODO: sign in the user here.

            firebaseAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Log.d("signInWithEmail: ", "Success");
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                //TODO: Launch MainActivity
                                finish();
                            }
                            else{
                                Log.d("signInWithEmail: ", "Success");
                                signUp.setVisibility(View.VISIBLE);
                                snackMessageWithButton(page2,getString(R.string.error_incorrect_password),Snackbar.LENGTH_INDEFINITE,getString(R.string.forgotPassword), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                       //TODO: password Recovery
                                    }
                                }, Color.GREEN);

                            }
                        }
                    });

            return true;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                // finish();
            } else {
                passwordField.setError(getString(R.string.error_incorrect_password));
                passwordField.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }



    private void snackMessageWithButton(View v, String msg, int l, String btnMsg, View.OnClickListener click,int color) {
        Snackbar snackbar = Snackbar
                .make(v, msg,l );
        snackbar.setAction(btnMsg,click);
        snackbar.setActionTextColor(color);
        snackbar.show();
    }

}
