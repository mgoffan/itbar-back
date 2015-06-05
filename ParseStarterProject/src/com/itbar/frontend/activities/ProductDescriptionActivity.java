package com.itbar.frontend.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.itbar.R;
import com.itbar.backend.services.ServiceRepository;
import com.itbar.backend.services.Session;
import com.itbar.backend.services.views.MenuItem;
import com.itbar.backend.services.views.User;

public class ProductDescriptionActivity extends Activity {

	boolean buyClicked = true; //habilitado para comprar
	boolean detailsClicked = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_description);
		userUI((MenuItem) this.getIntent().getSerializableExtra("menuitem"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_product_description, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item) {
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			int  total = Session.use().getCurrentOrder().getItems().size();
			if ( total != 0) {
				startActivity(new Intent(getApplicationContext(), TrolleyActivity.class));
			}else {
				Toast.makeText(ProductDescriptionActivity.this, "Carrito vacio", Toast.LENGTH_SHORT).show();
			}		}else if ( id == R.id.tell_friend){
			Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "", null));
			MenuItem prod = (MenuItem) this.getIntent().getSerializableExtra("menuitem");
			emailIntent.putExtra(Intent.EXTRA_SUBJECT, prod.getName());
			emailIntent.putExtra(Intent.EXTRA_TEXT,"Acabo de pedir " + prod.getName() + ". Descripcion: " + prod.getDescription()  + " por un total de $" + prod.getPrice() + " *** ITBAr 2015 - Proyecto de POO ***");
			startActivity(Intent.createChooser(emailIntent, "Enviar"));
		}
		return super.onOptionsItemSelected(item);
	}

	public void userUI(final MenuItem item) {

        /* Declaracion de objetos del XML y de cosas que utilizaremos, por ejemplo, el TimePickerDialog */
		TextView nameProduct = (TextView) findViewById(R.id.nameproduct);
		TextView descriptionProduct = (TextView) findViewById(R.id.productdescription);
		TextView priceProduct = (TextView) findViewById(R.id.productprice);
		nameProduct.setSingleLine(true);
		//setHorizontalFadingEdgeEnabled

		ImageView imageProduct = (ImageView) findViewById(R.id.imgproduct);

		final ImageButton add = (ImageButton) findViewById(R.id.addbtn);

        /*Fin de declaracion de objetos*/

        /* Comienzo de seteo de texto */
		setTitle(item.getName());
		nameProduct.setText(item.getName());
		//OJO PORQUE LOS TAMANIOS VARIAN Y PODEMOS TENER DEFASAJE DE ABSOLUTAMENTE TODO EL SCREEN
		nameProduct.setTextSize(40);
		descriptionProduct.setText(item.getDescription());
		priceProduct.setText("$" + item.getPrice());
	     /*Fin del seteo*/

        /*Comienzo de las acciones de los botones*/
		add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				LayoutInflater inflater = getLayoutInflater();
				View dialoglayout = inflater.inflate(R.layout.activity_message_for_description, null);

				final EditText cant = (EditText) dialoglayout.findViewById(R.id.cant);
				final EditText userDescription = (EditText) dialoglayout.findViewById(R.id.userdescription);
				final Button sendBtn = (Button) dialoglayout.findViewById(R.id.sendbtn);

				AlertDialog.Builder builder = new AlertDialog.Builder(ProductDescriptionActivity.this);
				builder.setView(dialoglayout);
				builder.show();

				sendBtn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Toast n = new Toast(ProductDescriptionActivity.this);
						n.makeText(ProductDescriptionActivity.this,"Agregado",Toast.LENGTH_SHORT).show();
						ServiceRepository.getInstance().getOrderService().addItem(item, userDescription.getText().toString(), Integer.parseInt(cant.getText().toString()));
						User u = Session.use().getCurrentUser();
						Intent i = new Intent(getApplicationContext(), TrolleyActivity.class);
						startActivity(i);
						add.setBackgroundResource(R.drawable.error);
					}
				});

			}
		});


        /*Fin de las acciones de los botones*/

	}

}