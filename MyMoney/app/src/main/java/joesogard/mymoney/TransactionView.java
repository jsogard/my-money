package joesogard.mymoney;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import joesogard.mymoney.activity.IndividualTransactionActivity;
import joesogard.mymoney.model.TransactionModel;

public class TransactionView extends ConstraintLayout {

    private final TextView headerText;
    private final TextView balanceText;
    public final TransactionModel transactionModel;


    public TransactionView(Context context, TransactionModel transactionModel){
        super(context);

        inflate(getContext(), R.layout.transaction_item, this);
        headerText = (TextView)findViewById(R.id.transactionHeader);
        balanceText = (TextView)findViewById(R.id.transactionBalance);
        this.transactionModel = transactionModel;

        headerText.setText(transactionModel.title);
        balanceText.setText(Float.toString(transactionModel.balance));
        int rColor = transactionModel.balance < 0 ? R.color.colorLoss : R.color.colorProfit;
        balanceText.setTextColor(getResources().getColor(rColor, null));
        setLayoutParams(
                new ConstraintLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id = ((TransactionView)v).transactionModel.id;
                Intent toIndividualTransaction = new Intent(v.getContext(), IndividualTransactionActivity.class);
                toIndividualTransaction.putExtra(IndividualTransactionActivity.EXTRA_INT_TRANSACTION_ID, id);
                v.getContext().startActivity(toIndividualTransaction);
            }
        });
    }



}
