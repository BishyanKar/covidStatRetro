package com.example.architectuecomp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class CasesAdapter extends PagedListAdapter<Case, CasesAdapter.CaseHolder> implements Filterable {

    private Context mContext;
    private List<Case> casesFull;
    private List<Case> cases;
    private MainActivity activity;
    private LottieAnimationView animationView;


    public void setAnimationView(LottieAnimationView animationView) {
        this.animationView = animationView;
    }

    public void setActivity(MainActivity activity) {
        this.activity = activity;
    }

    protected CasesAdapter() {
        super(diffCallback);
    }

    public void setCases(List<Case> cases) {
        this.cases = cases;
        casesFull = new ArrayList<>(cases);
    }

    static final DiffUtil.ItemCallback<Case> diffCallback = new DiffUtil.ItemCallback<Case>() {
        @Override
        public boolean areItemsTheSame(@NonNull Case oldItem, @NonNull Case newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Case oldItem, @NonNull Case newItem) {
            return oldItem.getCountry().compareTo(newItem.getCountry()) == 0 && oldItem.getTimeStamp().compareTo(newItem.getTimeStamp()) == 0;
        }
    };
    @NonNull
    @Override
    public CaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_case_layout, parent, false);

        return new CaseHolder(view);
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Case> filteredList = new ArrayList<>();

            if(constraint==null || constraint.length() ==0 )
            {
                filteredList.addAll(casesFull);
            }
            else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(Case item:casesFull)
                {
                    if(item.getCountry().toLowerCase().contains(filterPattern))
                    {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            cases.clear();
            cases.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    @Override
    public void onBindViewHolder(@NonNull CaseHolder holder, int position) {
        Case aCase = getItem(holder.getAdapterPosition());
        if(holder.getAdapterPosition() == cases.size()-1) {
            animationView.setVisibility(View.VISIBLE);
            activity.fetchData(cases.size());
        }

        holder.constraintLayout.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_slide_down));

        if(aCase!=null) {
            String country = aCase.getCountry();
            String totalCases = aCase.getTotal();
            String death = aCase.getDeaths();
            String updated = aCase.getTimeStamp();

            holder.country.setText(country);
            holder.updated.setText(updated);
            holder.death.setText(death);
            holder.total.setText(totalCases);
        }
    }

    static class CaseHolder extends RecyclerView.ViewHolder{
        private TextView country,death,total,updated;
        private ConstraintLayout constraintLayout;
        public CaseHolder(@NonNull View itemView) {
            super(itemView);
            country = itemView.findViewById(R.id.country);
            death = itemView.findViewById(R.id.deaths);
            total = itemView.findViewById(R.id.total_cases);
            updated = itemView.findViewById(R.id.day_n_time);
            constraintLayout = itemView.findViewById(R.id.item_constraint_layout);
        }
    }
}
