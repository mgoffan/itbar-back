package com.itbar.backend.util;

import com.itbar.backend.util.fields.CuitField;
import com.itbar.backend.util.fields.EmailField;
import com.itbar.backend.util.fields.LegajoField;
import com.itbar.backend.util.fields.NameField;
import com.itbar.backend.util.fields.NumberField;
import com.itbar.backend.util.fields.PasswordField;
import com.itbar.backend.util.fields.PhoneField;
import com.itbar.backend.util.fields.TextField;
import com.itbar.backend.util.fields.TimeField;
import com.itbar.backend.util.fields.Validator;

/**
 * <p>
 * El FormBuilder se utiliza para crear formularios predeterminados para las operaciones a lo largo
 * y ancho de la app. Garantiza que los formularios sean exactamente como se necesitan.
 * Se construyen en los activities y se leen en los Services</p>
 * <h4>Ejemplo De Uso:</h4>
 * <code>Form form = FormBuilder.buildBarLoginForm()</code>
 * @see com.itbar.frontend.activities.EnterActivity
 * Created by martin on 21/05/15.
 */
public class FormBuilder {

	/**
	 * Construye un formulario para crear un usuario
	 *
	 * @return Form el formulario que corresponde a la creacion de un usuario
	 */
	public static Form buildUserForm() {
		final Form form = new Form();
		form.addField(FieldKeys.KEY_LEGAJO, new LegajoField(true));
		form.addField(FieldKeys.KEY_EMAIL, new EmailField(true));
		form.addField(FieldKeys.KEY_PHONE, new PhoneField());
		form.addField(FieldKeys.KEY_NAME, new NameField(true));
		form.addField(FieldKeys.KEY_SURNAME, new NameField(true));
		form.addField(FieldKeys.KEY_ID, new TextField());
		return form;
	}

	/**
	 * Construye el formulario para hacer el login de un Bar
	 *
	 * @return Form un formulario para loggear a un Bar
	 */
	public static Form buildBarLoginForm() {
		Form form = new Form();
		form.addField(FieldKeys.KEY_CUIT, new CuitField(true));
		form.addField(FieldKeys.KEY_PASSWORD, new PasswordField(true));
		return form;
	}

	/**
	 * Formulario que contiene la informacion de un bar
	 * @return Form
	 */
	public static Form buildBarForm() {
		Form form = new Form();
		form.addField(FieldKeys.KEY_CUIT, new CuitField(true));
		form.addField(FieldKeys.KEY_EMAIL, new EmailField());
		form.addField(FieldKeys.KEY_NAME, new NameField(true));
		form.addField(FieldKeys.KEY_PHONE, new PhoneField());
		form.addField(FieldKeys.KEY_ID, new TextField(true));
		return form;
	}

	/**
	 * Formulario utilizado para que un bar pueda inscribirse en el sistema
	 *
	 * @return Form
	 * @see com.itbar.backend.services.BarService
	 * @deprecated No se utiliza mas pues el unico bar en esta App es el Eatbar
	 */
	public static Form buildBarSignupForm() {
		final Form form = new Form();
		form.addField(FieldKeys.KEY_CUIT, new CuitField(true));
		form.addField(FieldKeys.KEY_EMAIL, new EmailField(true));
		form.addField(FieldKeys.KEY_PHONE, new PhoneField());
		form.addField(FieldKeys.KEY_NAME, new NameField(true));
		form.addField(FieldKeys.KEY_PASSWORD, new PasswordField(true));
		form.addField(FieldKeys.KEY_REPEAT_PASSWORD, new PasswordField(true));
		form.setInterFieldValidation(new InterFieldValidation() {
			@Override
			public Boolean validateForm() {
				return form.get(FieldKeys.KEY_PASSWORD).equalsIgnoreCase(form.get(FieldKeys.KEY_REPEAT_PASSWORD));
			}
		});
		return form;
	}

	/**
	 * Crea el formulario utilizado para crear una nueva categoria de productos Ej: Desayuno o promos
	 *
	 * @return Form formulario para crear una categoria
	 * @see com.itbar.backend.services.BarService
	 */
	public static Form buildCategoryForm() {
		Form form = new Form();
		form.addField(FieldKeys.KEY_NAME, new NameField(true));
		form.addField(FieldKeys.KEY_ID, new TextField());
		return form;
	}

