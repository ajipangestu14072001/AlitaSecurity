package com.abeldandi.alitasecurity.callback;

import com.abeldandi.alitasecurity.ListElement;
import com.abeldandi.alitasecurity.model.DataObject;

public interface FetchRecyclerViewItems {
    void onIntent(DataObject dataObject);
    void onIntentEmployee(ListElement element);
}
