package romanowicz.projectx.Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.List;

public class SavedItemViewModel extends AndroidViewModel {

    private SavedItemRepository repository;
    private LiveData<List<SavedItemEntity>> allItems;

    public SavedItemViewModel(Application application) {
        super(application);
        repository = new SavedItemRepository(application);
        allItems = repository.getAllItems();
    }

    public LiveData<List<SavedItemEntity>> getAllItems() {
        return allItems;
    }
    public void insert(SavedItemEntity item) {
        repository.insert(item);
    }
    public void update(SavedItemEntity item) {
        repository.update(item);
    }
    public void delete(SavedItemEntity item) {
        repository.delete(item);
    }

    public SavedItemEntity findItem(int id) {
        return repository.findItem(id);
    }
}
