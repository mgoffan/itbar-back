package com.itbar.frontend.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itbar.R;
import com.itbar.backend.services.RemoteError;
import com.itbar.backend.services.ServiceRepository;
import com.itbar.backend.services.callbacks.FindMultipleCallback;
import com.itbar.backend.services.callbacks.RUDCallback;
import com.itbar.backend.services.views.Order;
import com.itbar.backend.util.FieldKeys;
import com.itbar.backend.util.Form;
import com.itbar.backend.util.FormBuilder;

import java.util.List;

public class OrderActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);

		drawUI();
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

	private void drawOrder(final Order order) {
		LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View v = vi.inflate(R.layout.order_layout, null);
		TextView orderID = (TextView) v.findViewById(R.id.orderID);
		TextView user = (TextView) v.findViewById(R.id.user);
		TextView totalPrice = (TextView) v.findViewById(R.id.totalprice);

		orderID.setText(order.getObjectId());
		user.setText(order.getBuyer().getName() + " " + order.getBuyer().getSurname() + " - " + order.getBuyer().getLegajo());
		totalPrice.setText(String.format("$%.02f", order.getTotal()));

		Button prods = (Button) v.findViewById(R.id.prodInOrderBtn);
		Button ready = (Button) v.findViewById(R.id.readyBtn);

		final LinearLayout insertPoint = (LinearLayout) findViewById(R.id.scrollOrder);
		insertPoint.addView(v);

		prods.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent =  new Intent(getApplicationContext(),ProductOrderActivity.class);
				intent.putExtra("orderID",order.getObjectId());
				startActivity(intent);

			}
		});

		ready.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {

				Form form = FormBuilder.buildOrderStatusChangeForm();

				form.set(FieldKeys.KEY_ID, order.getObjectId());
				form.set(FieldKeys.KEY_STATUS, "Preparada");

				ServiceRepository.getInstance().getOrderService().orderHasBeenPrepared(form, new RUDCallback() {
					@Override
					public void success() {
						insertPoint.removeView(v);
						Toast.makeText(getApplicationContext(), "Listo", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void error(RemoteError e) {
						Toast.makeText(getApplicationContext(),ScreenMessages.OOPS,Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
	}

	private void drawUI() {

		Form form = FormBuilder.buildGetOrdersForm();

		form.set(FieldKeys.KEY_STATUS, ScreenMessages.SENT);

		if (form.isValid()) {

			ServiceRepository.getInstance().getOrderService().getOrders(form, new FindMultipleCallback<Order>() {
				@Override
				public void success(List<Order> objects) {

					for ( Order order : objects ) {

						drawOrder(order);
					}
				}

				@Override
				public void error(RemoteError e) {
					Toast.makeText(getApplicationContext(), ScreenMessages.ERROR, Toast.LENGTH_SHORT).show();
				}
			});
		} else {
			Toast.makeText(getApplicationContext(), form.collectErrors().toString(), Toast.LENGTH_SHORT).show();
		}

	}
}
