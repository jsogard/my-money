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

    /* UNIT TESTS */

    /**
     * When I sync transactions
     *  And there are new transactions
     * Then they are added to the Map
     */
    @Test
    public void syncTransactions_AddMultipleToMapSuccessful() {

        TransactionDataAccessor dataAccessor = mock(TransactionDataAccessor.class);
        int initialMapCount = dataAccessor.TRANSACTION_MAP.size(), addCount = 5;
        List<TransactionModel> fetchedList = generateTransactionList(addCount);
        when(dataAccessor.fetchTransactions(null)).thenReturn(fetchedList);
        doCallRealMethod().when(dataAccessor).syncTransactions(null);

        dataAccessor.syncTransactions(null);

        Assert.assertEquals(initialMapCount + addCount, dataAccessor.TRANSACTION_MAP.size());
        for (TransactionModel transaction :
                fetchedList) {
            Assert.assertTrue(dataAccessor.TRANSACTION_MAP.containsTransaction(transaction));
        }
    }

    /**
     * When I sync transactions
     *  And there are no new transactions
     * Then the map is unchanged
     */
    @Test
    public void syncTransactions_AddNoneToMapSuccessful(){

        TransactionDataAccessor dataAccessor = mock(TransactionDataAccessor.class);
        int initialMapCount = dataAccessor.TRANSACTION_MAP.size();
        when(dataAccessor.fetchTransactions(null)).thenReturn(new ArrayList<>());
        doCallRealMethod().when(dataAccessor).syncTransactions(null);

        dataAccessor.syncTransactions(null);

        Assert.assertEquals(initialMapCount, dataAccessor.TRANSACTION_MAP.size());
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
        Random random = new Random();
        int id = Math.abs(random.nextInt());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, random.nextInt(48)-24);
        return new TransactionModel(
                id, "Transaction_" + id, random.nextFloat(), calendar);
    }
}
