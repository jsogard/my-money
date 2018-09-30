package joesogard.mymoney;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.ContentFrameLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import joesogard.mymoney.activity.IndividualTransactionActivity;
import joesogard.mymoney.activity.TransactionHistoryActivity;
import joesogard.mymoney.model.TransactionModel;

public class TransactionView extends ConstraintLayout {

    private final TextView headerText;
    private final TextView balanceText;
    public final TransactionModel transactionModel;

    public TransactionView(Context context, TransactionModel transactionModel){
        super(context);

        inflate(getContext(), R.layout.fragment_transaction, this);
        headerText = (TextView)findViewById(R.id.transactionHeader);
        balanceText = (TextView)findViewById(R.id.transactionBalance);
        this.transactionModel = transactionModel;

        headerText.setText(transactionModel.title);
        balanceText.setText(Float.toString(transactionModel.amount));
        int rColor = transactionModel.amount < 0 ? R.color.colorLoss : R.color.colorProfit;
        balanceText.setTextColor(getResources().getColor(rColor, null));
        setLayoutParams(
                new ConstraintLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = ((TransactionView)v).transactionModel.id;
                Intent toIndividualTransaction = new Intent(v.getContext(), IndividualTransactionActivity.class);
                toIndividualTransaction.putExtra(IndividualTransactionActivity.EXTRA_INT_TRANSACTION_ID, id);
                v.getContext().startActivity(toIndividualTransaction);
            }
        });
    }



}
