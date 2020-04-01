package cd.belhanda.imbishavideostream.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class search_view_model extends ViewModel {

    private MutableLiveData<String> mText;

    public search_view_model() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}