package com.itbar.backend.services.callbacks;

import com.itbar.backend.services.RemoteError;

/**
 * Created by martin on 5/23/15.
 */
public interface SaveOrderCallback {
	void success();
	void error(RemoteError e);
}
