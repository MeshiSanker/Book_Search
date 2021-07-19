package com.example.book_search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class Adapter_Book extends RecyclerView.Adapter<Adapter_Book.MyViewHolder> {

    private List<Book> books;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    public Adapter_Book(Context context, List<Book> book) {
        this.mInflater = LayoutInflater.from(context);
        this.books = book;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_books, parent, false);
        return new MyViewHolder(view);
    }

    // binds the data to each view

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Book book = books.get(position);

        setTitleView(holder, book);
        setBodyView(holder, book);
        setRatingView(holder, book);
        setImageView(holder, book);
    }

    //This Method is setting the titles for the books
    // if needed it will highlight the searched text.
    private void setTitleView(MyViewHolder holder, Book book) {
        TextView titleView = holder.title;
        if ((book.getFoundTtxt() != null) && !(book.getFoundTtxt().equals(""))) {
            SpannableString sp = new SpannableString(book.getTitle());
            BackgroundColorSpan bgColor = new BackgroundColorSpan(Color.YELLOW);

            int i = book.getTitle().toLowerCase().indexOf(book.getFoundTtxt().toLowerCase());
            int j = i + book.getFoundTtxt().length();

            sp.setSpan(bgColor, i, j, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            titleView.setText(sp);
        } else
            titleView.setText(book.getTitle());
    }

    //This Method is setting the body for the books
    // if needed it will highlight the searched text.
    private void setBodyView(MyViewHolder holder, Book book) {
        TextView bodyView = holder.body;
        if ((book.getFoundBtxt() != null) && !(book.getFoundBtxt().equals(""))) {
            SpannableString sp = new SpannableString(book.getBody());
            BackgroundColorSpan bgColor = new BackgroundColorSpan(Color.YELLOW);
            int i = book.getBody().toLowerCase().indexOf(book.getFoundBtxt().toLowerCase());
            int j = i + book.getFoundBtxt().length();
            sp.setSpan(bgColor, i, j, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            bodyView.setText(sp);
        } else
            bodyView.setText(book.getBody());
    }

    //This Method is setting the star rating for the books
    private void setRatingView(MyViewHolder holder, Book book) {
        RatingBar starsView = holder.rate;
        starsView.setRating((float) book.getRate());
        starsView.setEnabled(false);
    }

    //This Method is setting the background color of the image view
    // and loading the image from the given URL
    @SuppressLint("NewApi")
    private void setImageView(MyViewHolder holder, Book book) {
        ImageView image = holder.img;
        image.setBackgroundColor(Color.rgb( (float) book.getRed(),
                                            (float) book.getGreen(),
                                            (float) book.getBlue()));

        Glide
                .with(mInflater.getContext())
                .load(book.getImgUrl())
                .apply(new RequestOptions().override(180, 140))
                .into(holder.img);
    }

    // stores and recycles views as they are scrolled off screen
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView body;
        RatingBar rate;
        ImageView img;

        MyViewHolder(View view) {
            super(view);
            title = itemView.findViewById(R.id.book_TXT_title);
            body = itemView.findViewById(R.id.book_TXT_body);
            rate = itemView.findViewById(R.id.rating_stars);
            img = itemView.findViewById(R.id.book_IMG);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "Book: " +
                            books.get(getAdapterPosition()).getTitle() +
                            "  is chosen at index: " + getAdapterPosition() +
                            "", Toast.LENGTH_LONG).show();
                }

            });
        }

    }

    //This Method is getting the filtered book list and updating the search view.
    public void filterList(List<Book> filteredlist) {
        books = filteredlist;
        notifyDataSetChanged();
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return books.size();
    }

}
