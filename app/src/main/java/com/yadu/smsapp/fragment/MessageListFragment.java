package com.yadu.smsapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yadu.smsapp.R;
import com.yadu.smsapp.database.MessageDatabse;
import com.yadu.smsapp.gettersetter.MessageGetterSetter;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnMessageListClickedListener}
 * interface.
 */
public class MessageListFragment extends Fragment {

    MessageDatabse db;
    ArrayList<MessageGetterSetter> msg_list = new ArrayList<>();

    private OnMessageListClickedListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MessageListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages_list, container, false);

        db = new MessageDatabse(getActivity().getApplicationContext());
        db.open();
        msg_list = db.getCityMer();

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            if(msg_list.size()>0){

                recyclerView.setLayoutManager(new LinearLayoutManager(context));

                recyclerView.setAdapter(new MyMessagesRecyclerViewAdapter(msg_list, mListener));
            }

        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMessageListClickedListener) {
            mListener = (OnMessageListClickedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnMessageListClickedListener {
        // TODO: Update argument type and name
        void onMessageListClicked(MessageGetterSetter item);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }
}
