package com.itbar.backend.services.callbacks;

import com.itbar.backend.services.RemoteError;

import java.util.List;

/**
 * Created by martin on 5/23/15.
 */
public interface FindMultipleCallback<T> {
	void success(List<T> objects);
	void error(RemoteError e);
}
