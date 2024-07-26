package com.nupuit.vetmed.adapter;

import android.app.Activity;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.nupuit.vetmed.R;
import com.nupuit.vetmed.model.TwoMarkQuestions;

import io.realm.Realm;
import io.realm.RealmList;

//import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by USER on 23-May-17.
 */

public class AdapterHistoryDetails extends RecyclerView.Adapter<AdapterHistoryDetails.MyViewHolder> {

    Realm realm;


    Context context;
    Activity activity;

    RealmList<TwoMarkQuestions> questions2 = new RealmList<TwoMarkQuestions>();

    private static final int TYPE_ITEM_1 = 1;

    public AdapterHistoryDetails(Context context, RealmList<TwoMarkQuestions> questions2) {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
        this.questions2 = questions2;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_history_details_1_2_marks, parent, false);
            return new MyViewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position) {

            return TYPE_ITEM_1;
    }

    @Override
    public void onBindViewHolder(AdapterHistoryDetails.MyViewHolder holder, int position) {


        if (position < questions2.size()) {
            holder.questionText.setText("Question: "+questions2.get(position).getQsn());

            String qsnNumber = questions2.get(position).getQsnNumber();

            String imageName = "q" + qsnNumber;
            String PACKAGE_NAME = context.getPackageName();
            int imgId = context.getResources().getIdentifier(PACKAGE_NAME+":drawable/"+imageName , null, null);

            if(imgId==0){
                holder.imageViewHistory.setVisibility(View.INVISIBLE);
                holder.imageViewHistory.getLayoutParams().height=0;
            }
            else {
                //imageViewMock.getLayoutParams().height=200;
                //imageViewMock.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                //imageViewMock.setScaleType(ImageView.ScaleType.FIT_CENTER);
                holder.imageViewHistory.setVisibility(View.VISIBLE);
                holder.imageViewHistory.setImageResource(imgId);
            }

            //holder.imageViewHistory.setImageResource(R.drawable.q5);


            holder.option1.setText(questions2.get(position).getOp1());
            holder.option2.setText(questions2.get(position).getOp2());
            holder.option3.setText(questions2.get(position).getOp3());
            holder.option4.setText(questions2.get(position).getOp4());
            holder.correctText.setText(questions2.get(position).getCorrectAns());;
            holder.explainationText.setText(questions2.get(position).getExplaination());
            int answer = Integer.parseInt(questions2.get(position).getCorrectAns());

            if (questions2.get(position).getOp1().equals("#")){
                holder.option2.setVisibility(View.GONE);
            }
            if (questions2.get(position).getOp2().equals("#")){
                holder.option1.setVisibility(View.GONE);
            }
            if (questions2.get(position).getOp3().equals("#")){
                holder.option3.setVisibility(View.GONE);
            }
            if (questions2.get(position).getOp4().equals("#")){
                holder.option4.setVisibility(View.GONE);
            }

            switch (answer) {
                case 1:
                    holder.correctText.setText("Correct Answer: " + holder.option1.getText());
                    break;
                case 2:
                    holder.correctText.setText("Correct Answer: " + holder.option2.getText());
                    break;
                case 3:
                    holder.correctText.setText("Correct Answer: " + holder.option3.getText());
                    break;
                case 4:
                    holder.correctText.setText("Correct Answer: " + holder.option4.getText());
                    break;
            }

        }

    }

    @Override
    public int getItemCount() {
        return questions2.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView questionText;
        TextView correctText;
        TextView explainationText;
        TextView option1;
        TextView option2;
        TextView option3;
        TextView option4;
        ImageView imageViewHistory;




        MyViewHolder(View view) {
            super(view);

            questionText = (TextView) view.findViewById(R.id.oneMarkQuestion);
            imageViewHistory = view.findViewById(R.id.imageViewHistory);

            correctText = (TextView) view.findViewById(R.id.correct_answer);
            explainationText = (TextView) view.findViewById(R.id.explaination);
            option1 = (TextView) view.findViewById(R.id.option1);
            option2 = (TextView) view.findViewById(R.id.option2);
            option3 = (TextView) view.findViewById(R.id.option3);
            option4 = (TextView) view.findViewById(R.id.option4);

        }
    }

}
