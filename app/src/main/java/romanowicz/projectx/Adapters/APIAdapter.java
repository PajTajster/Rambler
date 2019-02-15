package romanowicz.projectx.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.List;

import romanowicz.projectx.APICalls.ItemInfo;
import romanowicz.projectx.R;

public class APIAdapter extends RecyclerView.Adapter<APIAdapter.APIAdapterViewHolder> {

    private final LayoutInflater layoutInflater;
    private Context adapterContext;
    private List<ItemInfo> itemsList;

    private RecyclerViewClickListener listener;

    public APIAdapter(Context context, List<ItemInfo> items, RecyclerViewClickListener listener) {
        layoutInflater = LayoutInflater.from(context);
        this.adapterContext = context;
        this.itemsList = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public APIAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.api_image_row, viewGroup, false);
        return new APIAdapterViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull APIAdapterViewHolder apiAdapterViewHolder, int i) {
        ItemInfo item = itemsList.get(i);

        try {
            apiAdapterViewHolder.title.setText(item.getName());
            apiAdapterViewHolder.type.setText(item.getType());
        }
        catch(NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public void setItemsList(List<ItemInfo> itemsList) {
        this.itemsList.clear();
        this.itemsList = itemsList;
        this.notifyDataSetChanged();
    }

    public ItemInfo getItemInfo(int pos) {
        return itemsList.get(pos);
    }

    class APIAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title;
        private TextView type;
        private Button details;
        private WeakReference<RecyclerViewClickListener> listenerRef;


        public APIAdapterViewHolder(@NonNull View itemView, RecyclerViewClickListener listener) {
            super(itemView);

            listenerRef = new WeakReference<>(listener);

            title = itemView.findViewById(R.id.recommendation_title);
            type = itemView.findViewById(R.id.recommendation_type);
            details = itemView.findViewById(R.id.recommendation_details_button);
            details.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listenerRef.get().onPositionClicked(getAdapterPosition());
        }
    }
}
