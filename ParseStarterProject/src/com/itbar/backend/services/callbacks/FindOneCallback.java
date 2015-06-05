package com.itbar.backend.services.callbacks;

import com.itbar.backend.services.RemoteError;

/**
 * Created by martin on 5/23/15.
 */
public interface FindOneCallback<T> {
	void success(T object);
	void error(RemoteError e);
}
