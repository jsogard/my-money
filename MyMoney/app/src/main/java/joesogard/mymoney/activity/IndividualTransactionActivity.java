package joesogard.mymoney.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import joesogard.mymoney.R;
import joesogard.mymoney.model.TransactionModel;

public class IndividualTransactionActivity extends AppCompatActivity {

    public static final String EXTRA_INT_TRANSACTION_ID = "EXTRA_INT_TRANSACTION_ID";
    private static final int DEFAULT_TRANSACTION_ID = -1;

    private TransactionModel transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_transaction);

        int transactionId = getIntent().getIntExtra(EXTRA_INT_TRANSACTION_ID, DEFAULT_TRANSACTION_ID);
        if(transactionId == DEFAULT_TRANSACTION_ID)
            throw new ExceptionInInitializerError();

        transaction = TransactionModel.TRANSACTION_MAP.get(transactionId);

        setViewUI();
    }

    private void setViewUI(){

        ((TextView)findViewById(R.id.transHeader)).setText(transaction.title);
        //((TextView)findViewById(R.id.transDate)).setText(transaction.date);
        ((TextView)findViewById(R.id.transAmount)).setText(Float.toString(transaction.amount));
        ((TextView)findViewById(R.id.transNotesText)).setText(transaction.description);
    }
}