	/**
	 * Construye el formulario designado para ingresar un nuevo item en un menu
	 *
	 * Requiere un nombre y un precio
	 *
	 * @return Form el formulario para crear un nuevo producto
	 * @see com.itbar.backend.services.BarService
	 */
	public static Form buildMenuItemForm() {
		Form form = new Form();
		form.addField(FieldKeys.KEY_NAME, new NameField(true));
		form.addField(FieldKeys.KEY_DESCRIPTION, new TextField());
		form.addField(FieldKeys.KEY_PRICE, (new NumberField<Double>(true)).setHasFloatingPoint(true));
		form.addField(FieldKeys.KEY_ID, new TextField());
		form.addField(FieldKeys.KEY_CATEGORY, new TextField());
		return form;
	}

	/**
	 *
	 * Construye un formulario para concretar un pedido en el servidor.
	 *
	 * Exige un horario, medio de pago, estado, y un total
	 *
	 * @return Form el formulario para colocar un pedido en el servidor
	 * @see Formats
	 * @see com.itbar.backend.services.OrderService
	 */
	public static Form buildOrderForm() {
		Form form = new Form();
		form.addField(FieldKeys.KEY_HORARIO, new TimeField(true));
		form.addField(FieldKeys.KEY_PAYMENT_TYPE, new TextField(true, new Validator() {
			@Override
			public Boolean isValid(String val) {
				return Formats.PAYMENT_TYPE.matcher(val).matches();
			}

			@Override
			public String errorDescription() {
				return "Tipo de pago incorrecto";
			}
		}));
		form.addField(FieldKeys.KEY_COMMENT, new TextField());
		form.addField(FieldKeys.KEY_STATUS, new TextField(true, new Validator() {
			@Override
			public Boolean isValid(String val) {
				return Formats.STATUS.matcher(val).matches();
			}

			@Override
			public String errorDescription() {
				return "Estado incorrecto";
			}
		}));
		form.addField(FieldKeys.KEY_TOTAL, (new NumberField<Double>(true)).setHasFloatingPoint(true));
		return form;
	}

	/**
	 * Construye un formulario para cancelar un pedido por identificador
	 *
	 * @return Form el formulario para cancelar un pedido
	 * @see com.itbar.backend.services.OrderService
	 */

	public static Form buildCancelOrderForm() {
		Form form = new Form();
		form.addField(FieldKeys.KEY_ID, new TextField());
		return form;
	}

	/**
	 * Construye un formulario para cancelar un pedido por identificador
	 *
	 * @return Form el formulario para cancelar un pedido
	 * @see com.itbar.backend.services.OrderService
	 */

	public static Form buildOrderStatusChangeForm() {
		Form form = new Form();
		form.addField(FieldKeys.KEY_ID, new TextField(true));
		form.addField(FieldKeys.KEY_STATUS, new TextField(true, new Validator() {
			@Override
			public Boolean isValid(String val) {
				return Formats.STATUS.matcher(val).matches();
			}

			@Override
			public String errorDescription() {
				return "Estado incorrecto";
			}
		}));
		return form;
	}

	/**
	 * Construye un formulario para realizar busquedas de pedidos por estado
	 * No exige que el estado sea un campo requerido, en tal caso traera todos los pedidos.
	 *
	 * @return Form el formulario para buscar pedidos por estado en el servidor
	 * @see Formats
	 */
	public static Form buildGetOrdersForm() {
		Form form = new Form();
		form.addField(FieldKeys.KEY_STATUS, new TextField(false, new Validator() {
			@Override
			public Boolean isValid(String val) {
				return Formats.STATUS.matcher(val).matches();
			}

			@Override
			public String errorDescription() {
				return "Estado incorrecto";
			}
		}));
		return form;
	}

	public static Form buildProductForOrderForm() {
		Form form = new Form();
		form.addField(FieldKeys.KEY_ID, new TextField(true));
		return form;
	}
}
