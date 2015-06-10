package com.itbar.frontend.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.itbar.R;
import com.itbar.backend.services.BarService;
import com.itbar.backend.services.RemoteError;
import com.itbar.backend.services.ServiceRepository;
import com.itbar.backend.services.callbacks.FindMultipleCallback;
import com.itbar.backend.services.views.Category;
import com.itbar.backend.services.views.MenuItem;

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

				for (Category category : objects) {

					for (MenuItem menuItem : category.getItems()) {

					}

				}
			}

			@Override
			public void error(RemoteError e) {
				// Toast con error aca
			}
		});

	}
}
