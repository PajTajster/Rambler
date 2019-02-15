package romanowicz.projectx.Database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

public class SavedItemRepository {
    private SavedItemDAO savedItemDAO;
    private LiveData<List<SavedItemEntity>> allItems;

    SavedItemRepository(Application application) {
        SavedItemDatabase db = SavedItemDatabase.getDatabase(application);
        savedItemDAO = db.SavedItemDAO();
        allItems = savedItemDAO.getAllItems();
    }

    LiveData<List<SavedItemEntity>> getAllItems() {
        return allItems;
    }

    public void insert(SavedItemEntity item) {
        new insertAsync(savedItemDAO).doInBackground(item);
    }
    public void update(SavedItemEntity item) {
        new updateAsync(savedItemDAO).doInBackground(item);
    }
    public void delete(SavedItemEntity item) {
        new deleteAsync(savedItemDAO).doInBackground(item);
    }

    public SavedItemEntity findItem(int id) {
        return savedItemDAO.findItem(id);
    }

    private static class insertAsync extends AsyncTask<SavedItemEntity, Void, Void> {
        private SavedItemDAO asyncDAO;

        insertAsync(SavedItemDAO dao) {
            asyncDAO = dao;
        }

        @Override
        protected Void doInBackground(SavedItemEntity... savedItemEntities) {
            asyncDAO.insert(savedItemEntities[0]);
            return null;
        }
    }
    private static class updateAsync extends AsyncTask<SavedItemEntity, Void, Void> {
        private SavedItemDAO asyncDAO;

        updateAsync(SavedItemDAO dao) {
            asyncDAO = dao;
        }

        @Override
        protected Void doInBackground(SavedItemEntity... savedItemEntities) {
            asyncDAO.update(savedItemEntities[0]);
            return null;
        }
    }
    private static class deleteAsync extends AsyncTask<SavedItemEntity, Void, Void> {
        private SavedItemDAO asyncDAO;

        deleteAsync(SavedItemDAO dao) {
            asyncDAO = dao;
        }

        @Override
        protected Void doInBackground(SavedItemEntity... savedItemEntities) {
            asyncDAO.delete(savedItemEntities[0]);
            return null;
        }
    }
}
