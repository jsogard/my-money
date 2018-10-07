package joesogard.mymoney;

import android.transition.TransitionManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import joesogard.mymoney.model.TransactionModel;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransactionDataAccessorTest {

    private static final Random RANDOM = new Random();

    private TransactionDataAccessor dataAccessor;

    @Before
    public void doRealMethodsByDefault() {
        dataAccessor = mock(TransactionDataAccessor.class);
//        when(dataAccessor.fetchTransactions(null)).thenCallRealMethod();
//        when(dataAccessor.getTransaction(anyLong())).thenCallRealMethod();
//        doCallRealMethod().when(dataAccessor).syncTransactions();
    }

    /* UNIT TESTS */

    /**
     * When I fetch new transactions
     *  And there are new transactions
     * Then they are added to the Map
     */
    @Test
    public void syncTransactions_AddMultipleToMap() {

        int initialMapCount = getTransactionMapSize(), addCount = 5;
        List<TransactionModel> fetchedList = generateTransactionList(addCount);
        when(dataAccessor.fetchTransactions(null)).thenReturn(fetchedList);
        doCallRealMethod().when(dataAccessor).syncTransactions();

        dataAccessor.syncTransactions();

        Assert.assertEquals(initialMapCount + addCount, getTransactionMapSize());
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
    public void syncTransactions_AddMultipleToList() {

        int initialListCount = dataAccessor.__TRANSACTION_LIST.size(), addCount = 5;
        List<TransactionModel> fetchedList = generateTransactionList(addCount);
        when(dataAccessor.fetchTransactions(null)).thenReturn(fetchedList);
        doCallRealMethod().when(dataAccessor).syncTransactions();

        dataAccessor.syncTransactions();

        Assert.assertEquals(initialListCount + addCount, dataAccessor.__TRANSACTION_LIST.size());
        Assert.assertTrue(dataAccessor.__TRANSACTION_LIST.containsAll(fetchedList));
        Assert.assertTrue(isSortedByDate(dataAccessor.__TRANSACTION_LIST));
    }

    /**
     * As a developer
     * When I test the app with sample data
     * Then the sample data is parsed correctly
     */
    @Test
    public void fetchTransactions_SampleDataIsValid(){

        List<TransactionModel> list = dataAccessor.fetchTransactions(null);
        Assert.assertTrue(list.size() > 0);
    }

    @Test
    public void getTransactionsByDay_OneTransactionReturnList(){

        TransactionModel latestTransactionModel = null;
        Calendar latestDate = Calendar.getInstance();
        for (int i = 0; i < 5; i++) {
            latestDate.add(Calendar.DAY_OF_MONTH, 1);
            latestTransactionModel = new TransactionModel(
                    i, "Trans_" + i,
                    new Float(i), (Calendar) latestDate.clone()
            );
            dataAccessor.__TRANSACTION_LIST.add(latestTransactionModel);
        }
        when(dataAccessor.getTransactionsByDay(latestDate)).thenCallRealMethod();

        List<TransactionModel> list = dataAccessor.getTransactionsByDay(latestDate);

        Assert.assertEquals(1, list.size());
        Assert.assertEquals(latestTransactionModel, list.get(0));
    }

    @Test
    public void getTransactionsByDay_NoTransactionsReturnEmpty(){

        Calendar baseDate = Calendar.getInstance(), date, missingDate = null;
        for (int i = 0; i < 5; i++) {
            date = (Calendar) baseDate.clone();
            date.add(Calendar.DAY_OF_MONTH, i);

            if(i == 3) {
                missingDate = date;
                continue;
            }

            dataAccessor.__TRANSACTION_LIST.add(new TransactionModel(
                    i, "Trans_" + i,
                    new Float(i), (Calendar) baseDate.clone()
            ));
        }
        when(dataAccessor.getTransactionsByDay(baseDate)).thenCallRealMethod();

        List<TransactionModel> list = dataAccessor.getTransactionsByDay(missingDate);

        Assert.assertEquals(0, list.size());
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

    private int getTransactionMapSize(){
        int count = 0;
        for (List<TransactionModel> list:
                dataAccessor.TRANSACTION_MAP.values()) {
            count += list.size();
        }
        return count;
    }
}
