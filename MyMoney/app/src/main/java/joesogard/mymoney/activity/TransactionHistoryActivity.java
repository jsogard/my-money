package joesogard.mymoney.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import java.util.Calendar;

import joesogard.mymoney.R;
import joesogard.mymoney.TransactionDataAccessor;
import joesogard.mymoney.TransactionDayFragment;
import joesogard.mymoney.model.TransactionModel;

public class TransactionHistoryActivity extends FragmentActivity
                implements  TransactionDayFragment.OnListFragmentInteractionListener {

    private TransactionDataAccessor transactionDataAccessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        transactionDataAccessor = new TransactionDataAccessor(this);
        transactionDataAccessor.syncTransactions(this);

        setContentView(R.layout.activity_transaction_history);
    }

    @Override
    public void onListFragmentInteraction(Calendar calendar) {

    }
}
