package joesogard.mymoney;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class TransactionModel {

    public static HashMap<Integer, TransactionModel> TRANSACTION_MAP =
            new HashMap<>();
    public static List<TransactionModel> TRANSACTION_LIST = new ArrayList<>();

    public int id;
    public String title;
    public String description;
    public float amount;

    public TransactionModel(int id, String title, String description, float amount){
        this.id = id;
        this.title = title;
        this.description = description;
        this.amount = amount;
    }

    public static void AddTransaction(TransactionModel transactionModel){
        TRANSACTION_MAP.put(transactionModel.id, transactionModel);
    }

    static {
        TransactionModel transactionModel;
        Random random = new Random();
        for(int i = 0; i < 15; i++){
            transactionModel = new TransactionModel(
                    i, "TransactionModel_" + i,
                    "Lorem Ipsum Dolor",
                    (random.nextFloat() - 0.5f) * 100
            );
            TRANSACTION_LIST.add(transactionModel);
            TRANSACTION_MAP.put(transactionModel.id, transactionModel);
        }
    }

}
