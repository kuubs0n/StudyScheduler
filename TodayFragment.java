package com.studyscheduler.kuubs0n.studyscheduler;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studyscheduler.kuubs0n.studyscheduler.Adapters.TodayAdapter;
import com.studyscheduler.kuubs0n.studyscheduler.Models.ItemModel;
import com.studyscheduler.kuubs0n.studyscheduler.Models.TodayModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class TodayFragment extends Fragment {

    private TextView _tv;
    private ListView _lvToday;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Calendar c = Calendar.getInstance();

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = db.getReferenceFromUrl("https://studyscheduler-9300d.firebaseio.com/pins/1234/Days/1");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, HashMap<String, String>> map = (HashMap<String, HashMap<String, String>>) dataSnapshot.getValue();
                prepareListView(map);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_today, container, false);
        return view;
    }

    private void prepareListView(HashMap<String, HashMap<String, String>> values){
        TodayModel[] todays = new  TodayModel[values.size()];
        Set<String> keys = values.keySet();
        List<String> sortedKeys = asSortedList(keys);
        int count = 0;
        for(String key : sortedKeys){
            HashMap<String, String> map = values.get(key);
            TodayModel tm = new TodayModel();
            for(String mapKey : map.keySet()){
                if(mapKey.equals("IsLab")) {
                    if(map.get(mapKey).equals("true"))
                        tm.IsLab = true;
                    else
                        tm.IsLab = false;
                }
                if(mapKey.equals("Place"))
                    tm.Place = map.get(mapKey);
                if(mapKey.equals("Prof"))
                    tm.Professor = map.get(mapKey);
                if(mapKey.equals("Name")){
                    tm.Name = map.get(mapKey);
                }
                tm.Times = key;
            }

            todays[count] = tm;
            count++;
        }

        View view = getView();
        _lvToday = view.findViewById(R.id.lvToday);

        TodayAdapter ta = new TodayAdapter(getActivity(), todays);
        _lvToday.setAdapter(ta);
    }
    public static
    <T extends Comparable<? super T>> List<T> asSortedList(Collection<T> c) {
        List<T> list = new ArrayList<T>(c);
        java.util.Collections.sort(list);
        return list;
    }
}