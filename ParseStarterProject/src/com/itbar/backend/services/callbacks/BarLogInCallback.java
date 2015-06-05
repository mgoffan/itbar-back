package com.itbar.backend.services.callbacks;

import com.itbar.backend.services.RemoteError;
import com.itbar.backend.services.views.Bar;

/**
 * Created by martin on 5/23/15.
 */
public interface BarLogInCallback {
	void success(Bar bar);
	void error(RemoteError e);
}
