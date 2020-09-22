package com.example.blooddonation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.blooddonation.Models.GetBloodGroupModel.Datum;
import com.example.blooddonation.R;
import com.example.blooddonation.interfaces.BloodGroupItemId;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteIngredientsAdapter extends ArrayAdapter<Datum> {
    private List<Datum> ingredientsListFull;
    BloodGroupItemId itemID;
    Context context;

    public AutoCompleteIngredientsAdapter(@NonNull Context context, @NonNull List<Datum> ingridientsList, BloodGroupItemId groceryItemID) {
        super(context, 0, ingridientsList);
        ingredientsListFull = new ArrayList<>(ingridientsList);
        this.context = context;
        this.itemID = groceryItemID;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return countryFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.customer_row, parent, false
            );

        }
        TextView textViewName = convertView.findViewById(R.id.tv_autocoplete_item_name);
        LinearLayout linearrow = convertView.findViewById(R.id.linearrow);

        Datum countryItem = getItem(position);
        if (countryItem != null) {


            textViewName.setText(countryItem.getName());
            textViewName.setOnTouchListener((v, event) -> {
                itemID.bloodGroupItem(countryItem.getId());

                return false;
            });

//            SharedPreferences sharedPreferences = context.getSharedPreferences("addGroceryItem", Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("itemID", countryItem.getId());
//            editor.apply();

        }


        return convertView;
    }

    private Filter countryFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<Datum> suggestions = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(ingredientsListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Datum item : ingredientsListFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        suggestions.add(item);

                    }
                }
            }
            results.values = suggestions;
            results.count = suggestions.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Datum) resultValue).getName();


        }
    };
}




