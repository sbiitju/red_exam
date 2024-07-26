package com.nupuit.vetmed.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//import com.facebook.CallbackManager;
//import com.facebook.FacebookCallback;
//import com.facebook.FacebookException;
//import com.facebook.FacebookSdk;
//import com.facebook.GraphRequest;
//import com.facebook.GraphResponse;
//import com.facebook.login.LoginResult;
//import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nupuit.vetmed.R;
import com.nupuit.vetmed.activity.MainActivity;
import com.nupuit.vetmed.activity.RegistrationActivity;
import com.nupuit.vetmed.utils.SharedPrefsSingleton;

public class LogInFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener {


    Button btnSignIn;
  //  EditText edt_name;
    EditText edt_email;
    TextView create_account, skip_now;
    String name;
    String email;
    ProgressDialog loading;


    //google
    GoogleSignInOptions gso;
    GoogleApiClient mGoogleApiClient;
    SignInButton signInButton;
    private static final int RC_SIGN_IN = 1;

    //firebase
    private FirebaseAuth mAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
 //       FacebookSdk.sdkInitialize(getActivity());
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);
        initialize(view);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity() /* FragmentActivity */, 1, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });



        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

          //      name = edt_name.getText().toString();
                email = edt_email.getText().toString();

//                if
//                        (name.equals("") || name == null) {
//                    edt_name.setError("Name can not be empty");
//                } else if (name.length() < 3) {
//                    edt_name.setError("Name should be more than 3 letter");
//                } else {

                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        edt_email.setError("Enter a valid email");
                    } else {


                        name = email.substring(0, email.indexOf("@"));
                        worksOnAuth(name, email);

                    }


              //  }

            }
        });

        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RegistrationActivity.class));
                getActivity().finish();
            }
        });

        skip_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefsSingleton.getInstance(getContext()).saveBoolean("skipped", true);
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });


        return view;
    }


    public LogInFragment() {
        // Required empty public constructor
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }





//        LoginManager.getInstance().logInWithReadPermissions(getActivity(),
//                Arrays.asList("email"));




    private void worksOnAuth(final String name, final String email) {

        //Log.e("cred", "name: " + name);
        //Log.e("cred", "email: " + email);

        loading.show();


        mAuth.signInWithEmailAndPassword(email, "123456")
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d("Fact", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w("Fact", "signInWithEmail:failure", task.getException());
      //                      Toast.makeText(getActivity(), task.getException().getMessage(),
       //                             Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });


    }

    private void initialize(View view) {

        loading = new ProgressDialog(getContext());
        loading.setCancelable(false);
        loading.setMessage("Signing in...");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        edt_email = (EditText) view.findViewById(R.id.edt_email);
     //   edt_name = (EditText) view.findViewById(R.id.edt_name);
        btnSignIn = (Button) view.findViewById(R.id.btnSignIn);

//        create_account = (TextView) view.findViewById(R.id.create_account);
        skip_now = (TextView) view.findViewById(R.id.skip);


//        signInButton = (SignInButton) view.findViewById(R.id.sign_in_button);

        mAuth = FirebaseAuth.getInstance();

    }




    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            loading.dismiss();
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        } else {
            loading.dismiss();
//            Toast.makeText(getActivity(), "Not Logged In", Toast.LENGTH_LONG).show();
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        //Log.d("Fact", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            name = acct.getDisplayName();
            email = acct.getEmail();
            worksOnAuth(name, email);
            //updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            //Log.d("Fact", "handleSignInResult:" + " Failed");
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // Log.d("Fact", "handleSignInResult:" + connectionResult.toString());
    }
}