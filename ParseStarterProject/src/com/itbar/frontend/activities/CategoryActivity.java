package com.itbar.frontend.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itbar.R;
import com.itbar.backend.services.RemoteError;
import com.itbar.backend.services.ServiceRepository;
import com.itbar.backend.services.callbacks.FindMultipleCallback;
import com.itbar.backend.services.callbacks.RUDCallback;
import com.itbar.backend.services.callbacks.SaveCategoryCallback;
import com.itbar.backend.services.views.Category;
import com.itbar.backend.util.FieldKeys;
import com.itbar.backend.util.Form;
import com.itbar.backend.util.FormBuilder;

import java.util.List;

public class CategoryActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category);

		drawUI();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_category, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item) {
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

	private void drawCategory(final Category category) {

		LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		final View v = vi.inflate(R.layout.category_viewer, null);
		TextView cat = (TextView) v.findViewById(R.id.categoryView);
		Button deleteBtn = (Button) v.findViewById(R.id.deleteBtn);
		Button prodButton = (Button) v.findViewById(R.id.prodBtn);

		cat.setText( category.getName() );


		final LinearLayout insertPoint = (LinearLayout) findViewById(R.id.scrollCategory);
		insertPoint.addView(v);

		deleteBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				Form form = FormBuilder.buildCategoryForm();

				form.set(FieldKeys.KEY_ID, category.getObjectId());
				form.set(FieldKeys.KEY_NAME, category.getName());

				if (form.isValid()) {

					ServiceRepository.getInstance().getBarService().removeCategory(form, new RUDCallback() {
						@Override
						public void success() {
							Toast.makeText(getApplicationContext(), ScreenMessages.OK, Toast.LENGTH_LONG).show();
							/** TODO: Eliminar la fila entera */
							insertPoint.removeView(v);
						}

						@Override
						public void error(RemoteError e) {
							Toast.makeText(CategoryActivity.this,ScreenMessages.OOPS,Toast.LENGTH_SHORT).show();
							e.printStackTrace();
						}
					});
				} else {
					Toast.makeText(CategoryActivity.this,form.collectErrors().toString(),Toast.LENGTH_SHORT).show();
				}
			}
		});

		prodButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
				intent.putExtra("category", category);
				startActivity(intent);
			}
		});

	}

	private void drawUI() {

		ImageButton addCategory = (ImageButton) findViewById(R.id.addCategory);

		addCategory.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				LayoutInflater inflater = getLayoutInflater();
				View dialoglayout = inflater.inflate(R.layout.category_layout, null);

				final EditText nameCategory = (EditText) dialoglayout.findViewById(R.id.name);
				final Button okBtn = (Button) dialoglayout.findViewById(R.id.okBtn);

				okBtn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Form form = FormBuilder.buildCategoryForm();
						form.set(FieldKeys.KEY_NAME, nameCategory.getText().toString());

						ServiceRepository.getInstance().getBarService().addCategory(form, new SaveCategoryCallback() {
							@Override
							public void success(Category category) {
								Toast.makeText(getApplicationContext(),ScreenMessages.ADDED,Toast.LENGTH_SHORT).show();
								drawCategory(category);
							}

							@Override
							public void error(RemoteError error) {
								Toast.makeText(getApplicationContext(),ScreenMessages.OOPS,Toast.LENGTH_SHORT).show();
							}
						});

					}
				});

				AlertDialog.Builder builder = new AlertDialog.Builder(CategoryActivity.this);
				builder.setView(dialoglayout);
				builder.show();



			}
		});


		ServiceRepository.getInstance().getBarService().getMenus(new FindMultipleCallback<Category>() {
			@Override
			public void success(List<Category> objects) {
				// objects tiene el listado de categorias con sus productos adentro
				LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

				for (Category category : objects) {

					drawCategory(category);

				}
			}

			@Override
			public void error(RemoteError e) {
				// Toast con error aca
				Toast.makeText(getApplicationContext(),ScreenMessages.OOPS,Toast.LENGTH_SHORT).show();
			}
		});

	}
}
