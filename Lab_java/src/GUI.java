

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import com.toedter.calendar.JDateChooser;
import java.awt.Component;

public class GUI {

	private JFrame frame;
	private JTextField txt_firstname;
	private JTextField txt_email;
	private JTextField txt_lastname;
	private JTextField txt_address;
	private JTextField txt_phone;
	private JTextField txt_model;
	private JTextField txt_cc;
	private JTextField txt_seats;
	private JTextField txt_cost;
    private JComboBox<String> txt_category;
	private JComboBox<String> txt_gender;
	private JFormattedTextField formattedTextField;
	private JTextField customerToRentEmail;
	private JTable table;
	private JComboBox<String> carToRent;
	private JTable rentalTable;
	private JDateChooser rentalFromDate = new JDateChooser();
	private JDateChooser rentalUntilDate = new JDateChooser();
	private JComboBox<String> carToSearch;
	private Component scrollPane_1_1;
	private JTable carRentalTable;
	private Object cost;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public GUI() {
		rentalFromDate.setDate(new Date());
		rentalUntilDate.setDate(new Date());
		initialize();
	}


	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1296, 796);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 1270, 757);
		frame.getContentPane().add(tabbedPane);
		
		JPanel carSubmission = new JPanel();
		tabbedPane.addTab("New car", null, carSubmission, null);
		carSubmission.setLayout(null);
		
		JButton SubmitCar = new JButton("SUBMIT");
		SubmitCar.addActionListener(new ActionListener() {


			public void actionPerformed(ActionEvent e) {
		        String carModel = txt_model.getText();
		        String carCC = txt_cc.getText();
		        String carCost = txt_cost.getText();
		        String carSeats = txt_seats.getText();
		        String carCategory = String.valueOf(txt_category.getSelectedItem());
		        
		        try {
		            Connection connection = DBUtils.getConnection();
		            Statement statement = connection.createStatement();
		            
		            String sql = "INSERT INTO cars (model, cc, seats, price, category) "
		                       + "VALUES ('" + carModel + "', '" + carCC + "', '" + carSeats + "', '" + carCost + "', '" + carCategory + "')";
		            
		            statement.executeUpdate(sql);
		            
		            DefaultTableModel carList = updateCarList().getCarList();
	                table.setModel(carList);
	                table.revalidate();
	                table.repaint();
	           
	                List<String> carModelList = updateCarList().getCarModelsList();
	                DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(carModelList.toArray(new String[0]));
	                carToRent.setModel(model);
	                carToRent.revalidate();
	                carToRent.repaint();
		            
		            statement.close();
		            connection.close();
		            clearNewCarFields();
		            
		            JOptionPane.showMessageDialog(null, "New vehicle added succesfully!");
		            
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		    }
		});
			
		SubmitCar.setBounds(126, 633, 191, 36);
		carSubmission.add(SubmitCar);
		
		JLabel lblNewLabel = new JLabel("MODEL");
		lblNewLabel.setBounds(126, 71, 150, 24);
		carSubmission.add(lblNewLabel);
		
		JLabel lblCc = new JLabel("CC");
		lblCc.setBounds(126, 143, 150, 24);
		carSubmission.add(lblCc);
		
		JLabel lblS = new JLabel("SEATS");
		lblS.setBounds(126, 234, 150, 24);
		carSubmission.add(lblS);
		
		JLabel lblCategory = new JLabel("CATEGORY");
		lblCategory.setBounds(126, 313, 150, 24);
		carSubmission.add(lblCategory);
		
		JLabel lblCost = new JLabel("COST");
		lblCost.setBounds(126, 390, 150, 24);
		carSubmission.add(lblCost);
		
		txt_model = new JTextField();
		txt_model.setBounds(338, 73, 208, 22);
		carSubmission.add(txt_model);
		txt_model.setColumns(10);
		
		txt_cc = new JTextField();
		txt_cc.setColumns(10);
		txt_cc.setBounds(338, 145, 208, 22);
		carSubmission.add(txt_cc);
		
		txt_seats = new JTextField();
		txt_seats.setColumns(10);
		txt_seats.setBounds(338, 235, 208, 22);
		carSubmission.add(txt_seats);
		
		txt_cost = new JTextField();
		txt_cost.setColumns(10);
		txt_cost.setBounds(338, 392, 208, 22);
		carSubmission.add(txt_cost);
		
		String[] carCategories = {"Economic", "SUV", "Sedan", "Sportscar"};
		txt_category = new JComboBox<>(carCategories);
		txt_category.setBounds(338, 314, 208, 24);
		carSubmission.add(txt_category);
		
		JPanel customerSubmission = new JPanel();
		tabbedPane.addTab("New customer", null, customerSubmission, null);
		customerSubmission.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("LAST NAME");
		lblNewLabel_1.setBounds(93, 126, 104, 30);
		customerSubmission.add(lblNewLabel_1);
		
		JButton btnSubmitCustomer = new JButton("SUBMIT");
		btnSubmitCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String firstname = txt_firstname.getText();
				String lastname = txt_lastname.getText();
				String email = txt_email.getText();
				String address = txt_address.getText();
				String phone = txt_phone.getText();
				String gender = String.valueOf(txt_gender.getSelectedItem());
			
			try {
				Connection connection = DBUtils.getConnection();
				
				java.sql.Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				
				String sqlEmailCheck = "SELECT id " +
		                  "FROM customers " +
		                  "WHERE email = '" + email + "'";
				
				ResultSet users_id = statement.executeQuery(sqlEmailCheck);
				
				if (users_id.first()) {
					JOptionPane.showMessageDialog(null, "This Email belongs to another account!");
				} else {
				
				String sql = "INSERT INTO customers (name, lastname, gender, address, email, phone)"
                        + "VALUES ('"+firstname+"', '"+lastname+"', '"+gender+"', '"+address+"', '"+email+"', '"+phone+"')";
				
				statement.execute(sql);
				clearNewCustomerFields();
				
				 JOptionPane.showMessageDialog(null, "Customer created succesfully!");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			}
		});
		btnSubmitCustomer.setBounds(80, 617, 152, 43);
		customerSubmission.add(btnSubmitCustomer);
		
		txt_firstname = new JTextField();
		txt_firstname.setBounds(330, 42, 226, 30);
		customerSubmission.add(txt_firstname);
		txt_firstname.setColumns(10);
		
		txt_email = new JTextField();
		txt_email.setColumns(10);
		txt_email.setBounds(330, 207, 226, 30);
		customerSubmission.add(txt_email);
		
		txt_lastname = new JTextField();
		txt_lastname.setColumns(10);
		txt_lastname.setBounds(330, 126, 226, 30);
		customerSubmission.add(txt_lastname);
		
		txt_address = new JTextField();
		txt_address.setColumns(10);
		txt_address.setBounds(330, 293, 226, 30);
		customerSubmission.add(txt_address);
		
		txt_phone = new JTextField();
		txt_phone.setColumns(10);
		txt_phone.setBounds(330, 473, 226, 30);
		customerSubmission.add(txt_phone);
		
		JLabel lblNewLabel_1_5 = new JLabel("FIRST NAME");
		lblNewLabel_1_5.setBounds(93, 42, 104, 30);
		customerSubmission.add(lblNewLabel_1_5);
		
		JLabel lblNewLabel_1_1 = new JLabel("EMAIL");
		lblNewLabel_1_1.setBounds(93, 207, 104, 30);
		customerSubmission.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("ADDRESS");
		lblNewLabel_1_2.setBounds(93, 293, 104, 30);
		customerSubmission.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("GENDER");
		lblNewLabel_1_3.setBounds(93, 391, 104, 30);
		customerSubmission.add(lblNewLabel_1_3);
		
		JLabel lblNewLabel_1_4 = new JLabel("PHONE");
		lblNewLabel_1_4.setBounds(93, 473, 104, 30);
		customerSubmission.add(lblNewLabel_1_4);
		
		JButton btnClear = new JButton("CLEAR");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txt_firstname.setText("");
				txt_lastname.setText("");
				txt_email.setText("");
				txt_address.setText("");
				txt_phone.setText("");
				
			}
		});
		btnClear.setBounds(404, 606, 152, 43);
		customerSubmission.add(btnClear);
		
		String[] gender = {"Male","Female"};
		txt_gender = new JComboBox<>(gender);
		txt_gender.setBounds(330, 391, 226, 30);
		customerSubmission.add(txt_gender);
		
		JPanel customersRentals = new JPanel();
		tabbedPane.addTab("Customer's Rentals", null, customersRentals, null);
		customersRentals.setLayout(null);
		
		JLabel lblNewLabel_4 = new JLabel("CUSTOMER'S RENTALS");
		lblNewLabel_4.setBounds(528, 11, 138, 30);
		customersRentals.add(lblNewLabel_4);
		
		rentalTable = new JTable();
		scrollPane_1 = new JScrollPane(rentalTable);
		scrollPane_1.setBounds(87, 200, 940, 458);
		customersRentals.add(scrollPane_1);
		
		JLabel lblNewLabel_5 = new JLabel("CUSTOMERS E-MAIL :");
		lblNewLabel_5.setBounds(87, 53, 121, 23);
		customersRentals.add(lblNewLabel_5);
		
		customersEmailToSearch = new JTextField();
		customersEmailToSearch.setBounds(218, 54, 202, 20);
		customersRentals.add(customersEmailToSearch);
		customersEmailToSearch.setColumns(10);
		
		
		
		JButton searchCustomersRentalBtn = new JButton("SEARCH");
		searchCustomersRentalBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String emailToSearch = customersEmailToSearch.getText();
				
				DefaultTableModel rentalList = updateCustomerRentalList(emailToSearch);
				rentalTable.setModel(rentalList);
				rentalTable.revalidate();
				rentalTable.repaint();
				
				
			}
		});
		searchCustomersRentalBtn.setBounds(87, 116, 108, 23);
		customersRentals.add(searchCustomersRentalBtn);
		
		JPanel rentalPanel = new JPanel();
		tabbedPane.addTab("New rental", null, rentalPanel, null);
		rentalPanel.setLayout(null);
		
		JLabel lblNewLabel_2_1 = new JLabel("CAR TO RENT");
		lblNewLabel_2_1.setBounds(111, 163, 113, 24);
		rentalPanel.add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_2_2 = new JLabel("FROM (DD/MM/YYYY)");
		lblNewLabel_2_2.setBounds(111, 227, 128, 24);
		rentalPanel.add(lblNewLabel_2_2);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("UNTIL (DD/MM/YYYY)");
		lblNewLabel_2_1_1.setBounds(111, 311, 128, 24);
		rentalPanel.add(lblNewLabel_2_1_1);
		
		JButton btnSubmitRental = new JButton("SUBMIT");
		btnSubmitRental.addActionListener(new ActionListener() {
		

		

			private String cars_id;
			private long cars_price;

			public void actionPerformed(ActionEvent e) {


				java.sql.Date fromDate = new java.sql.Date(rentalFromDate.getDate().getTime());
				java.sql.Date untilDate = new java.sql.Date(rentalUntilDate.getDate().getTime());
				String customerEmail = customerToRentEmail.getText();
				String carModel = String.valueOf(carToRent.getSelectedItem());
				
				
				LocalDate startingLocalDate = fromDate.toLocalDate();
		        LocalDate lastLocalDate = untilDate.toLocalDate();
				
		        long totalRentalDays = ChronoUnit.DAYS.between(startingLocalDate, lastLocalDate) + 1;
								
				try {
					Connection connection = DBUtils.getConnection();
					
					java.sql.Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					
					String sql_user = "SELECT id " +
			                  "FROM customers " +
			                  "WHERE email = '" + customerEmail + "'";
								
					ResultSet id_user = statement.executeQuery(sql_user);
				
					if (id_user.first()) {
						int users_id = id_user.getInt("id");
							
						String sql_car = "SELECT id " +
				                 "FROM cars " +
				                 "WHERE model = '" + carModel + "'";
	
						
						ResultSet id_car = statement.executeQuery(sql_car);
						
						id_car.first();
						int cars_id = id_car.getInt("id");
						
						String sql_price = "SELECT price " +
									"FROM cars " +
									"WHERE model = '" + carModel + "'";
						
						ResultSet price_car = statement.executeQuery(sql_price);
						
						price_car.first();
						int cars_price = price_car.getInt("price");
						
						double totalRentalCost = totalRentalDays * cars_price;
						
						String sql_rental = "INSERT into tenancy (customer_id , car_id , startingDay , lastDay , cost) " +
											"VALUES ('"+users_id+"' , '"+cars_id+"' , '"+fromDate+"' , '"+untilDate+"' , '"+totalRentalCost+"')";
					
						statement.execute(sql_rental);
						
						rentalFromDate.setDate(null);
						rentalUntilDate.setDate(null);
						customerToRentEmail.setText("");
						
					
						 JOptionPane.showMessageDialog(null, "New rental added succesfully!");
					} else {
					
						JOptionPane.showMessageDialog(null, "This email does not belong to any customer!");	
					
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		
		
		btnSubmitRental.setBounds(108, 473, 199, 37);
		rentalPanel.add(btnSubmitRental);
		
		customerToRentEmail = new JTextField();
		customerToRentEmail.setColumns(10);
		customerToRentEmail.setBounds(345, 100, 194, 22);
		rentalPanel.add(customerToRentEmail);
		
		JLabel lblNewLabel_2_3 = new JLabel("E-MAIL");
		lblNewLabel_2_3.setBounds(111, 99, 113, 24);
		rentalPanel.add(lblNewLabel_2_3);
		
		carToRent = new JComboBox<>(updateCarList().getCarModelsList().toArray(new String[0]));
		carToRent.setBounds(345, 164, 194, 22);
		rentalPanel.add(carToRent);
		
		rentalFromDate = new JDateChooser();
		rentalFromDate.getJCalendar().setMinSelectableDate(new Date());
		rentalFromDate.setBounds(345, 231, 194, 31);
		rentalPanel.add(rentalFromDate);
		
		rentalUntilDate = new JDateChooser();
		rentalUntilDate.getJCalendar().setMinSelectableDate(new Date());
		rentalUntilDate.setBounds(345, 311, 194, 31);
		rentalPanel.add(rentalUntilDate);
		
		JPanel ourCars = new JPanel();
		tabbedPane.addTab("Our cars", null, ourCars, null);
		ourCars.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("OUR CARS");
		lblNewLabel_3.setBounds(572, 23, 63, 32);
		ourCars.add(lblNewLabel_3);
		

		table = new JTable(updateCarList().getCarList());
		

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(214, 61, 780, 500);

		ourCars.add(scrollPane);
		
		JPanel carsRentals = new JPanel();
		tabbedPane.addTab("Car's Rentals", null, carsRentals, null);
		carsRentals.setLayout(null);
		
		JLabel lblNewLabel_5_1 = new JLabel("CAR MODEL :");
		lblNewLabel_5_1.setBounds(115, 61, 121, 23);
		carsRentals.add(lblNewLabel_5_1);
		
		JButton searchCarsRentals = new JButton("SEARCH");
		searchCarsRentals.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				String car = String.valueOf(carToSearch.getSelectedItem());
				
				DefaultTableModel carRentalList = updateCarRentalList(car);
				
				carRentalTable.setModel(carRentalList);
				carRentalTable.revalidate();
				carRentalTable.repaint();

			}				
				
			
		});
		searchCarsRentals.setBounds(115, 126, 108, 23);
		carsRentals.add(searchCarsRentals);
		
		carRentalTable = new JTable();
		scrollPane_1_1 = new JScrollPane(carRentalTable);
		scrollPane_1_1.setBounds(87, 200, 940, 458);
		carsRentals.add(scrollPane_1_1);
		
		JLabel lblNewLabel_4_1 = new JLabel("CAR'S RENTALS");
		lblNewLabel_4_1.setBounds(481, 11, 138, 30);
		carsRentals.add(lblNewLabel_4_1);
		
		carToSearch = new JComboBox<>(updateCarList().getCarModelsList().toArray(new String[0]));
		carToSearch.setBounds(246, 61, 169, 22);
		carsRentals.add(carToSearch);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Customer Deletion", null, panel, null);
		panel.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Customer's Email :");
		lblNewLabel_2.setBounds(87, 75, 123, 22);
		panel.add(lblNewLabel_2);
		
		customerToDelete = new JTextField();
		customerToDelete.setBounds(246, 76, 150, 21);
		panel.add(customerToDelete);
		customerToDelete.setColumns(10);
		
		JButton btnNewButton = new JButton("DELETE");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String deleteCustomer = customerToDelete.getText();
				
				try {
					Connection connection = DBUtils.getConnection();
					
					java.sql.Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					
					String sqlTenancy = "DELETE tenancy " +
		                    "FROM tenancy " +
		                    "INNER JOIN customers ON customers.id = tenancy.customer_id " +
		                    "WHERE customers.email = '" + deleteCustomer + "'";

					statement.execute(sqlTenancy);

					String sqlCustomers = "DELETE FROM customers " +
		                      "WHERE email = '" + deleteCustomer + "'";

					statement.execute(sqlCustomers);

					customerToDelete.setText("");
					
					 JOptionPane.showMessageDialog(null, "Customer deleted succesfully");
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		btnNewButton.setBounds(87, 161, 117, 32);
		panel.add(btnNewButton);
		
		JLabel lblNewLabel_2_4 = new JLabel("Customer's Email :");
		lblNewLabel_2_4.setBounds(87, 305, 123, 22);
		panel.add(lblNewLabel_2_4);
		
		JLabel lblNewLabel_2_5 = new JLabel("Rental's id :");
		lblNewLabel_2_5.setBounds(87, 385, 90, 22);
		panel.add(lblNewLabel_2_5);
		
		customersEmailToDeleteRental = new JTextField();
		customersEmailToDeleteRental.setColumns(10);
		customersEmailToDeleteRental.setBounds(246, 306, 150, 21);
		panel.add(customersEmailToDeleteRental);
		
		JButton btnNewButton_1 = new JButton("DELETE RENTAL");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String customerEmail = customersEmailToDeleteRental.getText();
				int rentalToDelete = Integer.parseInt(rentalToDeleteId.getText());
			
				try {
			        Connection connection = DBUtils.getConnection();
			        Statement statement = connection.createStatement();
			        
			        String sql = "DELETE tenancy " +
			        			"FROM tenancy " +
			        			"INNER JOIN customers ON customers.email = \'" + customerEmail + "\' " +
			        			"WHERE ( customers.email = \'" + customerEmail + "\') AND ( tenancy.id = " + rentalToDelete + ")";
			        
			        statement.execute(sql);
			        
			        JOptionPane.showMessageDialog(null, "Rental deleted succesfully!");
			        
			        statement.close();
			        connection.close();
			    } catch (SQLException ex) {
			        ex.printStackTrace();
			    }
			}
		});
		btnNewButton_1.setBounds(93, 493, 117, 32);
		panel.add(btnNewButton_1);
		
		rentalToDeleteId = new JTextField();
		rentalToDeleteId.setColumns(10);
		rentalToDeleteId.setBounds(246, 386, 150, 21);
		panel.add(rentalToDeleteId);
	}
	
	
	
	public CarListResult updateCarList() {
	    DefaultTableModel carList = new DefaultTableModel(new String[]{"Category", "Model", "CC", "Seats", "Price"}, 0);
	    List<String> carModelsList = new ArrayList<>();
	    
	    try {
	        Connection connection = DBUtils.getConnection();
	        Statement statement = connection.createStatement();
	        
	        String sql = "SELECT category, model, cc, seats, price FROM cars";
	        
	        ResultSet CarsList = statement.executeQuery(sql);
	        
	        while (CarsList.next()) {
	            String carModel = CarsList.getString("model");
	            String category = CarsList.getString("category");
	            int cc = CarsList.getInt("cc");
	            int seats = CarsList.getInt("seats");
	            int price = CarsList.getInt("price");

	            carList.addRow(new Object[]{category, carModel, cc, seats, price});
	            carModelsList.add(carModel);
	        }
	        
	        statement.close();
	        connection.close();
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return new CarListResult(carList, carModelsList);
	}
	
	public DefaultTableModel updateCarRentalList(String car) {
		DefaultTableModel carRentalList = new DefaultTableModel(new String[]{"startingDay", "lastDay", "cost" ,"email" }, 0);
		
		try {
			Connection connection = DBUtils.getConnection();
			
			java.sql.Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			String sql = "SELECT tenancy.startingDay, tenancy.lastDay, tenancy.cost, customers.email FROM tenancy INNER JOIN customers ON customers.id = tenancy.customer_id"
					+ " INNER JOIN cars ON cars.id = tenancy.car_id WHERE cars.model = \'" + car + "\'" ;
			
			ResultSet results = statement.executeQuery(sql);
			
	        
	        while (results.next()) {
	            String startingDay = results.getString("startingDay");
	            String lastDay = results.getString("lastDay");
	            int totalCost = results.getInt("cost");
	            String email = results.getString("email");
	            

	            carRentalList.addRow(new Object[]{startingDay, lastDay, totalCost, email});
	        }
	        
	        statement.close();
	        connection.close();
	        
			
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return carRentalList;
	}				
	
	public DefaultTableModel updateCustomerRentalList(String emailToSearch) {
		 DefaultTableModel rentalList = new DefaultTableModel(new String[]{"startingDay", "lastDay", "cost" , "model" , "rental id"}, 0);
		
		try {
			Connection connection = DBUtils.getConnection();
			
			java.sql.Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			String sql = "SELECT tenancy.startingDay, tenancy.lastDay, tenancy.cost, cars.model, tenancy.id FROM tenancy INNER JOIN cars ON cars.id = tenancy.car_id"
					+ " INNER JOIN customers ON customers.id = tenancy.customer_id WHERE customers.email = \'" + emailToSearch + "\'" ;
			
			ResultSet results = statement.executeQuery(sql);
			
	        
	        while (results.next()) {
	            String startingDay = results.getString("startingDay");
	            String lastDay = results.getString("lastDay");
	            int totalCost = results.getInt("cost");
	            String model = results.getString("model");
	            int rental_id = results.getInt("id");
	            

				rentalList.addRow(new Object[]{startingDay, lastDay, totalCost, model, rental_id});
	        }
	        
	        statement.close();
	        connection.close();
			
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return rentalList;
		
	}
	
	CarListResult result = updateCarList();
	DefaultTableModel carList = result.getCarList();
	List<String> carModelsList1 = result.getCarModelsList();
	private JTextField customersEmailToSearch;
	private JScrollPane scrollPane_1;
	private JTextField customerToDelete;
	private JTextField customersEmailToDeleteRental;
	private JTextField rentalToDeleteId;

	
	public void clearNewCarFields() {
	    txt_model.setText("");
	    txt_cc.setText("");
	    txt_seats.setText("");
	    txt_cost.setText("");
	}
	
	public void clearNewCustomerFields() {
		txt_firstname.setText("");
		txt_lastname.setText("");
		txt_email.setText("");
		txt_address.setText("");
		txt_phone.setText("");
	}
	
	public void clearNewRentalFields() {
		    formattedTextField.setText("");
		    customerToRentEmail.setText("");
		
	}
}
