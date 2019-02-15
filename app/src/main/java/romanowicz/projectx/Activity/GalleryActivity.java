package romanowicz.projectx.Activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import romanowicz.projectx.Adapters.DBAdapter;
import romanowicz.projectx.Adapters.RecyclerViewClickListener;
import romanowicz.projectx.Database.SavedItemEntity;
import romanowicz.projectx.Database.SavedItemViewModel;
import romanowicz.projectx.R;

public class GalleryActivity extends AppCompatActivity {

    public static final int SAVED_ITEM_DETAILS_REQUEST_CODE = 1;
    public static final String INTENT_DETAILS_ITEM = "Item";

    private SavedItemViewModel itemViewModel;
    public List<SavedItemEntity> itemsToSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_database);
        final DBAdapter adapter = new DBAdapter(this,
                new RecyclerViewClickListener() {
                    @Override
                    public void onPositionClicked(int position) {
                        Intent intent = new Intent(
                                GalleryActivity.this,
                                SavedItemDetailsActivity.class);

                        SavedItemEntity item = itemViewModel.findItem(position);

                        intent.putExtra(INTENT_DETAILS_ITEM, item);
                        startActivityForResult(intent, SAVED_ITEM_DETAILS_REQUEST_CODE);
                    }
                });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemViewModel = ViewModelProviders.of(this).get(SavedItemViewModel.class);

        itemViewModel.getAllItems().observe(this, new Observer<List<SavedItemEntity>>() {
            @Override
            public void onChanged(@Nullable List<SavedItemEntity> savedItemEntities) {
                adapter.setAllItems(savedItemEntities);
                adapter.notifyDataSetChanged();
            }
        });

        EditText searchbar = findViewById(R.id.database_searching_edittext);

        itemsToSearch = new LinkedList<>();

        searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0) {
                    itemsToSearch.clear();
                    for(SavedItemEntity item: itemViewModel.getAllItems().getValue()) {
                        if(item.getTitle().toLowerCase().contains(s.toString().toLowerCase())) {
                            itemsToSearch.add(item);
                        }
                    }
                    adapter.setAllItems(itemsToSearch);
                    adapter.notifyDataSetChanged();
                }
                else {
                    itemsToSearch.clear();
                    itemsToSearch.addAll(itemViewModel.getAllItems().getValue());

                    adapter.setAllItems(itemsToSearch);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SAVED_ITEM_DETAILS_REQUEST_CODE) {
            if(resultCode == SavedItemDetailsActivity.RESULT_DELETE) {
                SavedItemEntity item = (SavedItemEntity) data.getSerializableExtra(
                        SavedItemDetailsActivity.REPLY_DELETE);

                itemViewModel.delete(item);

                Toast.makeText(
                        getApplicationContext(),
                        R.string.database_removed,
                        Toast.LENGTH_SHORT).show();
            }
        }

    }
}
