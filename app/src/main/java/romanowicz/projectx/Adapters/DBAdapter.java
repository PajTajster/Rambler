package romanowicz.projectx.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.List;

import romanowicz.projectx.Database.SavedItemEntity;
import romanowicz.projectx.R;

public class DBAdapter extends RecyclerView.Adapter<DBAdapter.DBAdapterViewHolder> {

    private final LayoutInflater layoutInflater;
    private List<SavedItemEntity> allItems;
    private RecyclerViewClickListener listener;

    public DBAdapter(Context context, RecyclerViewClickListener clickListener) {
        layoutInflater = LayoutInflater.from(context);
        listener = clickListener;
    }

    @NonNull
    @Override
    public DBAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = layoutInflater.inflate(R.layout.saved_item_row, viewGroup, false);
        return new DBAdapterViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull DBAdapterViewHolder dbAdapterViewHolder, int i) {
        if(allItems != null) {
            SavedItemEntity item = allItems.get(i);
            dbAdapterViewHolder.itemTitle.setText(item.getTitle());
            dbAdapterViewHolder.itemType.setText(item.getType());
        }
        else {
            dbAdapterViewHolder.itemTitle.setText(R.string.no_data);
            dbAdapterViewHolder.itemType.setText(R.string.no_data);
        }
    }

    @Override
    public int getItemCount() {
        if(allItems == null) {
            return 0;
        }
        else {
            return  allItems.size();
        }
    }

    public void setAllItems(List<SavedItemEntity> allItems) {
        this.allItems = allItems;
    }

    @Override
    public long getItemId(int position) {
        return allItems.get(position).getID();
    }

    class DBAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView itemTitle;
        private final TextView itemType;
        private final Button itemDetails;
        private WeakReference<RecyclerViewClickListener> listenerRef;

        public DBAdapterViewHolder(@NonNull View itemView, RecyclerViewClickListener listener) {
            super(itemView);

            listenerRef = new WeakReference<>(listener);

            itemTitle = itemView.findViewById(R.id.savedItem_title);
            itemType = itemView.findViewById(R.id.savedItem_type);
            itemDetails = itemView.findViewById(R.id.savedItem_details);

            itemDetails.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            SavedItemEntity item = allItems.get(getAdapterPosition());
            listenerRef.get().onPositionClicked(item.getID());
        }
    }

}
