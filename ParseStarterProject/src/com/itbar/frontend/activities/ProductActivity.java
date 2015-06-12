package com.itbar.frontend.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.KeyEvent;
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
import com.itbar.backend.services.RemoteError;
import com.itbar.backend.services.ServiceRepository;
import com.itbar.backend.services.callbacks.SaveMenuItemCallback;
import com.itbar.backend.services.views.Category;
import com.itbar.backend.util.Field;
import com.itbar.backend.util.FieldKeys;
import com.itbar.backend.util.Form;
import com.itbar.backend.util.FormBuilder;
import com.itbar.backend.util.Formats;

import java.lang.reflect.Array;
import java.util.regex.Pattern;

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

	private void drawUI(final Category category) {

		LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		for (final com.itbar.backend.services.views.MenuItem item : category.getItems()) {
			View v = vi.inflate(R.layout.products_view, null);
			TextView prodName = (TextView) v.findViewById(R.id.prodName);
			TextView price = (TextView) v.findViewById(R.id.priceProd);
			ImageButton ok = (ImageButton) v.findViewById(R.id.ok);
			ImageButton no = (ImageButton) v.findViewById(R.id.no);

			prodName.setText(item.getName());
			price.setText("$" + String.format("%.02f", item.getPrice()).replace(",","."));

			ViewGroup insertPoint = (ViewGroup) findViewById(R.id.scrollProds);
			insertPoint.addView(v);//new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


			ok.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//Me abre un builder propio a partir de lo que tengo en product_layout
					LayoutInflater inflater = getLayoutInflater();
					View dialoglayout = inflater.inflate(R.layout.product_layout, null);

					final EditText name = (EditText) dialoglayout.findViewById(R.id.name);
					final EditText price = (EditText) dialoglayout.findViewById(R.id.price);
					price.setFilters(new InputFilter[]{
								                                  new DigitsKeyListener(Boolean.FALSE, Boolean.TRUE) {
									                                  int afterDecimal = 2;

									                                  @Override
									                                  public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
										                                  String temp = price.getText().toString() + source.toString();

										                                  if (temp.equals(".")) {
											                                  return "0.";
										                                  } else if (temp.contains(".")) {
											                                  temp = temp.substring(temp.indexOf(".") + 1);
											                                  if (temp.length() > afterDecimal) {
												                                  return "";
											                                  }
										                                  }

										                                  return super.filter(source, start, end, dest, dstart, dend);
									                                  }
								                                  }

					});
					final EditText desc = (EditText) dialoglayout.findViewById(R.id.desc);
					Button ok = (Button) dialoglayout.findViewById(R.id.ok);

					name.setText(item.getName());
					price.setText(String.format("%.02f", item.getPrice()).replace(",","."));
					desc.setText(item.getDescription());

					ok.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							Toast.makeText(getApplicationContext(), "Cambios aplicados", Toast.LENGTH_SHORT).show();

							Form form = FormBuilder.buildMenuItemForm();

							form.set(FieldKeys.KEY_NAME, name.getText().toString());
							form.set(FieldKeys.KEY_PRICE, price.getText().toString());
							form.set(FieldKeys.KEY_DESCRIPTION, desc.getText().toString());
							form.set(FieldKeys.KEY_ID, item.getObjectId());
							form.set(FieldKeys.KEY_CATEGORY, category.getObjectId());

										        ServiceRepository.getInstance().getBarService().updateMenuItem(form, new SaveMenuItemCallback() {
											        @Override
											        public void success(com.itbar.backend.services.views.MenuItem menuItem) {
												        Log.v("APP123", menuItem.getName());
												        Toast.makeText(getApplicationContext(), "Yaay", Toast.LENGTH_SHORT);
											        }

											        @Override
											        public void error(RemoteError error) {
												        Log.v("APP123", error.getCode() + "");
												        Log.v("APP123", error.getMessage());
												        error.printStackTrace();
											        }
										        });
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
