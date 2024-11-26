package com.rohan.passwordgenerator;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Second extends Fragment {
    SearchView searchView;
    ArrayList<passwordManager> arrCin;
    PasswordAdapter adapter;


    public Second() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_second, container, false);
        searchView = view.findViewById(R.id.search);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
        Mydb data = new Mydb(getContext());
        RecyclerView recyclerView = view.findViewById(R.id.recyclear);
        arrCin =  data.fetch();
      adapter = new PasswordAdapter(requireContext(),arrCin);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(adapter);
        for (int i =0;i<arrCin.size();i++){
            Log.d("Passwords", "name :"+arrCin.get(i).name+", password: "+arrCin.get(i).Pass);

        }

        return view;
    }

    private void filterList(String text) {
        List<passwordManager> filteredList = new ArrayList<>();
        for (passwordManager item:arrCin){
            if(item.name.toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()){
            Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
        }else{
            adapter.setfilteredList(filteredList);

        }
    }
}