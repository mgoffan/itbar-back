package com.itbar.backend.services.callbacks;

import com.itbar.backend.services.RemoteError;

/**
 * RUD: Read, Update, Delete
 *
 * Created by martin on 5/23/15.
 */
public interface RUDCallback {
	void success();
	void error(RemoteError e);
}
