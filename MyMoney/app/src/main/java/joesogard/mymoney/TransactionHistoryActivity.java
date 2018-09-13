package joesogard.mymoney;

import android.os.Bundle;
import android.app.Activity;

import joesogard.mymoney.dummy.DummyContent;

public class TransactionHistoryActivity extends Activity
                implements TransactionFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);
    }

    @Override
    public void onListFragmentInteraction(DummyContent.Transaction item) {

    }
}
