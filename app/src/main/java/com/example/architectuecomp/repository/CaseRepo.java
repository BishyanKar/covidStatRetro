package com.example.architectuecomp.repository;

import android.app.Application;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.example.architectuecomp.MainActivity;
import com.example.architectuecomp.model.Case;
import com.example.architectuecomp.db.CaseDao;
import com.example.architectuecomp.db.Database;
import com.example.architectuecomp.model.Covid;
import com.example.architectuecomp.network.api.ApiProvider;
import com.example.architectuecomp.reponses.GenericResponse;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CaseRepo {
    private CaseDao caseDao;
    private LiveData<List<Case>> allCases;
    private MutableLiveData<GenericResponse> responseList = new MutableLiveData<>();

    public CaseRepo(Application application) {
        Database database = Database.getInstance(application);
        caseDao = database.caseDao();
        allCases = caseDao.getAllCases();
    }

    public LiveData<List<Case>> getAllCases()
    {
        return allCases;
    }
    public void insert(Case aCase)
    {
        new Thread(new InsertCaseBackgroundTask(caseDao,aCase)).start();
    }
    public void update(Case aCase)
    {
        new Thread(new UpdateCaseBackgroundTask(caseDao,aCase)).start();
    }
    public void delete(Case aCase)
    {
        new Thread(new DeleteCaseBackgroundTask(caseDao,aCase)).start();
    }
    public void deleteAll()
    {
        new Thread(new DeleteAllBackgroundTask(caseDao)).start();
    }

    public MutableLiveData<GenericResponse> getReponseList(){
        return responseList;
    }

    public void fetchDataOnline(Context context){

        Call<Covid> call = ApiProvider.getInstance().provide().getCovidData();
        call.enqueue(new Callback<Covid>() {
            @Override
            public void onResponse(Call<Covid> call, Response<Covid> response) {
                if(!response.isSuccessful()){
                    //Toast.makeText(context, ""+response.code(), Toast.LENGTH_SHORT).show();
                    responseList.postValue(GenericResponse.create(response.code()+""));
                    return;
                }
                if(response.body()==null){
                    //Toast.makeText(context, "Unable to fetch Data", Toast.LENGTH_SHORT).show();
                    responseList.postValue(GenericResponse.create());
                    return;
                }
                final List<com.example.architectuecomp.reponses.Response> responses = response.body().getResponse();
                //caseViewModel.deleteAll();
                /*for(com.example.architectuecomp.reponses.Response res : responses){
                    Case aCase = new Case(res.getCountry(),res.getDay()+" | "+res.getTime(),res.getCases().getTotal()+"",res.getDeaths().getTotal()+"",0+"");
                    caseViewModel.insert(aCase);
                }
                lottieAnimationView.setVisibility(View.INVISIBLE);*/
                responseList.postValue(GenericResponse.create(responses));
            }

            @Override
            public void onFailure(Call<Covid> call, Throwable t) {
                //Toast.makeText(context, "Request Failed", Toast.LENGTH_SHORT).show();
                responseList.postValue(GenericResponse.create(t));
            }
        });
    }

    static class InsertCaseBackgroundTask implements Runnable{

        private CaseDao caseDao;
        private Case aCase;

        public InsertCaseBackgroundTask(CaseDao caseDao,Case aCase) {
            this.caseDao = caseDao;
            this.aCase = aCase;
        }

        @Override
        public void run() {
            caseDao.insert(aCase);
        }
    }
    static class UpdateCaseBackgroundTask implements Runnable{

        private CaseDao caseDao;
        private Case aCase;

        public UpdateCaseBackgroundTask(CaseDao caseDao,Case aCase) {
            this.caseDao = caseDao;
            this.aCase = aCase;
        }

        @Override
        public void run() {
            caseDao.update(aCase);
        }
    }
    static class DeleteCaseBackgroundTask implements Runnable{

        private CaseDao caseDao;
        private Case aCase;

        public DeleteCaseBackgroundTask(CaseDao caseDao,Case aCase) {
            this.caseDao = caseDao;
            this.aCase = aCase;
        }

        @Override
        public void run() {
            caseDao.delete(aCase);
        }
    }

    static class DeleteAllBackgroundTask implements Runnable{

        private CaseDao caseDao;

        public DeleteAllBackgroundTask(CaseDao caseDao) {
            this.caseDao = caseDao;
        }

        @Override
        public void run() {
            caseDao.deleteAll();
        }
    }

}