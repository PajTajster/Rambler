package romanowicz.projectx.Activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import romanowicz.projectx.APICalls.ItemInfo;
import romanowicz.projectx.APICalls.SimiliarProducts;
import romanowicz.projectx.APICalls.TasteDiveAPI;
import romanowicz.projectx.Adapters.APIAdapter;
import romanowicz.projectx.Adapters.RecyclerViewClickListener;
import romanowicz.projectx.Database.SavedItemEntity;
import romanowicz.projectx.Database.SavedItemViewModel;
import romanowicz.projectx.R;

public class SearchActivity extends AppCompatActivity {

    private final String BASE_URL = "https://tastedive.com/api/";
    private final int API_EXTRA_INFO = 1;
    private final String API_KEY = "329057-FancyBoi-QETUL34T";

    public static final int DETAILS_ITEM_ACTIVITY_REQUEST_CODE = 12;
    public static final String INTENT_DETAILS_DATA = "Item";

    private RecyclerView recyclerView;
    private APIAdapter apiAdapter;
    private List<ItemInfo> itemsList;
    private EditText searchEditText;
    private Button searchButton;

    private SavedItemViewModel itemViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchButton = findViewById(R.id.searchButton);
        searchEditText = findViewById(R.id.searchEditText);

        itemViewModel = ViewModelProviders.of(this).get(SavedItemViewModel.class);

        recyclerView = findViewById(R.id.recycler_view_items);
        itemsList = new ArrayList<>();
        apiAdapter = new APIAdapter(this, itemsList, new RecyclerViewClickListener() {
            @Override
            public void onPositionClicked(int position) {
                Intent intent = new Intent(
                        SearchActivity.this,
                        APIItemDetailsActivity.class);

                ItemInfo itemInfo = apiAdapter.getItemInfo(position);
                intent.putExtra(INTENT_DETAILS_DATA,itemInfo);
                startActivityForResult(intent, DETAILS_ITEM_ACTIVITY_REQUEST_CODE);
            }
        });

        recyclerView.setAdapter(apiAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchEditText.getText().toString().length() <= 0){
                    Toast.makeText(
                            getApplicationContext(),
                            R.string.APICall_No_Input,
                            Toast.LENGTH_LONG).show();
                }
                else {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    TasteDiveAPI service = retrofit.create(TasteDiveAPI.class);
                    Call<SimiliarProducts> call = service.searchSimilar(
                            searchEditText.getText().toString(), API_EXTRA_INFO, API_KEY);

                    call.enqueue(new Callback<SimiliarProducts>() {
                        @Override
                        public void onResponse(Call<SimiliarProducts> call, Response<SimiliarProducts> response) {

                            SimiliarProducts similiarProducts = response.body();

                            if(similiarProducts.getFoundProducts().getItemInfoList().isEmpty()) {
                                Toast.makeText(
                                        getApplicationContext(),
                                        R.string.APICall_Success_Data_NOT_Found,
                                        Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(
                                        getApplicationContext(),
                                        R.string.APICall_Success_Data_Found,
                                        Toast.LENGTH_LONG).show();
                            }

                            for(ItemInfo i: similiarProducts.getFoundProducts().getItemInfoList()) {
                                i.setNormalTypeField(getApplicationContext());
                            }

                            apiAdapter.setItemsList(similiarProducts.getFoundProducts().getItemInfoList());
                            apiAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<SimiliarProducts> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),
                                    R.string.APICall_Failed,
                                    Toast.LENGTH_LONG).show();
                        }

                });
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == DETAILS_ITEM_ACTIVITY_REQUEST_CODE) {
            if(resultCode == APIItemDetailsActivity.RESULT_ADD) {
                SavedItemEntity item = new SavedItemEntity();

                ItemInfo itemInfo = (ItemInfo) data.getSerializableExtra(
                        APIItemDetailsActivity.REPLY_ADD);

                item.convertFromAPIToEntity(itemInfo);

                itemViewModel.insert(item);

                Toast.makeText(
                        getApplicationContext(),
                        R.string.database_added,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
