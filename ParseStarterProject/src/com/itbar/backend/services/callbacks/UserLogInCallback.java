package com.itbar.backend.services.callbacks;

import com.itbar.backend.services.RemoteError;
import com.itbar.backend.services.views.User;

/**
 * Created by martin on 5/22/15.
 */
public interface UserLogInCallback {
	void success(User user);
	void error(RemoteError e);
}
