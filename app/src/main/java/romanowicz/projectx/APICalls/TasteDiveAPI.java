package romanowicz.projectx.APICalls;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TasteDiveAPI {

    @GET("similar")
    Call<SimiliarProducts> searchSimilar(@Query("q") String query,
                                         @Query("info") int info,
                                         @Query("k") String key);
}
