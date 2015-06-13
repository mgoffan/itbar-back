package com.itbar.frontend.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.itbar.R;
import com.itbar.backend.services.RemoteError;
import com.itbar.backend.services.ServiceRepository;
import com.itbar.backend.services.callbacks.FindMultipleCallback;
import com.itbar.backend.util.FieldKeys;
import com.itbar.backend.util.Form;
import com.itbar.backend.util.FormBuilder;

import java.util.List;

public class OrderActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_order, menu);
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

	private void drawOrders() {

	}

	private void drawUI() {

		Form form = FormBuilder.buildGetOrdersForm();

		form.set(FieldKeys.KEY_STATUS, "Enviada");

		if (form.isValid()) {

			ServiceRepository.getInstance().getOrderService().getOrders(form, new FindMultipleCallback() {
				@Override
				public void success(List objects) {
					LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

				}

				@Override
				public void error(RemoteError e) {
					Toast.makeText(getApplicationContext(),ScreenMessages.ERROR,Toast.LENGTH_SHORT).show();
				}
			});
		} else {
			// No es valido que es raro
		}

	}
}
