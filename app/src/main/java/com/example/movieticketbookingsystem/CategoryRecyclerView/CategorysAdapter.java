package com.example.movieticketbookingsystem.CategoryRecyclerView;

import android.app.AlertDialog;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieticketbookingsystem.AddCategory;
import com.example.movieticketbookingsystem.Models.CategoryModel;
import com.example.movieticketbookingsystem.R;
import com.example.movieticketbookingsystem.RecyclerView.MovieListener;
import com.example.movieticketbookingsystem.databinding.ItemCategoryBinding;
import com.example.movieticketbookingsystem.databinding.ItemMovieBinding;

import java.util.ArrayList;

public class CategorysAdapter extends RecyclerView.Adapter<CategorysAdapter.CategorysViewHolder> {

    ArrayList<CategoryModel> categorys;
    MovieListener listener;
    public CategorysAdapter(ArrayList<CategoryModel> categorys , MovieListener listener) {
        this.categorys = categorys;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategorysViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryBinding binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.getContext())
                , parent, false);
        return new CategorysViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategorysViewHolder holder, int position) {
        CategoryModel c =categorys.get(position);
        int pos= position;
        holder.categoryName.setText(c.getCatName());;

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onDelete(c.getId() , pos);
                return true;
            }
        });


    }
    @Override
    public int getItemCount() {
        return categorys.size();
    }

    class CategorysViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;

        public CategorysViewHolder(ItemCategoryBinding binding) {
            super(binding.getRoot());
            categoryName = binding.itemCategoryNameTv;
        }
    }
}
