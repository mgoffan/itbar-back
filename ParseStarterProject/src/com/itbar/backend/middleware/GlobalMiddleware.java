package com.itbar.backend.middleware;

import android.content.Context;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

/**
 * Created by martin on 5/23/15.
 */
public class GlobalMiddleware {

	public static void init(Context ctx) {

		// Initialize Crash Reporting.
		ParseCrashReporting.enable(ctx);

		// Enable Local Datastore.
		Parse.enableLocalDatastore(ctx);

		// Add your initialization code here
		Parse.initialize(ctx);


		ParseUser.enableAutomaticUser();
		final ParseACL defaultACL = new ParseACL();
		defaultACL.setPublicWriteAccess(true);
		defaultACL.setPublicReadAccess(true);
		// Optionally enable public read access.
		// defaultACL.setPublicReadAccess(true);
		ParseACL.setDefaultACL(defaultACL, true);

	}
}
