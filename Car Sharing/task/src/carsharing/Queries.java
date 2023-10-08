package carsharing;

public class Queries {
    public final static String selectCustomerId = "SELECT id FROM customer WHERE name = ?";
    public final static String selectCarId = "SELECT id FROM car WHERE name = ?";
    public final static String selectCompany = "SELECT name FROM company WHERE id = ?";
    public final static String selectFreeCars = "SELECT cr.name AS car_name " +
            "FROM CAR cr WHERE cr.company_id = ? " +
            "AND cr.id NOT IN (SELECT rented_car_id FROM customer WHERE rented_car_id >0)";
    public final static String updateRentedCar = "UPDATE customer SET rented_car_id = ? WHERE id = ?";
    public final static String updateReturnRentedCar = "UPDATE customer SET rented_car_id = null WHERE id = ?";
    public final static String selectInfoAboutUserCar = "SELECT cr.NAME AS car_name, cmp.NAME AS company_name FROM CAR cr " +
            "JOIN COMPANY cmp ON cmp.id = cr.COMPANY_ID " +
            "JOIN CUSTOMER cs ON cs.rented_car_id = cr.ID " +
            "WHERE cs.id = ?";
    public final static String selectRentedCarForCheck = "SELECT rented_car_id FROM customer WHERE id = ?";
    public final static String selectRentedCarId = "SELECT car.id" +
            "FROM CAR car" +
            "WHERE car.id = (SELECT rented_car_id FROM customer WHERE id = ?)";
    public final static String insertNewCompany = "INSERT INTO company(name) VALUES (?)";
    public final static String insertCar = "INSERT INTO car(name,company_id ) VALUES (?, ?)";
    public final static String insertCustomer = "INSERT INTO customer(name) VALUES (?)";
    public final static String selectCustomers = "Select * from customer order by id asc";
    public final static String selectCompanies = "Select * from company order by id asc";
    public final static String selectCars = "select * from car where company_id=?";
    public final static String companyTable = "CREATE TABLE IF NOT EXISTS company("+
            "id INTEGER PRIMARY KEY AUTO_INCREMENT, " +
            "name CHARACTER VARYING UNIQUE NOT NULL)";
    public final static String carTable = "CREATE TABLE IF NOT EXISTS car(" +
            "id INT PRIMARY KEY AUTO_INCREMENT, " +
            "name VARCHAR UNIQUE NOT NULL," +
            "company_id INT  NOT NULL, " +
            "CONSTRAINT fk_company FOREIGN KEY (company_id) REFERENCES company(id))";

    public final static String customersTable = "CREATE TABLE IF NOT EXISTS customer " +
            "(id INT PRIMARY KEY AUTO_INCREMENT, " +
            "name VARCHAR(255) UNIQUE NOT NULL," +
            "rented_car_id INT DEFAULT NULL," +
            "FOREIGN KEY (rented_car_id) REFERENCES CAR(id))";
}
