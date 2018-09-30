package joesogard.mymoney;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import joesogard.mymoney.model.TransactionModel;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransactionDataAccessorTest {

    private static final int ID = 12345;
    private static final String TITLE = "Transaction";
    private static final float POSITIVE_BALANCE = 123.45f;
    private static final float NEGATIVE_BALANCE = -543.21f;
    private static final Calendar TODAY = Calendar.getInstance();

    private TransactionDataAccessor dataAccessor;

    @Before
    public void doRealMethodsByDefault(){
        dataAccessor = mock(TransactionDataAccessor.class);
        when(dataAccessor.fetchTransactions()).thenCallRealMethod();
        when(dataAccessor.getTransaction(anyInt())).thenCallRealMethod();
        doCallRealMethod().when(dataAccessor).syncTransactions();
    }

    /**
     * When I fetch new transactions
     *  And there are new transactions
     * Then they are added to the Map
     */
    @Test
    public void fetchTransactions_AddMultipleToMap(){

        int initialMapCount = dataAccessor.TRANSACTION_MAP.size(), addCount = 5;
        List<TransactionModel> fetchedList = generateTransactionList(addCount);
        when(dataAccessor.fetchTransactions()).thenReturn(fetchedList);

        dataAccessor.syncTransactions();

        Assert.assertEquals(initialMapCount + addCount, dataAccessor.TRANSACTION_MAP.size());
        for (TransactionModel transaction :
                fetchedList) {
            Assert.assertNotEquals(null, dataAccessor.getTransaction(transaction.id));
        }
    }


    private List<TransactionModel> generateTransactionList(int count){
        List<TransactionModel> list = new ArrayList<TransactionModel>(count);
        int id;
        Calendar date;
        for(int i = 0; i < count; i++){
            id = ID + i;
            date = (Calendar)TODAY.clone();
            date.add(Calendar.MINUTE, i);
            list.add(new TransactionModel(id, TITLE + id, POSITIVE_BALANCE, date));
        }
        return list;
    }
}
