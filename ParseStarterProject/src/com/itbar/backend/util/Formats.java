package com.itbar.backend.util;

import java.util.regex.Pattern;

/**
 * Contiene los REGExp para validar los distintos campos en la app
 *
 * @see Field
 * Created by martin on 20/05/15.
 */
public interface Formats {

	Pattern EMAIL = Pattern.compile("^[-0-9a-zA-Z.+_]+@[-0-9a-zA-Z.+_]+\\.[a-zA-Z]{2,4}$");

	Pattern PHONE = Pattern.compile(".*");

	Pattern KEY_ID = Pattern.compile(".*");

	Pattern LEGAJO = Pattern.compile("^[1-9][0-9]{4}$");

	Pattern CUIT = Pattern.compile("\\d+");

	Pattern NAME = Pattern.compile(".*");

	Pattern NUMBER_INT = Pattern.compile("^(\\+|-)?([0-9]|[1-9][0-9])+$");
	Pattern NUMBER_DOUBLE = Pattern.compile("^(\\+|-)?([0-9]|[1-9][0-9])+((\\.|,)[0-9]+)?$");

	Pattern NUMBER_TWO_DECIMAL = Pattern.compile("^(\\+|-)?(0|[1-9][0-9]*)((\\.|,)[0-9]{2})?$");

	Pattern TIME24 = Pattern.compile("^([0-1][0-9]|2[0-3]):[0-5][0-9]$");
	Pattern TIME12 = Pattern.compile("^(0[1-9]|1[0-2]):[0-5][0-9]] (AM|PM|am|pm)$");

	/**
	 * Patron que valida los estados posibles de un pedido
	 * Solo estos son los posibles
	 */
	Pattern STATUS = Pattern.compile("^(Enviada|Cancelada|Archivada|Preparada)$");
	/**
	 * Este patron valida los posibles metodos de pago
	 * Solo estos son posibles
	 */
	Pattern PAYMENT_TYPE = Pattern.compile("^(Efectivo|Factura)$");
}