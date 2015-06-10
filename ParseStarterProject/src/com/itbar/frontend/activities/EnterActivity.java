package com.itbar.frontend.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.itbar.R;
import com.itbar.backend.services.RemoteError;
import com.itbar.backend.services.ServiceRepository;
import com.itbar.backend.services.callbacks.BarLogInCallback;
import com.itbar.backend.services.callbacks.UserLogInCallback;
import com.itbar.backend.services.views.Bar;
import com.itbar.backend.services.views.User;
import com.itbar.backend.util.FieldKeys;
import com.itbar.backend.util.Form;
import com.itbar.backend.util.FormBuilder;
import com.parse.ParseAnalytics;
//import com.parse.starter.R;


public class EnterActivity extends Activity {
	/**
	 * Called when the activity is first created.
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ParseAnalytics.trackAppOpenedInBackground(getIntent());

		drawUI();

	}


	public void drawUI() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Window w = getWindow();
		w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

		setContentView(R.layout.activity_ingreso);

		final EditText usuario = (EditText) findViewById(R.id.usuariotxt);
		final EditText contra = (EditText) findViewById(R.id.contratxt);

		Button ingresar = (Button) findViewById(R.id.enterbtn);

		RelativeLayout back = (RelativeLayout) findViewById(R.id.back);

		final ImageView imagen = (ImageView) findViewById(R.id.imagen);

		back.setBackgroundResource(R.drawable.fondo_ppal_itbar);


		ingresar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Form loginForm = FormBuilder.buildBarLoginForm();

				loginForm.set(FieldKeys.KEY_CUIT, usuario.getText().toString());
				loginForm.set(FieldKeys.KEY_PASSWORD, contra.getText().toString());


				if (loginForm.isValid()) {
					ServiceRepository.getInstance().getBarService().loginBar(loginForm, new BarLogInCallback() {
						@Override
						public void success(Bar user) {
							if (user != null) {
								Intent intent = new Intent(EnterActivity.this, MainActivity.class);
								startActivity(intent);
								imagen.setBackgroundResource(R.drawable.ok);
								Toast.makeText(getApplicationContext(), ScreenMessages.WELCOME, Toast.LENGTH_SHORT).show();
								finish();
							} else {
								imagen.setBackgroundResource(R.drawable.error);
								Toast.makeText(getApplicationContext(), ScreenMessages.NO_USER_VALID, Toast.LENGTH_SHORT).show();
							}
						}

						@Override
						public void error(RemoteError e) {
							Toast.makeText(getApplicationContext(), ScreenMessages.ERROR, Toast.LENGTH_SHORT).show();
						}
					});
				} else {
					Log.v("APP123", loginForm.collectErrors().toString());
				}

			}
		});

	}


	public static class listOfProducts extends Activity {

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_list_of_products);
		}

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.menu_list_of_products, menu);
			return true;
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// Handle action bar item clicks here. The action bar will
			// automatically handle clicks on the Home/Up button, so long
			// as you specify a parent activity in AndroidManifest.xml.
			int id = item.getItemId();

			return super.onOptionsItemSelected(item);
		}
	}
}
