package romanowicz.projectx.APICalls;

import com.google.gson.annotations.SerializedName;

public class SimiliarProducts {
    @SerializedName("Similar")
    private SimiliarItems foundProducts;

    SimiliarProducts(SimiliarItems info) {
        foundProducts = info;
    }

    public SimiliarItems getFoundProducts() {
        return foundProducts;
    }

    public void setFoundProducts(SimiliarItems foundProducts) {
        this.foundProducts = foundProducts;
    }
}
