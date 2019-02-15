package romanowicz.projectx.APICalls;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SimiliarItems {
    @SerializedName("Results")
    List<ItemInfo> itemInfoList;

    SimiliarItems(List<ItemInfo> itemInfo) {
        itemInfoList = itemInfo;
    }

    public List<ItemInfo> getItemInfoList() {
        return itemInfoList;
    }

    public void setItemInfoList(List<ItemInfo> itemInfoList) {
        this.itemInfoList = itemInfoList;
    }
}
