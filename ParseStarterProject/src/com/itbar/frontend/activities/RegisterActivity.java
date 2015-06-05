package com.itbar.frontend.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//import com.parse.starter.R;
import com.itbar.backend.services.RemoteError;
import com.itbar.backend.services.RemoteException;
import com.itbar.backend.services.ServiceRepository;
import com.itbar.backend.services.UserService;
import com.itbar.backend.services.callbacks.UserSignUpCallback;
import com.itbar.backend.util.Field;
import com.itbar.backend.util.FieldKeys;
import com.itbar.backend.util.Form;
import com.itbar.backend.util.FormBuilder;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.R.*;

import com.itbar.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class RegisterActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		final EditText legajo = (EditText) findViewById(R.id.legajoregistertxt);
		final EditText name = (EditText) findViewById(R.id.nametxt);
		final EditText surname = (EditText) findViewById(R.id.surname);
		final EditText mail = (EditText) findViewById(R.id.mailtxt);
		final EditText phone = (EditText) findViewById(R.id.phonetxt);
		final EditText pass = (EditText) findViewById(R.id.passtxt);
		final EditText rpass = (EditText) findViewById(R.id.repeatpasstxt);
		Button register = (Button) findViewById(R.id.registerinregisterbtn);

		register.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Form signupForm = FormBuilder.buildSignupForm();

				signupForm.set(FieldKeys.KEY_LEGAJO, legajo.getText().toString());
				signupForm.set(FieldKeys.KEY_NAME, name.getText().toString());
				signupForm.set(FieldKeys.KEY_SURNAME, surname.getText().toString());
				signupForm.set(FieldKeys.KEY_EMAIL, mail.getText().toString());
				signupForm.set(FieldKeys.KEY_PHONE, phone.getText().toString());
				signupForm.set(FieldKeys.KEY_PASSWORD, pass.getText().toString());
				signupForm.set(FieldKeys.KEY_REPEAT_PASSWORD, rpass.getText().toString());

				if (signupForm.isValid()) {

					ServiceRepository.getInstance().getUserService().signupUser(signupForm, new UserSignUpCallback() {
						@Override
						public void success() {
							Toast.makeText(getApplicationContext(), "Se ha registrado correctamente", Toast.LENGTH_SHORT).show();
						}
						@Override
						public void error(RemoteError e) {
							Toast.makeText(getApplicationContext(), "Ha ocurrido un error imprevisto, codigo: " + e.getCode(), Toast.LENGTH_SHORT).show();
						}
					});

				} else {
					Toast.makeText(getApplicationContext(), signupForm.collectErrors().toString(), Toast.LENGTH_SHORT).show();
				}
			}
		});

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_register, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
