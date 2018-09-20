package joesogard.mymoney.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import joesogard.mymoney.R;
import joesogard.mymoney.TransactionFragment;
import joesogard.mymoney.model.TransactionModel;

public class TransactionHistoryActivity extends FragmentActivity
                implements TransactionFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);
    }

    @Override
    public void onListFragmentInteraction(TransactionModel transactionModel) {
        Intent toIndividualTransaction = new Intent(
                TransactionHistoryActivity.this, IndividualTransactionActivity.class);
        toIndividualTransaction.putExtra(
                IndividualTransactionActivity.EXTRA_INT_TRANSACTION_ID, transactionModel.id);
        startActivity(toIndividualTransaction);
    }
}
