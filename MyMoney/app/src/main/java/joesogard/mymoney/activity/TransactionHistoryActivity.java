package joesogard.mymoney.activity;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;

import joesogard.mymoney.R;
import joesogard.mymoney.TransactionFragment;
import joesogard.mymoney.TransactionModel;
import joesogard.mymoney.dummy.DummyContent;

public class TransactionHistoryActivity extends FragmentActivity
                implements TransactionFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);
    }

    @Override
    public void onListFragmentInteraction(TransactionModel item) {

    }
}
