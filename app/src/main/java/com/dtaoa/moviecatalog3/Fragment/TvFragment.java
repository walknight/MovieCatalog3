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

import com.dtaoa.moviecatalog3.Adapter.DataAdapter;
import com.dtaoa.moviecatalog3.DetailActivity;
import com.dtaoa.moviecatalog3.R;
import com.dtaoa.moviecatalog3.ViewModel.DataModel;
import com.dtaoa.moviecatalog3.ViewModel.MainViewModel;

import java.util.ArrayList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvFragment extends Fragment {
    private RecyclerView rvTv;
    private ArrayList<DataModel> tvShows = new ArrayList<>();
    private DataAdapter adapter;
    private ProgressBar progressBar;
    private String Lang;

    private MainViewModel mainViewModel;

    public TvFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new DataAdapter();
        adapter.notifyDataSetChanged();
        progressBar = view.findViewById(R.id.progressBar);
        rvTv = view.findViewById(R.id.rv_fragment_tv);
        rvTv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTv.setAdapter(adapter);

        showLoading(true);
        Lang = Locale.getDefault().getLanguage().toLowerCase();

        mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);
        mainViewModel.setListMovies(getActivity(), Lang, "tv");
        mainViewModel.getMovies().observe(this, new Observer<ArrayList<DataModel>>() {
            @Override
            public void onChanged(@Nullable ArrayList<DataModel> tvShows) {
                if(tvShows != null){
                    adapter.setData(tvShows);
                    showLoading(false);
                }
            }
        });

        adapter.setOnItemClickCallback(new DataAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(DataModel data) {
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
