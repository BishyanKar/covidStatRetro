package com.example.architectuecomp;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

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
}
