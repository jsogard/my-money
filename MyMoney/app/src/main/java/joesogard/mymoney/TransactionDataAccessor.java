package joesogard.mymoney;

import android.content.Context;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

import joesogard.mymoney.model.TransactionModel;

public class TransactionDataAccessor {
    protected static HashMap<Long, TransactionModel> TRANSACTION_MAP = new HashMap<>();
    protected static List<TransactionModel> TRANSACTION_LIST = new ArrayList<>();

    private Context context;

    public TransactionDataAccessor() {
    }

    public TransactionDataAccessor(Context context){
        this.context = context;
    }

    protected List<TransactionModel> fetchTransactions(Context context) {
        List<TransactionModel> fetchedList = new ArrayList<>();
        if(Consts.Debug.FAKE_TRANSACTION_DATA){

            JSONArray jsonArray = readJSONFile(context, Consts.Debug.TRANSACTION_FILE_NAME);
            jsonArray.forEach(
                    (jsonObject) -> {
                        try {
                            fetchedList.add(new TransactionModel((org.json.simple.JSONObject)jsonObject));
                        } catch (java.text.ParseException e) {
                            e.printStackTrace();
                        }
                    });
            return fetchedList;
        }

        return null;
    }

    private JSONArray readJSONFile(Context context, String fileName){
        try{
            InputStream inputStream = context.getAssets().open(fileName);
            JSONArray object = (JSONArray)new JSONParser().parse(new InputStreamReader(inputStream, "UTF-8"));
            return (JSONArray) object;
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        } catch(ParseException e){
            e.printStackTrace();
        }
        return null;
    }

    public void syncTransactions() {
        List<TransactionModel> list = fetchTransactions(context);
        for (TransactionModel transaction :
                list) {
            TRANSACTION_MAP.put(transaction.id, transaction);
//            insertTransactionByDate(transaction);
        }
        TRANSACTION_LIST.addAll(list);
        TRANSACTION_LIST.sort((o1, o2) -> o1.date.compareTo(o2.date));
    }

    public TransactionModel getTransaction(long id){
        return TRANSACTION_MAP.get(id);
    }

    public ListIterator<TransactionModel> getTransactionListIterator(){
        if(TRANSACTION_LIST.size() == 0)
            return null;
        return TRANSACTION_LIST.listIterator(TRANSACTION_LIST.size());
    }

    private void insertTransactionByDate(TransactionModel transaction){
        Calendar before = null, after = null;
        for(int index = TRANSACTION_LIST.size()-1; index >= 0; index--){
            after = before;
            before = TRANSACTION_LIST.get(index).date;
            if(transaction.date.after(before) && (after == null || transaction.date.before(after)) ){
                TRANSACTION_LIST.add(index + 1, transaction);
                return;
            }
        }
        TRANSACTION_LIST.add(0, transaction);
    }
}
