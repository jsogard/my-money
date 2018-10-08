package joesogard.mymoney.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class TransactionMap extends HashMap<Integer, List<TransactionModel>> {
    
    @Override
    public int size() {
        int count = 0;
        for (List value :
                values()) {
            count += value.size();
        }
        return count;
    }

    public boolean containsTransaction(TransactionModel value) {
        int key = value.date.get(Calendar.DAY_OF_MONTH);
        List<TransactionModel> dayTransactions = get(key);
        return dayTransactions != null && dayTransactions.contains(value);
    }

    public void put(TransactionModel value) {
        int key = value.date.get(Calendar.DAY_OF_MONTH);
        if(!containsKey(key)){
            put(key, new ArrayList<>());
        }
        get(key).add(value);
    }
}
