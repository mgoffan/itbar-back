package com.itbar.backend.services.callbacks;

import com.itbar.backend.services.RemoteError;
import com.itbar.backend.services.views.Category;

/**
 * Created by martin on 10/06/15.
 */
public interface SaveCategoryCallback {
	void success(Category category);
	void error(RemoteError error);
}
