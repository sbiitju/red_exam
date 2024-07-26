package com.nupuit.vetmed.utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nupuit.vetmed.model.MockupList;
import com.nupuit.vetmed.model.MockupModel;
import com.nupuit.vetmed.model.MockupSetContainedID;
import com.nupuit.vetmed.model.RealmString;
import com.nupuit.vetmed.model.TwoMarkQuestions;

import java.util.ArrayList;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by USER on 20-May-17.
 */

public class DataFromFirebase {

    Context context;
    Realm realm;
    ProgressDialog loading;
    FirebaseDatabase database, database4;
    DatabaseReference ref;


    public DataFromFirebase(Context context) {

        this.context = context;
        Realm.init(context);
        realm = Realm.getDefaultInstance();

    }

    public void getDataFromFirebase() {
        loading = new ProgressDialog(context);
        loading.setCancelable(false);
        loading.setTitle("Loading...");
        loading.setMessage("Wait for a few seconds. Gathering data and question sets...");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.show();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
//
                realm.where(TwoMarkQuestions.class).findAll().deleteAllFromRealm();
            }
        });

        final int[] i = {0};
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("questions");
        ref.keepSynced(true);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    // TODO: handle the post

                    String qsnNumber = postSnapshot.child("qsnNumber").getValue().toString();

                    //Log.d("ErrorNumber", qsnNumber);

                    String qsn = postSnapshot.child("qsn").getValue().toString();
                    String op1 = postSnapshot.child("options1").getValue().toString();
                    String op2 = postSnapshot.child("options2").getValue().toString();
                    String op3 = postSnapshot.child("options3").getValue().toString();
                    String op4 = postSnapshot.child("options4").getValue().toString();
                    String correctAns = postSnapshot.child("correctAns").getValue().toString();
                    String explaination = postSnapshot.child("explaination").getValue().toString();

                    String mark = postSnapshot.child("mark").getValue().toString();

                    final TwoMarkQuestions twoMarkQuestions = new TwoMarkQuestions(qsn, op1, op2, op3, op4, correctAns, explaination, i[0] + "", mark);

                    //Log.e("class", twoMarkQuestions.toString());

                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            //  realm.where(OneMarkQuestion.class).findAll().deleteAllFromRealm();
                            realm.copyToRealm(twoMarkQuestions);
                        }
                    });

                    i[0]++;

                }


                makeMockupSet();
              //  parseFourMarkQuestion();
                // parseTenMarkQuestion();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


    }


    private void makeMockupSet() {

        RealmResults<TwoMarkQuestions> realmResults2 = realm.where(TwoMarkQuestions.class).findAll();
        int size2 = realmResults2.size();

        for (int i = 0; i < 100; i++) {

            RealmList<TwoMarkQuestions> questions2 = new RealmList<TwoMarkQuestions>();
            RealmList<RealmString> questionsNUmber2 = new RealmList<RealmString>();

            ArrayList<String> templist = new ArrayList<String>();

            RealmList<RealmString> tempList = new RealmList<RealmString>();


            while (questions2.size() < 10) {

                Random r = new Random();
                int randomNumber = r.nextInt((int) size2);

                final TwoMarkQuestions oneMarkQuestion = realmResults2.get(randomNumber);
                RealmString re = new RealmString(oneMarkQuestion.getQsnNumber());

                if (templist.size() == 0) {
                    templist.add(oneMarkQuestion.getQsnNumber());
                    questions2.add(oneMarkQuestion);
                    questionsNUmber2.add(re);
                    tempList.add(re);
                } else {
                    if (!templist.contains(oneMarkQuestion.getQsnNumber())) {
                        templist.add(oneMarkQuestion.getQsnNumber());
                        questions2.add(oneMarkQuestion);
                        questionsNUmber2.add(re);
                        tempList.add(re);
                    } else continue;
                }


              //  Log.d("qsnNumber", questions2.size() + " of qsn 2");

            }

            MockupSetContainedID mockID = new MockupSetContainedID(i + "", tempList);
            MockupList mockupList = new MockupList(questions2, i + "");
            MockupModel mockupModel1 = new MockupModel("Mockup " + (i + 1), "18 min", "20", i + "", true, "10");

            saveToRealm(mockupList, mockupModel1, mockID);
        }

        loading.dismiss();

    }


    private void saveToRealm(final MockupList mockupList, final MockupModel mockupModel, final MockupSetContainedID mockID) {


        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(mockupList);
                realm.copyToRealm(mockupModel);
                realm.copyToRealm(mockID);
            }
        });

    }


}
