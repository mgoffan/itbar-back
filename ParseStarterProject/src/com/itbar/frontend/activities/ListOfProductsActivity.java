package com.itbar.frontend.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.itbar.R;
import com.itbar.backend.services.ServiceRepository;
import com.itbar.backend.services.Session;
import com.itbar.backend.services.views.Category;
import com.itbar.backend.services.views.User;
import com.itbar.frontend.Models.ScreenMessages;

public class ListOfProductsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_products);
        drawUI((Category) this.getIntent().getSerializableExtra("category"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_of_products, menu);
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
            int  total = Session.use().getCurrentOrder().getItems().size();
            if ( total != 0) {
                startActivity(new Intent(getApplicationContext(), TrolleyActivity.class));
            }else {
                Toast.makeText(ListOfProductsActivity.this, "Carrito vacio", Toast.LENGTH_SHORT).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void drawUI(final Category category){
        setTitle(category.getName());
        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (final com.itbar.backend.services.views.MenuItem product : category.getItems()){
            View v = vi.inflate(R.layout.product_list, null);
            TextView nameProd = (TextView) v.findViewById(R.id.prodName);
            final ImageButton seeDesc = (ImageButton) v.findViewById(R.id.seeDesc);
            final ImageView background = (ImageView) v.findViewById(R.id.background);


            nameProd.setText(product.getName());

            seeDesc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LayoutInflater inflater = getLayoutInflater();
                    View dialoglayout = inflater.inflate(R.layout.activity_message_for_description, null);

                    final EditText cant = (EditText) dialoglayout.findViewById(R.id.cant);
                    final EditText userDescription = (EditText) dialoglayout.findViewById(R.id.userdescription);
                    final Button sendBtn = (Button) dialoglayout.findViewById(R.id.sendbtn);

                    AlertDialog.Builder builder = new AlertDialog.Builder(ListOfProductsActivity.this);
                    builder.setView(dialoglayout);
                    builder.show();


                    sendBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Preguntar que es lo que pasa acaaaaaaa!!!
                            if(!cant.getText().toString().equals("")|| !cant.getText().toString().equals("0")) {
                                Toast n = new Toast(ListOfProductsActivity.this);
                                n.makeText(ListOfProductsActivity.this, "Agregado", Toast.LENGTH_SHORT).show();
                                ServiceRepository.getInstance().getOrderService().addItem(product, userDescription.getText().toString(), Integer.parseInt(cant.getText().toString()));
                                User u = Session.use().getCurrentUser();
                                Intent i = new Intent(getApplicationContext(), TrolleyActivity.class);
                                startActivity(i);
                            }else{
                                Toast.makeText(getApplicationContext(), ScreenMessages.ERROR,Toast.LENGTH_SHORT ).show();
                            }

                        }
                    });

                }
            });

            background.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), ProductDescriptionActivity.class);
                    intent.putExtra("menuitem", product);
                    background.setBackgroundResource(R.drawable.pressed_greybutton);
                    startActivity(intent);
                }
            });

            ViewGroup insertPoint = (ViewGroup) findViewById(R.id.productcontainer);
            insertPoint.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }


    }




}
