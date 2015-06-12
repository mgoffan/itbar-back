package com.itbar.frontend.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itbar.R;
import com.itbar.backend.services.RemoteError;
import com.itbar.backend.services.ServiceRepository;
import com.itbar.backend.services.callbacks.FindMultipleCallback;
import com.itbar.backend.services.callbacks.RUDCallback;
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

	private void drawUI() {

		ServiceRepository.getInstance().getBarService().getMenus(new FindMultipleCallback<Category>() {
			@Override
			public void success(List<Category> objects) {
				// objects tiene el listado de categorias con sus productos adentro
				LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

				for (final Category category : objects) {

					View v = vi.inflate(R.layout.category_viewer, null);
					TextView cat = (TextView) v.findViewById(R.id.categoryView);
					Button editButton = (Button) v.findViewById(R.id.deleteBtn);
					Button prodButton = (Button) v.findViewById(R.id.prodBtn);

					cat.setText( category.getName() );


					LinearLayout insertPoint = (LinearLayout) findViewById(R.id.scrollCategory);
					insertPoint.addView(v);
					//, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

					editButton.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							Toast.makeText(getApplicationContext(), "Hay que borrarlo", Toast.LENGTH_SHORT).show();

							Form form = FormBuilder.buildCategoryForm();

							form.set(FieldKeys.KEY_ID, category.getObjectId());
							form.set(FieldKeys.KEY_NAME, category.getName());

							if (form.isValid()) {

								ServiceRepository.getInstance().getBarService().removeCategory(form, new RUDCallback() {
									@Override
									public void success() {
										Toast.makeText(getApplicationContext(), "Yaay", Toast.LENGTH_LONG).show();
										/** TODO: Eliminar la fila entera */
									}

									@Override
									public void error(RemoteError e) {
										Log.v("APP123", e.getCode() + "");
										Log.v("APP123", e.getMessage());
										e.printStackTrace();
									}
								});
							} else {
								Log.v("APP123", form.collectErrors().toString());
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
			}

			@Override
			public void error(RemoteError e) {
				// Toast con error aca
				Log.v("APP123", e.getCode()+ "");
				Log.v("APP123", e.getMessage());
				e.printStackTrace();
			}
		});

	}
}
