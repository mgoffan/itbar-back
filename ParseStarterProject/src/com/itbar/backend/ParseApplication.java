package com.itbar.backend;

import android.app.Application;

import com.itbar.backend.services.GlobalServices;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseUser;
import android.R.*;


public class ParseApplication extends Application {

  @Override
  public void onCreate() {
	  super.onCreate();

	  GlobalServices.init(this);
  }
}
