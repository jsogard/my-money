package joesogard.mymoney;

import java.util.HashMap;
import java.util.List;

import joesogard.mymoney.model.TransactionModel;

public class TransactionDataAccessor {
    protected static HashMap<Integer, TransactionModel> TRANSACTION_MAP = new HashMap<>();

    public TransactionDataAccessor() {
    }

    protected List<TransactionModel> fetchTransactions() {
        return null;
    }

    public void syncTransactions() {
        List<TransactionModel> list = fetchTransactions();
        for (TransactionModel transaction :
                list) {
            TRANSACTION_MAP.put(transaction.id, transaction);
        }
    }

    public TransactionModel getTransaction(int id){
        return TRANSACTION_MAP.get(id);
    }
}
