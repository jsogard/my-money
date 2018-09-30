package joesogard.mymoney.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import java.util.Calendar;

import joesogard.mymoney.R;
import joesogard.mymoney.TransactionDayFragment;
import joesogard.mymoney.model.TransactionModel;

public class TransactionHistoryActivity extends FragmentActivity
                implements  TransactionDayFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        TransactionModel.getTransactionData();
    }

    @Override
    public void onListFragmentInteraction(Calendar calendar) {


    }
}
