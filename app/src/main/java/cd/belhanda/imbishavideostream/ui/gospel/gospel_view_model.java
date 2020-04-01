package cd.belhanda.imbishavideostream.ui.gospel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class gospel_view_model extends ViewModel {

    private MutableLiveData<String> mText;

    public gospel_view_model() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}