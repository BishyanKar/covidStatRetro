package com.example.architectuecomp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.architectuecomp.model.Case;
import com.example.architectuecomp.model.Covid;
import com.example.architectuecomp.network.api.CovidApi;
import com.example.architectuecomp.reponses.GenericResponse;
import com.example.architectuecomp.reponses.Response;
import com.example.architectuecomp.viewmodel.CaseViewModel;
import com.example.architectuecomp.viewmodel.CasesAdapter;

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

        caseViewModel.getAllCases().observe(this, new Observer<List<Case>>() {
            @Override
            public void onChanged(List<Case> cases) {
                adapter.submitList(cases);
                adapter.setCases(cases);
            }
        });

        caseViewModel.getResponseList().observe(this, new Observer<GenericResponse>() {
            @Override
            public void onChanged(GenericResponse genericResponse) {
                switch (genericResponse.getClass().getSimpleName()){
                    case "EmptyResponse" :
                        Toast.makeText(MainActivity.this, "Empty Data returned", Toast.LENGTH_SHORT).show();
                        lottieAnimationView.setVisibility(View.INVISIBLE);
                        break;
                    case "FailedCodeResponse" :
                        String code = ((GenericResponse.FailedCodeResponse)genericResponse).getResCode();
                        Toast.makeText(MainActivity.this,code,Toast.LENGTH_SHORT).show();
                        lottieAnimationView.setVisibility(View.INVISIBLE);
                        break;
                    case "SuccessResponse" :
                        List<Response> responses = ((GenericResponse.SuccessResponse)genericResponse).getResponseList();
                        caseViewModel.deleteAll();
                        for(com.example.architectuecomp.reponses.Response res : responses){
                            Case aCase = new Case(res.getCountry(),res.getDay()+" | "+res.getTime(),res.getCases().getTotal()+"",res.getDeaths().getTotal()+"",0+"");
                            caseViewModel.insert(aCase);
                        }
                        lottieAnimationView.setVisibility(View.INVISIBLE);
                        break;
                    case "ErrorResponse" :
                        String err =  ((GenericResponse.ErrorResponse)genericResponse).getErr();
                        Toast.makeText(MainActivity.this,err,Toast.LENGTH_SHORT).show();
                        lottieAnimationView.setVisibility(View.INVISIBLE);
                        break;
                }
            }
        });
        //fetchData();

    }
    public void fetchData()
    {
        caseViewModel.fetchDataOnline(MainActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        MenuItem update = menu.findItem(R.id.sync);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setIconified(false);
        searchView.setFocusable(true);
        searchView.requestFocusFromTouch();
        searchView.setQueryHint("Search your country...");
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
                fetchData();
                return false;
            }
        });

        return true;
    }
}