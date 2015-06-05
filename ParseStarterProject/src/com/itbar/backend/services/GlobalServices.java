package com.itbar.backend.services;

import android.content.Context;

import com.itbar.backend.middleware.GlobalMiddleware;

/**
 * Created by martin on 5/23/15.
 */
public class GlobalServices {

	public static void init(Context ctx) {

		GlobalMiddleware.init(ctx);

	}
}
