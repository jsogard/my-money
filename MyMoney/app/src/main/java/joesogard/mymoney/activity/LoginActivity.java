package joesogard.mymoney.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import joesogard.mymoney.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void OnClick_Login(View view) {
        Intent toMain = new Intent(LoginActivity.this, TransactionHistoryActivity.class);
        startActivity(toMain);
    }
}
