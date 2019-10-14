package com.dtaoa.moviecatalog3.Fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dtaoa.moviecatalog3.Adapter.DataAdapter;
import com.dtaoa.moviecatalog3.DetailActivity;
import com.dtaoa.moviecatalog3.ViewModel.DataModel;
import com.dtaoa.moviecatalog3.ViewModel.MainViewModel;
import com.dtaoa.moviecatalog3.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {
    private RecyclerView rvMovies;
    private ArrayList<DataModel> movies = new ArrayList<>();
    private DataAdapter adapter;
    private ProgressBar progressBar;
    private String Lang;

    private MainViewModel mainViewModel;

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new DataAdapter();
        adapter.notifyDataSetChanged();
        progressBar = view.findViewById(R.id.progressBar);
        rvMovies = view.findViewById(R.id.rv_fragment_movie);
        rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovies.setAdapter(adapter);

        showLoading(true);
        Lang = Locale.getDefault().getLanguage().toLowerCase();

        mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);
        mainViewModel.setListMovies(getActivity(), Lang, "movie");
        mainViewModel.getMovies().observe(this, new Observer<ArrayList<DataModel>>() {
            @Override
            public void onChanged(@Nullable ArrayList<DataModel> movies) {
                if(movies != null){
                    adapter.setData(movies);
                    showLoading(false);
                }
            }
        });

       adapter.setOnItemClickCallback(new DataAdapter.OnItemClickCallback() {
           @Override
           public void onItemClicked(DataModel data) {
               //Toast.makeText(getContext(), "Anda Memilih Judul " + data.getTitle(), Toast.LENGTH_SHORT).show();
               showDetail(data);
           }
       });


    }

    private void showLoading(Boolean state){
        if(state){
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showDetail(DataModel data){
        Intent detailIntent = new Intent(getActivity(), DetailActivity.class);
        detailIntent.putExtra(DetailActivity.EXTRA_DATA, data);
        startActivity(detailIntent);
    }


}
