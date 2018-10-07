package joesogard.mymoney;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MyTransactionDayRecyclerViewAdapterTest {

    private MyTransactionDayRecyclerViewAdapter.ViewHolder viewHolder;

    @Before
    public void CreateViewHolder(){
        View view = mock(View.class);
        when(view.findViewById(R.id.transactionDayDate)).thenReturn(new TextView(null));
        when(view.findViewById(R.id.transactionsList)).thenReturn(new LinearLayout(null));
//        viewHolder = new MyTransactionDayRecyclerViewAdapter.ViewHolder(view);
    }

}
