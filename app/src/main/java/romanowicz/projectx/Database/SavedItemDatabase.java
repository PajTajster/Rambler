package romanowicz.projectx.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {SavedItemEntity.class}, version = 1)
public abstract class SavedItemDatabase extends RoomDatabase {

    public abstract SavedItemDAO SavedItemDAO();


    private static volatile SavedItemDatabase INSTANCE;

    static SavedItemDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (SavedItemDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SavedItemDatabase.class, "items_database")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
