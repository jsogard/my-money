package joesogard.mymoney;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import joesogard.mymoney.TransactionDayFragment.OnListFragmentInteractionListener;
import joesogard.mymoney.activity.IndividualTransactionActivity;
import joesogard.mymoney.activity.TransactionHistoryActivity;
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

    private final List<Calendar> mValues;
    private final OnListFragmentInteractionListener mListener;
    private TransactionDataAccessor transactionDataAccessor;

    public MyTransactionDayRecyclerViewAdapter(List<Calendar> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        transactionDataAccessor = new TransactionDataAccessor();
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
        date.add(Calendar.DAY_OF_MONTH, -1 - position);
        TransactionDayUtils.setStartOfDay(date);
        holder.mItem = date;
        if(holder.mTransactionViewList == null)
            holder.populateTransactionViewList();

        holder.populateTransactionLayout();
        holder.mDateView.setText(
                TransactionDayUtils.dateFormat.format(new Date(holder.mItem.getTimeInMillis())));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mDateView;
        public final LinearLayout mTransactionLayout;
        public List<TransactionView> mTransactionViewList = null;

        public Calendar mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mDateView = (TextView) view.findViewById(R.id.transactionDayDate);
            mTransactionLayout = (LinearLayout) view.findViewById(R.id.transactionsList);

        }

        public void populateTransactionViewList(){
            if(mItem == null)
                throw new ExceptionInInitializerError("Must initialize mItem.");

            mTransactionViewList = new ArrayList<TransactionView>();
            TransactionView transactionView;

            ListIterator<TransactionModel> transactionListIterator =
                    transactionDataAccessor.getTransactionListIterator();
            TransactionModel transactionModel;
            while(transactionListIterator.hasPrevious()){
                transactionModel = transactionListIterator.previous();
                if(TransactionDayUtils.isOnSameDay(transactionModel.date, mItem)) {

                    transactionView = new TransactionView(mView.getContext(), transactionModel);
                    mTransactionViewList.add(transactionView);
                }
                else if(transactionModel.date.before(mItem)){
                    break;
                }
            }
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
