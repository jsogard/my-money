package joesogard.mymoney;

import android.content.Context;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import joesogard.mymoney.model.TransactionMap;
import joesogard.mymoney.model.TransactionModel;

public class TransactionDataAccessor {
    protected static TransactionMap TRANSACTION_MAP = new TransactionMap();

    private Context context;

    public TransactionDataAccessor() {
    }

    public TransactionDataAccessor(Context context){
        this.context = context;
    }

    protected List<TransactionModel> fetchTransactions(Context context) {

        if(Consts.Debug.FAKE_TRANSACTION_DATA){
            return requestTransactionsLocal(context);
        }

        return null;
    }

    /**
     *
     */
    public void syncTransactions(Context context) {
        List<TransactionModel> fetch = fetchTransactions(context), newValue;
        int dateKey;
        boolean exists;

        for (TransactionModel transaction :
                fetch) {
            dateKey = transaction.date.get(Calendar.DAY_OF_MONTH);
            exists = TRANSACTION_MAP.containsKey(dateKey);
            newValue = TRANSACTION_MAP.getOrDefault(dateKey, new ArrayList<>());
            newValue.add(transaction);
            if(exists)
                TRANSACTION_MAP.replace(dateKey, newValue);
            else
                TRANSACTION_MAP.put(dateKey, newValue);
        }
    }

    public List<TransactionModel> getTransactionsByDay(Calendar mDate) {
        return TRANSACTION_MAP.getOrDefault(
                mDate.get(Calendar.DAY_OF_MONTH), new ArrayList<>());
    }

    private List<TransactionModel> requestTransactionsLocal(Context context){
        try{
            List<TransactionModel> fetchedList = new ArrayList<>();
            InputStream inputStream = context.getAssets().open(Consts.Debug.TRANSACTION_FILE_NAME);
            JSONArray transactionData = (JSONArray)new JSONParser().parse(new InputStreamReader(inputStream, "UTF-8"));

            transactionData.forEach(
                    (jsonObject) -> {
                        try {
                            fetchedList.add(new TransactionModel((org.json.simple.JSONObject)jsonObject));
                        } catch (java.text.ParseException e) {
                            e.printStackTrace();
                        }
                    });
            return fetchedList;
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        } catch(ParseException e){
            e.printStackTrace();
        }
        return null;
    }
}
