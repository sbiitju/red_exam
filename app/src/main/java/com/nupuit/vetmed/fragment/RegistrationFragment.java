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
import android.widget.Toast;

//import com.facebook.CallbackManager;
//import com.facebook.FacebookCallback;
//import com.facebook.FacebookException;
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
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nupuit.vetmed.R;
//import com.facebook.FacebookSdk;
import com.nupuit.vetmed.activity.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener {


    private static final int RC_SIGN_IN = 1;
    //manual
    Button btnSignIn;
    EditText edt_name, edt_email;
    ProgressDialog loading;


    //facebook



    //common
    String name, email;


    //google
    GoogleSignInOptions gso;
    GoogleApiClient mGoogleApiClient;
    SignInButton signInButton;

    //creating user to firebase
    private FirebaseAuth mAuth;

    FirebaseDatabase database;
    DatabaseReference ref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
 //       FacebookSdk.sdkInitialize(getActivity());
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        initialize(view);

        signInButton = (SignInButton) view.findViewById(R.id.google_sign_in);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity() /* FragmentActivity */, this /* OnConnectionFailedListener */)
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

                name = edt_name.getText().toString();
                email = edt_email.getText().toString();

                if (name.equals("") || name == null) {
                    edt_name.setError("Name can not be empty");
                } else if (name.length() < 3) {
                    edt_name.setError("Name should be more than 3 letter");
                } else {

                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        edt_email.setError("Enter a valid email");
                    } else {
                        worksOnAuth(name, email);
                    }


                }

            }
        });


        return view;
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }






//        LoginManager.getInstance().logInWithReadPermissions(getActivity(),
//                Arrays.asList("email"));







    private void worksOnAuth(final String name, final String email) {

        loading.show();

        //Log.e("cred", "name: " + name);
        //Log.e("cred", "email: " + email);


        mAuth.createUserWithEmailAndPassword(email, "123456")
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d("fact", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            database = FirebaseDatabase.getInstance();
                            String userId = user.getUid();
                            ref = database.getReference("users");
                            ref.child(userId).child("name").setValue(name);
                            ref.child(userId).child("email").setValue(email);
                            updateUI(user);

                            setUsername(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w("fact", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getActivity(), task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });




    }

    private void setUsername(FirebaseUser user) {

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //Log.d("Factory", "User profile updated.");
                            startActivity(new Intent(getActivity(), MainActivity.class));
                            getActivity().finish();
                        }
                    }
                });

        loading.dismiss();


    }

    private void initialize(View view) {
        loading = new ProgressDialog(getContext());
        loading.setCancelable(false);
        loading.setMessage("Registering...");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        edt_email = (EditText) view.findViewById(R.id.edt_email);
        edt_name = (EditText) view.findViewById(R.id.edt_name);
        btnSignIn = (Button) view.findViewById(R.id.registration_button);




        mAuth = FirebaseAuth.getInstance();

    }

    public RegistrationFragment() {
        // Required empty public constructor
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

        } else {
            loading.dismiss();
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
        //Log.d("Fact", "handleSignInResult:" + connectionResult.toString());
    }
}
