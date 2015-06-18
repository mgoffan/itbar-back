package com.itbar.frontend.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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
import com.itbar.backend.services.views.Order;
import com.itbar.backend.services.views.OrderProduct;
import com.itbar.backend.util.FieldKeys;
import com.itbar.backend.util.Form;
import com.itbar.backend.util.FormBuilder;

import java.util.List;

public class ProductOrderActivity extends Activity {

	private String order;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_order);

		this.order = (String) this.getIntent().getSerializableExtra("orderID");

		drawUI();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_product_order, menu);
		return true;
	}

	public void drawUI() {

		Form form = FormBuilder.buildProductForOrderForm();
		form.set(FieldKeys.KEY_ID, order);

		ServiceRepository.getInstance().getOrderService().getProductsForOrder(form, new FindMultipleCallback<OrderProduct>() {
			@Override
			public void success(List<OrderProduct> objects) {
				LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

				for (OrderProduct prod : objects) {
					drawProd(prod);
				}

			}

			@Override
			public void error(RemoteError e) {
				Toast.makeText(getApplicationContext(), ScreenMessages.OOPS, Toast.LENGTH_SHORT).show();
			}
		});

	}

	public void drawProd(OrderProduct order) {
		LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		final View v = vi.inflate(R.layout.product_order_layout, null);

		TextView nameProd = (TextView) v.findViewById(R.id.prodName);

		if (order.getMenuItem() != null)
			nameProd.setText(order.getMenuItem().getName());
		else
			nameProd.setText(ScreenMessages.DELETED_PROD);

		final LinearLayout insertPoint = (LinearLayout) findViewById(R.id.scrollOrderProducrt);
		insertPoint.addView(v);


	}


}
