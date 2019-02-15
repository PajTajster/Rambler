package romanowicz.projectx.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface SavedItemDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SavedItemEntity savedItemEntity);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(SavedItemEntity savedItemEntity);

    @Delete
    void delete(SavedItemEntity savedItemEntity);

    @Query("DELETE FROM items_table")
    void deleteAll();

    @Query("SELECT * FROM items_table ORDER BY title ASC")
    LiveData<List<SavedItemEntity>> getAllItems();

    @Query("SELECT * FROM items_table WHERE ID == :id")
    SavedItemEntity findItem(int id);
}
