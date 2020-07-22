package com.example.architectuecomp.viewmodel;

import android.app.Application;
import android.content.Context;

import com.example.architectuecomp.model.Case;
import com.example.architectuecomp.reponses.GenericResponse;
import com.example.architectuecomp.repository.CaseRepo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class CaseViewModel extends AndroidViewModel {
    private CaseRepo caseRepo;
    private LiveData<List<Case>> allCases;

    public CaseViewModel(@NonNull Application application) {
        super(application);
        caseRepo = new CaseRepo(application);
        allCases = caseRepo.getAllCases();
    }

    public void insert(Case aCase){
        caseRepo.insert(aCase);
    }
    public void update(Case aCase){
        caseRepo.update(aCase);
    }
    public void delete(Case aCase){
        caseRepo.delete(aCase);
    }
    public void deleteAll(){
        caseRepo.deleteAll();
    }
    public LiveData<List<Case>> getAllCases(){
        return allCases;
    }


    public void fetchDataOnline(Context context){
        caseRepo.fetchDataOnline(context);
    }
    public MutableLiveData<GenericResponse> getResponseList(){
        return caseRepo.getReponseList();
    }
}