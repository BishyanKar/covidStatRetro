package com.example.architectuecomp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView userList;
    private RecyclerView recyclerView;
    private CaseViewModel caseViewModel;
    private CasesAdapter adapter;
    private LottieAnimationView lottieAnimationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        lottieAnimationView = findViewById(R.id.animation_view);
        caseViewModel = new ViewModelProvider(this).get(CaseViewModel.class);
        adapter = new CasesAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        caseViewModel.getAllCases().observe(this, new Observer<PagedList<Case>>() {
            @Override
            public void onChanged(PagedList<Case> cases) {
                adapter.setCases(cases);
                adapter.submitList(cases);
                adapter.setActivity(MainActivity.this);
                adapter.setAnimationView(lottieAnimationView);
            }
        });

        //fetchData();

    }
    public void fetchData(final int index)
    {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public okhttp3.Response intercept(@NotNull Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        Request newRequest = originalRequest.newBuilder()
                                .addHeader("x-rapidapi-host", "covid-193.p.rapidapi.com")
                                .addHeader("x-rapidapi-key", "PLACE_YOUR_OWN_API_KEY")
                                .build();

                        return chain.proceed(newRequest);
                    }
                })
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://covid-193.p.rapidapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        CovidApi covidApi = retrofit.create(CovidApi.class);
        Call<Covid> call = covidApi.getCovidData();
        call.enqueue(new Callback<Covid>() {
            @Override
            public void onResponse(Call<Covid> call, Response<Covid> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, ""+response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if(response.body()==null){
                    Toast.makeText(MainActivity.this, "Unable to fetch Data", Toast.LENGTH_SHORT).show();
                    return;
                }
                final List<com.example.architectuecomp.Response> responses = response.body().getResponse();
                if(index == 0){
                    caseViewModel.deleteAll();
                    Log.d("MAIN", "onResponse: fetching new data");
                }
                else {
                    Log.d("MAIN", "onResponse: fetching next cluster of data");
                }
                for(int i = index; i < Math.min(responses.size(),index + 30); i++){
                    com.example.architectuecomp.Response res = responses.get(i);
                    Case aCase = new Case(res.getCountry(),res.getDay()+" | "+res.getTime(),res.getCases().getTotal()+"",res.getDeaths().getTotal()+"",0+"");
                    caseViewModel.insert(aCase);
                }
                lottieAnimationView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<Covid> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        MenuItem update = menu.findItem(R.id.sync);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });

        update.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                lottieAnimationView.setVisibility(View.VISIBLE);
                fetchData(0);
                return false;
            }
        });

        return true;
    }
}
