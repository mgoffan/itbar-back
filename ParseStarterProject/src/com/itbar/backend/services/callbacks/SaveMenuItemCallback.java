package com.itbar.backend.services.callbacks;

import com.itbar.backend.services.RemoteError;
import com.itbar.backend.services.views.Category;
import com.itbar.backend.services.views.MenuItem;

/**
 * Created by martin on 10/06/15.
 */
public interface SaveMenuItemCallback {
	void success(MenuItem menuItem);
	void error(RemoteError error);
}
