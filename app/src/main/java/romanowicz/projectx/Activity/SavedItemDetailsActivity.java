package romanowicz.projectx.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import romanowicz.projectx.Database.SavedItemEntity;
import romanowicz.projectx.R;

public class SavedItemDetailsActivity extends AppCompatActivity {

    public static final int RESULT_DELETE = 54;
    public static final String REPLY_DELETE = "ReplyItem";


    private TextView itemTitle;
    private TextView itemDescription;
    private Button itemWiki;
    private Button itemYT;
    private Button itemDelete;
    private SavedItemEntity item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_item_details);

        Intent intent = getIntent();
        item = (SavedItemEntity) intent.getSerializableExtra(GalleryActivity.INTENT_DETAILS_ITEM);

        itemTitle = findViewById(R.id.saved_item_title);
        itemTitle.setText(item.getTitle());

        itemDescription = findViewById(R.id.saved_item_description);
        itemDescription.setMovementMethod(new ScrollingMovementMethod());
        itemDescription.setText(item.getDescription());

        itemWiki = findViewById(R.id.saved_item_wikipedia);
        itemWiki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(item.getWikipediaURL()));

                startActivity(browserIntent);
            }
        });

        itemYT = findViewById(R.id.saved_item_youtube);
        itemYT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(item.getYoutubeURL()));

                startActivity(browserIntent);
            }
        });

        itemDelete = findViewById(R.id.saved_item_delete);
        itemDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                replyIntent.putExtra(REPLY_DELETE, item);
                setResult(RESULT_DELETE, replyIntent);
                finish();
            }
        });

        if(item.getWikipediaURL() == null) {
            itemWiki.setVisibility(View.GONE);
        }
        else {
            itemWiki.setVisibility(View.VISIBLE);
        }
        if(item.getYoutubeURL() == null) {
            itemYT.setVisibility(View.GONE);
        }
        else {
            itemWiki.setVisibility(View.VISIBLE);
        }

    }
}
