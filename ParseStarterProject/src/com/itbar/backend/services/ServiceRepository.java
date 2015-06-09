package com.itbar.backend.services;


/**
 * El Service Repository reune a los servicios en un solo Singleton, porque estos son unicos, nunca
 * va a haber dos servicios iguales. Se implemento de esta manera despues de haber hablado con
 * Pablo. La idea fue para evitar caer en los llamados a metodos de clase a cada uno de los
 * servicios individuales. Ademas de ofrecernos la posibilidad de configurar un servicio para que
 * su uso dependa de su estado interno y que no tenga variables de clase que hablarian de una
 * conducta de la clase en si.
 *
 * @see BarService
 * @see UserService
 * @see OrderService
 * Created by ioninielavitzky on 5/28/15.
 */
public class ServiceRepository {

	private static ServiceRepository instance = null;

	private BarService barService;
	private MenuServices menuServices;
	private OrderService orderService;

	public ServiceRepository() {
		barService = new BarService();
		menuServices = new MenuServices();
		orderService = new OrderService();
	}

	public static ServiceRepository getInstance() {
		if (instance == null)
			instance = new ServiceRepository();
		return instance;
	}

    /*  Los setters no tienen sentido. No pueden cambiar los servicios, son unicos */

	public BarService getBarService() {
		return barService;
	}

//    public void setBarService(BarService barService) {
//        this.barService = barService;
//    }

	public MenuServices getMenuServices() {
		return menuServices;
	}

//    public void setMenuServices(MenuServices menuServices) {
//        this.menuServices = menuServices;
//    }

	public OrderService getOrderService() {
		return orderService;
	}

//    public void setOrderService(OrderService orderService) {
//        this.orderService = orderService;
//    }
}
