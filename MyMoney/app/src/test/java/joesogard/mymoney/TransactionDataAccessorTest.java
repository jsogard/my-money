package joesogard.mymoney;

import android.transition.TransitionManager;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import joesogard.mymoney.model.TransactionModel;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransactionDataAccessorTest {

    private static final Random RANDOM = new Random();

    private TransactionDataAccessor dataAccessor;

    @Before
    public void doRealMethodsByDefault() throws JSONException {
        dataAccessor = mock(TransactionDataAccessor.class);
        when(dataAccessor.fetchTransactions()).thenCallRealMethod();
        when(dataAccessor.getTransaction(anyLong())).thenCallRealMethod();
        doCallRealMethod().when(dataAccessor).syncTransactions();
    }

    /* UNIT TESTS */

    /**
     * When I fetch new transactions
     *  And there are new transactions
     * Then they are added to the Map
     */
    @Test
    public void syncTransactions_AddMultipleToMap() throws JSONException {

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

    /**
     * When I fetch new transactions
     *  And there are new transactions
     * Then they are added to the list by date
     */
    @Test
    public void syncTransactions_AddMultipleToList() throws JSONException {

        int initialListCount = dataAccessor.TRANSACTION_LIST.size(), addCount = 5;
        List<TransactionModel> fetchedList = generateTransactionList(addCount);
        when(dataAccessor.fetchTransactions()).thenReturn(fetchedList);

        dataAccessor.syncTransactions();

        Assert.assertEquals(initialListCount + addCount, dataAccessor.TRANSACTION_LIST.size());
        Assert.assertTrue(dataAccessor.TRANSACTION_LIST.containsAll(fetchedList));
        Assert.assertTrue(isSortedByDate(dataAccessor.TRANSACTION_LIST));
    }

    /**
     * As a developer
     * When I test the app with sample data
     * Then the sample data is parsed correctly
     */
    @Test
    public void fetchTransactions_SampleDataIsValid(){

        List<TransactionModel> list = dataAccessor.fetchTransactions();
        Assert.assertTrue(list.size() > 0);
    }

    /* HELPER METHODS */

    private List<TransactionModel> generateTransactionList(int count){
        List<TransactionModel> list = new ArrayList<>(count);
        for(int i = 0; i < count; i++){
            list.add(generateTransaction());
        }
        return list;
    }

    private TransactionModel generateTransaction(){
        int id = Math.abs(RANDOM.nextInt());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, RANDOM.nextInt(48)-24);
        return new TransactionModel(
                id, "Transaction_" + id, RANDOM.nextFloat(), calendar);
    }

    private boolean isSortedByDate(List<TransactionModel> list){
        TransactionModel prev = null;
        for (TransactionModel transaction :
                list) {
            if(prev != null && prev.date.after(transaction.date))
                return false;
            prev = transaction;
        }
        return true;
    }
}
