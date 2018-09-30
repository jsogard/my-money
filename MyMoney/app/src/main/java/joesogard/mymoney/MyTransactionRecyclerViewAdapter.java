package joesogard.mymoney;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import joesogard.mymoney.TransactionFragment.OnListFragmentInteractionListener;
import joesogard.mymoney.dummy.DummyContent.Transaction;
import joesogard.mymoney.model.TransactionModel;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Transaction} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyTransactionRecyclerViewAdapter extends RecyclerView.Adapter<MyTransactionRecyclerViewAdapter.ViewHolder> {

    private final List<TransactionModel> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyTransactionRecyclerViewAdapter(List<TransactionModel> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_transaction, parent, false);
        return new ViewHolder(view);
    }

    /*
    How the recyclerview translates from data in a list to a fragment
    ViewHolder holds the fragment_transaction and sets the values for strings here
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mHeaderView.setText(mValues.get(position).title);
        holder.mContentView.setText(mValues.get(position).description);
        holder.mAmountView.setText(Float.toString(mValues.get(position).amount));

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

    /*
    Holds the view of the fragment
    Binds the fragments views to variables that can be set
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mHeaderView;
        public final TextView mContentView;
        public final TextView mAmountView;
        public TransactionModel mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mHeaderView = (TextView) view.findViewById(R.id.transactionHeader);
            mContentView = (TextView) view.findViewById(R.id.description);
            mAmountView = (TextView) view.findViewById(R.id.description);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
