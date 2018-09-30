package joesogard.mymoney.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import java.util.Calendar;

import joesogard.mymoney.R;
import joesogard.mymoney.TransactionDayFragment;
import joesogard.mymoney.TransactionFragment;
import joesogard.mymoney.model.TransactionModel;

public class TransactionHistoryActivity extends FragmentActivity
                implements  TransactionFragment.OnListFragmentInteractionListener,
                            TransactionDayFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        TransactionModel.getTransactionData();
    }

    @Override
    public void onListFragmentInteraction(TransactionModel transactionModel) {
        Intent toIndividualTransaction = new Intent(
                TransactionHistoryActivity.this, IndividualTransactionActivity.class);
        toIndividualTransaction.putExtra(
                IndividualTransactionActivity.EXTRA_INT_TRANSACTION_ID, transactionModel.id);
        startActivity(toIndividualTransaction);
    }

    @Override
    public void onListFragmentInteraction(Calendar calendar) {


    }
}
