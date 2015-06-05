package com.itbar.frontend.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.itbar.R;
import com.itbar.backend.services.RemoteError;
import com.itbar.backend.services.ServiceRepository;
import com.itbar.backend.services.Session;
import com.itbar.backend.services.callbacks.FindMultipleCallback;
import com.itbar.backend.services.views.Category;
import com.itbar.frontend.appmanager.Counter;

import java.util.List;


public class MainActivity extends Activity {

	public static final String KEY_PROMO = "Promos";
	public static List<Category> categories = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		userUI();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			int  total = Session.use().getCurrentOrder() == null ? 0 : Session.use().getCurrentOrder().getItems().size();
			if ( total != 0) {
				startActivity(new Intent(getApplicationContext(), TrolleyActivity.class));
			}else {
				Toast.makeText(MainActivity.this, "Carrito vacio", Toast.LENGTH_SHORT).show();
			}
		} else if (id == R.id.actiontwo) {
			startActivity(new Intent(getApplicationContext(), SuggestActivity.class));
		} else if (id == R.id.actionthree) {
			Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://itba.edu.ar"));
			startActivity(i);
		}


		return super.onOptionsItemSelected(item);
	}

	public void userUI() {

		setTitle("ITBAr");
		ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView2);
		scrollView.setVerticalFadingEdgeEnabled(false);
		scrollView.setScrollContainer(false);

		ServiceRepository.getInstance().getBarService().getMenus(new FindMultipleCallback<Category>() {
			@Override
			public void success(List<Category> objects) {
				Log.v("APP123", objects.toString());
//
				categories = objects;

				for (final Category c : objects) {

					if (c.getName().equals(KEY_PROMO)) {
						Counter cont = new Counter();
						// Mostrar los productos de la promo
						LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
						for (final com.itbar.backend.services.views.MenuItem item : c.getItems()) {
							View v = vi.inflate(R.layout.menu_view, null);
							TextView textView = (TextView) v.findViewById(R.id.title);
							textView.setText(item.getName());
							Button picBtn = (Button) v.findViewById(R.id.picBtn);

							cont.add();
							int toMod = cont.moduleFour();
							switch (toMod) {
								case 0:
									picBtn.setBackgroundResource(R.drawable.new_menu1);
									break;
								case 1:
									picBtn.setBackgroundResource(R.drawable.new_menu2);
									break;
								case 2:
									picBtn.setBackgroundResource(R.drawable.new_menu3);
									break;
								case 3:
									picBtn.setBackgroundResource(R.drawable.new_menu4);
									break;
							}

							picBtn.setOnClickListener(new View.OnClickListener() {

								@Override
								public void onClick(View v) {
									Intent intent = new Intent(getApplicationContext(), ProductDescriptionActivity.class);
									intent.putExtra("menuitem", item);

									startActivity(intent);
									overridePendingTransition(R.animator.animation1, R.animator.animation2);
								}
							});

							ViewGroup insertPoint = (ViewGroup) findViewById(R.id.menuContainer);
							insertPoint.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
						}
					} else {


						LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
						View v = vi.inflate(R.layout.category_view, null);

						// fill in any details dynamically here
						TextView textView = (TextView) v.findViewById(R.id.textfromcategory);
						textView.setText(c.getName());
						ImageButton picBtn = (ImageButton) v.findViewById(R.id.catbtn);

						// insert into main view
						ViewGroup insertPoint = (ViewGroup) findViewById(R.id.categoryContainer);
						insertPoint.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

						picBtn.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								Intent i = new Intent(getApplicationContext(), ListOfProductsActivity.class);

								i.putExtra("category", c);


								startActivity(i);
							}
						});
					}
				}
			}

			@Override
			public void error(RemoteError e) {
			}
		});

	}


}
