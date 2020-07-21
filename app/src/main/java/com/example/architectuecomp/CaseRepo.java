package com.example.architectuecomp;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class CaseRepo {
    private CaseDao caseDao;
    private LiveData<PagedList<Case>> allCases;

    public CaseRepo(Application application) {
        Database database = Database.getInstance(application);
        caseDao = database.caseDao();
        allCases = new LivePagedListBuilder<>(caseDao.getAllCases(),30).build();
    }

    public LiveData<PagedList<Case>> getAllCases()
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
