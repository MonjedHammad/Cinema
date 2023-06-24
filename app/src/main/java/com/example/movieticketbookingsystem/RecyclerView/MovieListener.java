package com.example.movieticketbookingsystem.RecyclerView;

public interface MovieListener {
    void onDelete(int id ,int pos);
    void onEdit(int id, int pos);

    void onItemClick (int pos);
}
