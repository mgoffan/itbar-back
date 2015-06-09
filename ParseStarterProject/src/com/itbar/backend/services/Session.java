package com.itbar.backend.services;

import com.itbar.backend.services.views.Bar;
import com.itbar.backend.services.views.Order;
import com.itbar.backend.services.views.User;

/**
 * Singleton que reune la informacion universal en el ciclo de vida de la app
 *
 * Discucion:
 *
 * Se eligio el patron Singleton porque puede haber solamente un usuario loggeado en la app, nunca
 * van a haber dos usuarios loggeados a la vez. La sesion adoptara la informacion correspondiente
 * a cada usuario
 *
 * Created by martin on 5/23/15.
 */
public class Session {

	private static Session instance = null;

	private Bar currentBar = null;

	private Session() {

	}

	public static Session use() {
		if (instance == null) {
			instance = new Session();
		}
		return instance;
	}

	public Bar getCurrentBar() {
		return currentBar;
	}

	protected void setCurrentBar(Bar currentBar) {
		this.currentBar = currentBar;
	}

}
