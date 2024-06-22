package com.bitmask.android.demos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bitmask.android.demos.databinding.FragmentFirstBinding;
import com.bitmask.android.demos.handler.HandlerActivity;
import com.bitmask.android.demos.retrofit.RetrofitActivity;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    private List<ActivityInfo> activityInfoList = new ArrayList<>();

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(v ->
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment)
        );

        activityInfoList.add(new ActivityInfo("HandlerActivity", HandlerActivity.class));
        activityInfoList.add(new ActivityInfo("RetrofitActivity", RetrofitActivity.class));
        activityInfoList.add(new ActivityInfo("HandlerActivity", HandlerActivity.class));
        activityInfoList.add(new ActivityInfo("HandlerActivity", HandlerActivity.class));
        activityInfoList.add(new ActivityInfo("HandlerActivity", HandlerActivity.class));
        CustomAdapter customAdapter = new CustomAdapter(activityInfoList);

        binding.recyclerviewFirst.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerviewFirst.setAdapter(customAdapter);
//        customAdapter.notifyDataSetChanged();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    class ActivityInfo{
        public String name;
        public Class<?> activity;

        public ActivityInfo(String name, Class<?> activity) {
            this.name = name;
            this.activity = activity;
        }
    }


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the VieactivityInfoListwHolder's View
            textView = view.findViewById(R.id.textView);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {

        private List<ActivityInfo> myActivityInfoList;

        public CustomAdapter(List<ActivityInfo> dataSet) {
            myActivityInfoList = dataSet;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view, which defines the UI of the list item
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_row, viewGroup, false);

            return new ViewHolder(view);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            ActivityInfo activityInfo = myActivityInfoList.get(position);
            viewHolder.getTextView().setText(activityInfo.name);
            viewHolder.getTextView().setOnClickListener(v -> {
                Intent intent = new Intent(viewHolder.itemView.getContext(), activityInfo.activity);
                viewHolder.itemView.getContext().startActivity(intent);
            });
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return myActivityInfoList.size();
        }
    }
}