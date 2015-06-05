package com.itbar.backend.middleware;

import android.util.Log;

import com.itbar.backend.middleware.translators.UserTranslator;
import com.itbar.backend.services.RemoteError;
import com.itbar.backend.services.callbacks.UserLogInCallback;
import com.itbar.backend.services.callbacks.UserLogOutCallback;
import com.itbar.backend.services.callbacks.UserSignUpCallback;
import com.itbar.backend.services.views.User;
import com.parse.LogInCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


/**
 * Middleware de conexion con el proveedor de base de datos, en este caso Parse.
 *
 * Discucion:
 *
 * Un Middleware es el encargado de realizar operaciones en el proveedor de servicios. Es la capa
 * entre el proveedor de base de datos  y los servivios de la aplicacion. Cuenta con
 * las siguientes premisas:
 *  * La informacion recibida ya fue validada cuando proviene del lado del front
 *  * Tiene que funcionar como una capa: es decir para un lado tiene que hablar Parse y para el otro
 *    lenguaje app, de manera tal que si el dia de manana el proveedor de base de datos dejara de
 *    ser Parse entonces simplemente lo que se cambia es el codigo de conexion, y la capa de
 *    servicios permanece intacta
 *  * Lo anterior involucra que toda cuestion involucrada a la existencia de Parse se queda en esta
 *    capa, como todos los callbacks de Parse, modelos de Parse (ParseUser, ParseObject), los
 *    traductores y la Excepcion de Parse
 *
 * @see com.itbar.backend.util.Form
 * @see com.itbar.backend.util.FormBuilder
 *
 * Created by martin on 5/22/15.
 */
public class UserMiddleware {

	public static void loginUser(String legajo, String pass, final UserLogInCallback cb) {

		ParseUser.logInInBackground(legajo, pass, new LogInCallback() {
			@Override
			public void done(ParseUser user, ParseException e) {
				if (e != null) {
					cb.error(new RemoteError(e));
				} else {
					cb.success(UserTranslator.toUser(user));
				}
			}
		});
	}

	public static void signupUser(User user, String pass, final UserSignUpCallback cb) {

		ParseUser parseUser = UserTranslator.fromUser(user, pass);

		parseUser.signUpInBackground(new SignUpCallback() {
			@Override
			public void done(ParseException e) {
				if (e != null) {
					cb.error(new RemoteError(e));
				} else {
					cb.success();
				}
			}
		});

	}

	public static void logoutUser() {

		ParseUser.logOut();
	}

	public static void logoutUser(final UserLogOutCallback cb) {

		ParseUser.logOutInBackground(new LogOutCallback() {
			@Override
			public void done(ParseException e) {
				if (e != null) {
					cb.error(new RemoteError(e));
				} else {
					cb.success();
				}

			}
		});

	}

}
