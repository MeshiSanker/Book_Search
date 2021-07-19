package com.example.book_search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.appcompat.widget.SearchView;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Book> bookList;
    private RecyclerView books_recycler;
    private Adapter_Book adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        find_views();
        initBookList();

        adapter = new Adapter_Book(this, bookList);

        initViews();
    }

    private void find_views() {
        books_recycler = findViewById(R.id.recycler_books);
    }

    private void initViews() {
        books_recycler.setLayoutManager(new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false));
        books_recycler.setAdapter(adapter);
    }

    //Creating the Books list from the given JSON file
    public void initBookList(){
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray book_arr = obj.getJSONArray("data");
            parseAllBooks(book_arr);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //Loading the JSON file into a string
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("books_list.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    //This Method is creating a Books List Object from the given JSON array.
    public void parseAllBooks(JSONArray responseJson){
        this.bookList = new ArrayList<>();
        try{
            for (int i=0; i< responseJson.length(); i++){
                JSONObject booksDetails = responseJson.getJSONObject(i);
                Book b = new Book();
                b.setTitle(booksDetails.getString("title"));
                b.setBody(booksDetails.getString("body"));
                b.setRate(booksDetails.getDouble("rating"));
                b.setImgUrl(booksDetails.getString("url"));

                //The Placeholder parameter is written as an object, so we read it as object and
                //read the colors from it.
                JSONObject color_arr = booksDetails.getJSONObject("placeholderColor");
                b.setRed(color_arr.getDouble("red"));
                b.setGreen(color_arr.getDouble("green"));
                b.setBlue(color_arr.getDouble("blue"));
                this.bookList.add(b);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }




    //This method initiate the menu view
    //and calling the filter method in case the search test has changed.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.actionSearch);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // calling a method to filter our recycler view.
                filter(newText);
                return false;
            }
        });
        return true;
    }

    //This Method is handeling the menu buttons click.
    //if sort by A-Z is clicked the list will be sorted by title
    //is sorted by rating the list will be sorted by the stars
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.sortAZ:
                Collections.sort(bookList);
                adapter.filterList(bookList);
                return true;

            case R.id.sortRate:
                Collections.sort(bookList, new Comparator<Book>() {
                    @Override
                    public int compare(Book b1, Book b2) {
                        return new Double(b1.getRate()).compareTo(new Double(b2.getRate()));
                    }
                });
                //reverse the list so the highest rate will be on top
                Collections.reverse(bookList);
                adapter.filterList(bookList);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //This Method checks if the search string is appearing in te books title or body.
    //if it does, it will update the FoundText(T/B) and the adapter will highlight it.
    //If there is no matching text it will say "No Data Found"
    private void filter(String text) {

        List<Book> filteredlist = new ArrayList<>();
        boolean toAdd=false;

        for (Book item : bookList) {
            // checking if the entered string matched with title/body
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                item.setFoundTtxt(text);
                toAdd=true;
            } if(item.getBody().toLowerCase().contains(text.toLowerCase())){
                item.setFoundBtxt(text);
                toAdd=true;
            }
            if (toAdd){
                filteredlist.add(item);
            }
            toAdd=false;
        }
        if (filteredlist.isEmpty()) {
            // if no book is found, show a msg.
            Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();
        } else {
            //passing the filtered list to our adapter class
            adapter.filterList(filteredlist);
        }
    }

}