package cd.belhanda.imbishavideostream.ui.about;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class about_view_model extends ViewModel {

    private MutableLiveData<String> mText;

    public about_view_model() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}