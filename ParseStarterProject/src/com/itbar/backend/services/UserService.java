package com.itbar.backend.services;

import com.itbar.backend.middleware.UserMiddleware;
import com.itbar.backend.services.callbacks.UserLogInCallback;
import com.itbar.backend.services.callbacks.UserLogOutCallback;
import com.itbar.backend.services.callbacks.UserSignUpCallback;
import com.itbar.backend.util.FieldKeys;
import com.itbar.backend.util.Form;
import com.itbar.backend.services.views.User;

/**
 * Servicio que controla la informacion que envia el front para que luego sea enviada al servidor
 * a traves del Middleware.
 *
 * Discucion:
 *
 * Como en todo servicio la informacion que llega del front llega a traves de un Form para
 * garantizar que toda la informacion este validada y que no se meta basura en el sistema y para
 * ofrecer una base de informacion validada en el resto de la cadena para que no impida en la
 * logica subyacente
 *
 * Un servicio se comunica con la Session porque actua como intermediario entre los Middleware y el
 * front, y sabe las condiciones de la app:
 *  * solo un usuario puede estar loggeado
 *  * cada usuario (bar o alumno) puede realizar ciertas operaciones
 *  * existe solo un carrito abierto durante la app
 *
 * Por estas razones se encarga de comunicarse con la Session y de fijar un marco para que la
 * Session tenga la informacion correcta y validada, pues a traves del Form solo se obtiene
 * informacion validada.
 *
 *
 * @see Form
 * @see com.itbar.backend.util.FormBuilder
 * Created by martin on 21/05/15.
 */
public class UserService {

	public static void loginUser(Form form, final UserLogInCallback cb) {

		if (   Session.use().getCurrentUser() == null
				&& Session.use().getCurrentBar() == null /* Lo escribo asi porque va a desaparecer con el backoffice */
				&& (form.hasBeenValidated() || form.isValid())) {

			UserMiddleware.loginUser(form.get(FieldKeys.KEY_LEGAJO), form.get(FieldKeys.KEY_PASSWORD), new UserLogInCallback() {
						@Override
						public void success(User user) {
							Session.use().setCurrentUser(user);
							cb.success(user);
						}

						@Override
						public void error(RemoteError e) {
							cb.error(e);
						}
					}
			);



		} else {
			if (   Session.use().getCurrentUser() == null
					&& Session.use().getCurrentBar() == null) {
				cb.error(new RemoteError(RemoteError.ALREADY_LOGGED_IN, "Ya inicio sesion"));
			} else {
				cb.error(new RemoteError(RemoteError.INVALID_FORM, "Formulario invalido"));
			}
		}
	}

	public static void signupUser(Form form, final UserSignUpCallback cb) {

		if (   Session.use().getCurrentUser() == null
				&& Session.use().getCurrentBar() == null
				&& (form.hasBeenValidated() || form.isValid()) ) {

			final User user = new User(form);

			UserMiddleware.signupUser(user, form.get(FieldKeys.KEY_PASSWORD), new UserSignUpCallback() {
				@Override
				public void success() {
					Session.use().setCurrentUser(user);
					cb.success();
				}

				@Override
				public void error(RemoteError e) {
					cb.error(e);
				}
			});
		} else {
			if (   Session.use().getCurrentUser() == null
					&& Session.use().getCurrentBar() == null) {
				cb.error(new RemoteError(RemoteError.ALREADY_LOGGED_IN, "Ya inicio sesion"));
			} else {
				cb.error(new RemoteError(RemoteError.INVALID_FORM, "Formulario invalido"));
			}
		}
	}

	public static void logoutUser(final UserLogOutCallback cb) {

		if (Session.use().getCurrentUser() != null) {

			UserMiddleware.logoutUser(new UserLogOutCallback() {
				@Override
				public void success() {
					Session.use().setCurrentUser(null);
					Session.use().setCurrentOrder(null);
					cb.success();
				}

				@Override
				public void error(RemoteError e) {
					cb.error(e);
				}
			});
		} else {

			cb.error(new RemoteError(RemoteError.NO_SUCH_USER, "No hay usuario loggeado"));

		}

	}

}
