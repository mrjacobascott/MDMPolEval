package com.lazydad.mdmpoleval.ui.main;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class PageViewModel extends ViewModel {


    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();

    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            /*
            if (input==1){
                //do something
                ImportPolicy policy = new ImportPolicy();
                policy.getPolicy();

                return String.valueOf("this is a test");
            }
            */

            return String.valueOf("this is tab: "+ mIndex);
        }
    });

//    private void policyImport() {
        //do something

  //  }

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<String> getText() {
        return mText;
    }
}