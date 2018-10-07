package joesogard.mymoney;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import joesogard.mymoney.TransactionDayFragment.OnListFragmentInteractionListener;
import joesogard.mymoney.model.TransactionModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

/**
 * {@link RecyclerView.Adapter} that can display a  and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyTransactionDayRecyclerViewAdapter extends RecyclerView.Adapter<MyTransactionDayRecyclerViewAdapter.ViewHolder> {

    private final OnListFragmentInteractionListener mListener;
    private final TransactionDataAccessor transactionDataAccessor;

    public MyTransactionDayRecyclerViewAdapter(OnListFragmentInteractionListener listener) {
        setHasStableIds(true);
        mListener = listener;
        transactionDataAccessor = new TransactionDataAccessor();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_transactionday, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Calendar date = (Calendar)TransactionDayUtils.getToday().clone();
        date.add(Calendar.DAY_OF_MONTH, -position);
        TransactionDayUtils.setStartOfDay(date);
        holder.mDate = date;
        holder.populateTransactionViewList();

        holder.populateTransactionLayout();
        holder.mDateView.setText(
                TransactionDayUtils.dateFormat.format(new Date(holder.mDate.getTimeInMillis())));

        holder.mView.setOnClickListener(v -> {
            if (null != mListener) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                mListener.onListFragmentInteraction(holder.mDate);
            }
        });
    }

    @Override
    public int getItemCount() {
        return TransactionDayUtils.getToday().get(Calendar.DAY_OF_MONTH);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mDateView;
        public final LinearLayout mTransactionLayout;
        public final List<TransactionView> mTransactionViewList;

        public Calendar mDate;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mDateView = (TextView) view.findViewById(R.id.transactionDayDate);
            mTransactionLayout = (LinearLayout) view.findViewById(R.id.transactionsList);
            mTransactionViewList = new ArrayList<>();
        }

        public void populateTransactionViewList(){
            if(mDate == null)
                throw new ExceptionInInitializerError("Must initialize mDate.");

            List<TransactionModel> todayTransactions = transactionDataAccessor.getTransactionsByDay(mDate);
            for (TransactionModel transactionModel:
                 todayTransactions) {
                if(!transactionViewListContainsModel(transactionModel))
                    mTransactionViewList.add(new TransactionView(mView.getContext(), transactionModel));
            }
        }

        private boolean transactionViewListContainsModel(TransactionModel transactionModel){
            for (TransactionView transactionView:
                 mTransactionViewList) {
                if(transactionView.transactionModel == transactionModel)
                    return true;
            }
            return false;
        }

        public void populateTransactionLayout(){
            mTransactionLayout.removeAllViewsInLayout();
            for (TransactionView transactionView :
                    mTransactionViewList) {
                mTransactionLayout.addView(transactionView);
            }
        }

        @Override
        public String toString() {
            return mDateView.getText().toString();
        }
    }
}
