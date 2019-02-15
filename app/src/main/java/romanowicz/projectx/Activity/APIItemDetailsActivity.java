package romanowicz.projectx.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import romanowicz.projectx.APICalls.ItemInfo;
import romanowicz.projectx.R;

public class APIItemDetailsActivity extends AppCompatActivity {

    public final static int RESULT_ADD = 34;
    public final static String REPLY_ADD = "ReplyItem";

    private TextView itemTitle;
    private TextView itemDescription;
    private Button itemWiki;
    private Button itemYT;
    private Button itemSave;
    private ItemInfo itemInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apiitem_details);

        Intent intent = getIntent();
        itemInfo = (ItemInfo) intent.getSerializableExtra(SearchActivity.INTENT_DETAILS_DATA);

        itemTitle = findViewById(R.id.recommendation_details_title);
        itemTitle.setText(itemInfo.getName());

        itemDescription = findViewById(R.id.recommendation_details_description);
        itemDescription.setMovementMethod(new ScrollingMovementMethod());
        itemDescription.setText(itemInfo.getDescription());

        itemWiki = findViewById(R.id.recommendation_details_wikipedia);
        itemWiki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemInfo.getWikipediaURL() != null) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(itemInfo.getWikipediaURL()));

                    startActivity(browserIntent);
                }
            }
        });

        itemYT = findViewById(R.id.recommendation_details_youtube);
        itemYT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemInfo.getYoutubeURL() != null) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(itemInfo.getYoutubeURL()));

                    startActivity(browserIntent);
                }
            }
        });

        itemSave = findViewById(R.id.recommendation_details_save);
        itemSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                replyIntent.putExtra(REPLY_ADD, itemInfo);
                setResult(RESULT_ADD, replyIntent);
                finish();
            }
        });


        if(itemInfo.getWikipediaURL() == null) {
            itemWiki.setVisibility(View.GONE);
        }
        else {
            itemWiki.setVisibility(View.VISIBLE);
        }
        if(itemInfo.getYoutubeURL() == null) {
            itemYT.setVisibility(View.GONE);
        }
        else {
            itemYT.setVisibility(View.VISIBLE);
        }
    }
}
