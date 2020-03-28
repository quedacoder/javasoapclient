package com.quedacoder.ws.soap;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CustomerOrderWsClient {

	public static void main(String[] args) throws MalformedURLException {

		// Create the service stub by creating an instance of the service
		CustomerOrdersWsImplService service = new CustomerOrdersWsImplService(
				new URL("http://localhost:8080/wsdlfirstws/customerordersservice?wsdl"));
		CustomerOrdersPortType customerOrdersWsImplPort = service.getCustomerOrdersWsImplPort();

		// Get list of customer orders 
		System.out.println();
		BigInteger customerId = BigInteger.valueOf(1);
		getCustomerOrders(customerOrdersWsImplPort, customerId);

		// create customer order
		System.out.println();
		customerId = BigInteger.valueOf(2);
		createCustomerOrder(customerOrdersWsImplPort, customerId);
		
		// get the order for customer 2
		System.out.println();
		getCustomerOrders(customerOrdersWsImplPort, customerId);
		
	}

	private static void createCustomerOrder(CustomerOrdersPortType customerOrdersWsImplPort, BigInteger customerId) {
		CreateOrdersRequest request = new CreateOrdersRequest();

		// Create products for order
		List<Product> products = new ArrayList<>();

		// create the first line item
		Product lineItem1 = new Product();
		lineItem1.setId("2");
		lineItem1.setDescription("TV");
		lineItem1.setQuantity(BigInteger.valueOf(3));
		products.add(lineItem1);

		// create the second line item
		Product lineItem2 = new Product();
		lineItem2.setId("3");
		lineItem2.setDescription("HDMI Cable");
		lineItem2.setQuantity(BigInteger.valueOf(3));
		products.add(lineItem2);

		// Create a new order
		Order order = new Order();
		order.setId(BigInteger.valueOf(2));
		order.getProduct().addAll(products);

		// set the request parameters for create orders
		request.setCustomerId(customerId);
		request.setOrder(order);
		
		// Call the service to create an order using the request
		CreateOrdersResponse response = customerOrdersWsImplPort.createOrders(request);
		
		System.out.println();
		
		// get the response from the service
		if (response.isResult()) {
			System.out.println("Your order was created successfully");
		} else {
			System.out.println("Something happend and the order was not created");
		}
	}

	private static void getCustomerOrders(CustomerOrdersPortType customerOrdersWsImplPort, BigInteger customerId) {

		GetOrdersRequest request = new GetOrdersRequest();
		request.setCustomerId(customerId);

		GetOrdersResponse response = customerOrdersWsImplPort.getOrders(request);

		List<Order> orders = response.getOrder();

		// Loop through the orders and print out the products
		for (Order o : orders) {
			
			System.out.println();

			List<Product> products = o.getProduct();

			System.out.println("Order #: " + o.getId());

			for (Product p : products) {
				System.out.println("\tProduct Code: " + p.getId() + "\n\tProduct Description: " + p.getDescription()
						+ "\n\tOrdered Quantity: " + p.getQuantity());
			}
		}
	}

}
