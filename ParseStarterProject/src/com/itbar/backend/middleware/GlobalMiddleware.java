package com.itbar.backend.middleware;

import android.content.Context;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseUser;

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
		ParseACL defaultACL = new ParseACL();
		// Optionally enable public read access.
		// defaultACL.setPublicReadAccess(true);
		ParseACL.setDefaultACL(defaultACL, true);

	}
}
