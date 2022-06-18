package com.geektech.repeat53;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.geektech.repeat53.adapter.ImageAdapter;
import com.geektech.repeat53.databinding.ActivityMainBinding;
import com.geektech.repeat53.network.RetrofitService;
import com.geektech.repeat53.network.model.Hit;
import com.geektech.repeat53.network.model.PixabayModel;



import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    RetrofitService retrofitService;
    private String KEY = "27723662-43b1a48fa73b0ddcb14cd5408";
    private ImageAdapter adapter;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        retrofitService = new RetrofitService();

        initClickers();
        binding.swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page++;
                String word = binding.etWord.getText().toString();
                getImageFromApi(word, 10, page);
                binding.swipeLayout.setRefreshing(false);
            }
        });


    }

    private void initClickers() {
        binding.btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                page = 1;
                String word = binding.etWord.getText().toString();
                getImageFromApi(word, 10, page);
            }
        });

        binding.btnPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                page++;
                String word = binding.etWord.getText().toString();
                getImageFromApi(word, 10, page);
            }
        });


    }

    private void getImageFromApi(String word, int per_page, int page) {
        retrofitService.getApi().getImages(KEY, word, per_page, page).enqueue(new Callback<PixabayModel>() {
            @Override
            public void onResponse(Call<PixabayModel> call, Response<PixabayModel> response) {
                if (response.isSuccessful()){
                    Log.e("ololo","onResponse: " + response.body().getHits().get(0).getLargeImageURL());
                    adapter = new ImageAdapter((ArrayList<Hit>) response.body().getHits());
                    binding.recycler.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<PixabayModel> call, Throwable t) {
                Log.e("ololo","onFailure: " + t.getLocalizedMessage());
            }
        });
    }
}