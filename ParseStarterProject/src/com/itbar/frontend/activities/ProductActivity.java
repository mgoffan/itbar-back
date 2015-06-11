package com.itbar.frontend.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.itbar.R;
import com.itbar.backend.services.views.Category;

import java.lang.reflect.Array;

public class ProductActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product);

		drawUI((Category) this.getIntent().getSerializableExtra("category"));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_product, menu);
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

	private void drawUI(Category category) {

		LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		for (final com.itbar.backend.services.views.MenuItem item : category.getItems()){
			View v = vi.inflate(R.layout.products_view, null);
			ViewGroup insertPoint = (ViewGroup) findViewById(R.id.scrollProds);
			insertPoint.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

			TextView prodName = (TextView) findViewById(R.id.prodName);
			TextView price = (TextView) findViewById(R.id.priceProd);
			ImageButton ok = (ImageButton) findViewById(R.id.ok);
			ImageButton no = (ImageButton) findViewById(R.id.no);

			prodName.setText( item.getName() );
			price.setText("$"+item.getPrice());

			ok.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//Me abre un builder propio a partir de lo que tengo en product_layout
					LayoutInflater inflater = getLayoutInflater();
					View dialoglayout = inflater.inflate(R.layout.product_layout, null);

					EditText name = (EditText) findViewById(R.id.name);
					EditText price = (EditText) findViewById(R.id.price);
					EditText desc = (EditText) findViewById(R.id.desc);
					Button ok = (Button) findViewById(R.id.ok);

					name.setText(item.getName());
					price.setText("$"+item.getPrice());
					desc.setText(item.getDescription());

					ok.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							Toast.makeText(getApplicationContext(),"Cambios aplicados",Toast.LENGTH_SHORT).show();
						}
					});


					AlertDialog.Builder builder = new AlertDialog.Builder(ProductActivity.this);
					builder.setView(dialoglayout);
					builder.show();

				}
			});

			no.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//Borrar de Parse! - O sea, cambiar el estado
				}
			});



		}

		

	}
}
