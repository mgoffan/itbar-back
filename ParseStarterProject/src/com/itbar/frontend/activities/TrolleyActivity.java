package com.itbar.frontend.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.itbar.R;
import com.itbar.backend.services.RemoteError;
import com.itbar.backend.services.ServiceRepository;
import com.itbar.backend.services.Session;
import com.itbar.backend.services.callbacks.SaveOrderCallback;
import com.itbar.backend.services.views.OrderProduct;
import com.itbar.backend.util.FieldKeys;
import com.itbar.backend.util.Form;
import com.itbar.backend.util.FormBuilder;
import com.itbar.frontend.Models.ScreenMessages;
import com.itbar.frontend.appmanager.Price;
import com.itbar.frontend.appmanager.BooleanStatus;

import java.util.Calendar;
import java.util.List;

public class TrolleyActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getActionBar().hide();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trolley);
		userUI();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_trolley, menu);
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

	public void userUI() {

		final Calendar cal = Calendar.getInstance();

		ImageButton add = (ImageButton) findViewById(R.id.sendbtn);
		ImageButton home = (ImageButton) findViewById(R.id.mainbtn);
		final ImageButton payment = (ImageButton) findViewById(R.id.payment);
		final BooleanStatus statusPayment = new BooleanStatus(true);

		final TextView total = (TextView) findViewById(R.id.total);
		final Price price = new Price();
		final String[] pay = {"Efectivo"};

		final Form form = FormBuilder.buildGetOrderForm();

		List<OrderProduct> cart;
		cart = Session.use().getCurrentOrder().getItems();


		for(int i = 0 ; i < cart.size(); i++){
			final BooleanStatus status = new BooleanStatus(true);
			final OrderProduct prod = cart.get(i);
			LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View v = vi.inflate(R.layout.my_trolley_list, null);

			final TextView nameProdTag = (TextView) v.findViewById(R.id.prodName);
			final TextView priceTag = (TextView) v.findViewById(R.id.priceTrolley);
			final ImageButton removeTag = (ImageButton) v.findViewById(R.id.remove);
			ImageView btnBack = (ImageView) v.findViewById(R.id.btnBackground);

			nameProdTag.setText(prod.getMenuItem().getName());
			priceTag.setText("$" + prod.getMenuItem().getPrice() + " x" + prod.getQuantity());

			price.add(prod.getMenuItem().getPrice()*prod.getQuantity());

			final ViewGroup insertPoint = (ViewGroup) findViewById(R.id.container);
			insertPoint.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

			removeTag.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (status.getState()) {
						Session.use().getCurrentOrder().removeItem(prod);
						Toast.makeText(TrolleyActivity.this, ScreenMessages.PRODUCT_DELETED, Toast.LENGTH_SHORT).show();
						removeTag.setVisibility(View.INVISIBLE);
						//v.setVisibility(View.INVISIBLE);
						nameProdTag.setTextColor(Color.RED);
						priceTag.setTextColor(Color.RED);
						price.delete(prod.getMenuItem().getPrice() * prod.getQuantity());
						total.setText("$" + price);
						status.change();
					} else {
						Toast.makeText(TrolleyActivity.this, ScreenMessages.PRODUCT_HAS_DELETED, Toast.LENGTH_SHORT).show();
					}

				}
			});

			btnBack.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {

					final LayoutInflater inflater = getLayoutInflater();
					final View dialoglayout = inflater.inflate(R.layout.activity_message_for_description, null);

					final EditText cant = (EditText) dialoglayout.findViewById(R.id.cant);
					final EditText userDescription = (EditText) dialoglayout.findViewById(R.id.userdescription);
					final Button sendBtn = (Button) dialoglayout.findViewById(R.id.sendbtn);

					cant.setClickable(false);
					cant.setFocusable(false);
					userDescription.setClickable(false);
					userDescription.setFocusable(false);
					cant.setText("" + prod.getQuantity());

					sendBtn.setVisibility(View.INVISIBLE);

					userDescription.setText("" + prod.getComment());
					AlertDialog.Builder builder = new AlertDialog.Builder(TrolleyActivity.this);
					builder.setView(dialoglayout);
					builder.show();
				}
			});


		}

		total.setText("$" + price.getPrice());

		add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				final TimePicker timePicker = new TimePicker(TrolleyActivity.this);
				timePicker.setIs24HourView(true);
				timePicker.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
				timePicker.setCurrentMinute(cal.get(Calendar.MINUTE));

				new AlertDialog.Builder(TrolleyActivity.this)
						.setTitle(ScreenMessages.SET_TIME_TITLE)
						.setPositiveButton(ScreenMessages.ENTER, new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {

								Form form = FormBuilder.buildOrderForm();

								form.set(FieldKeys.KEY_HORARIO, String.format("%02d", timePicker.getCurrentHour()) + ":" + String.format("%02d", timePicker.getCurrentMinute()));
								form.set(FieldKeys.KEY_TOTAL, price.toString());
								form.set(FieldKeys.KEY_PAYMENT_TYPE, pay[0]);
								form.set(FieldKeys.KEY_COMMENT, "");
								form.set(FieldKeys.KEY_STATUS, "Enviada");

								if (form.isValid()) {

									ServiceRepository.getInstance().getOrderService().placeOrder(form, new SaveOrderCallback() {
										@Override
										public void success() {
											Toast.makeText(getApplicationContext(), ScreenMessages.SET_ORDER, Toast.LENGTH_SHORT).show();
										}

										@Override
										public void error(RemoteError e) {
											Toast.makeText(getApplicationContext(), ScreenMessages.ERROR, Toast.LENGTH_SHORT).show();
										}
									});
								} else {
									Toast.makeText(getApplicationContext(), form.collectErrors().toString(), Toast.LENGTH_SHORT).show();
								}
								startActivity(new Intent(getApplicationContext(),MainActivity.class));

							}
						})
						.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								Toast.makeText(getApplicationContext(), ScreenMessages.UNDERSTOOD, Toast.LENGTH_SHORT).show();
							}
						}).setView(timePicker).show();
			}
		});





		home.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), MainActivity.class));
			}
		});

		payment.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(statusPayment.getState()){
					Toast.makeText(TrolleyActivity.this, ScreenMessages.CASH,Toast.LENGTH_SHORT).show();
					//form.set(FieldKeys.KEY_PAYMENT_TYPE, ScreenMessages.CASH);
					pay[0] = "Efectivo";
					payment.setBackgroundResource(R.drawable.document);
					statusPayment.change();
				}else{
					Toast.makeText(TrolleyActivity.this, ScreenMessages.CREDIT,Toast.LENGTH_SHORT).show();
					payment.setBackgroundResource(R.drawable.money);
					//form.set(FieldKeys.KEY_PAYMENT_TYPE, ScreenMessages.CREDIT);
					statusPayment.change();
				}

			}
		});

	}


}
