package carsharing;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class DAO {
    private String dbName;
    private File directory;
    protected Connection connection;
    private DataLevel dataLevel;
    public DAO(String dbName){
        this.dbName = dbName;
        this.directory = new File(new File("").getAbsoluteFile() + "/Car Sharing/task/src/carsharing/db/");
        try {
            Class.forName("org.h2.Driver").newInstance();
            if(dbName == null){
                this.connection = DriverManager.getConnection("jdbc:h2:"+ "./src/carsharing/db/my");
            }else{
                this.connection = DriverManager.getConnection("jdbc:h2:"+ "./src/carsharing/db/"+dbName);
            }
            connection.setAutoCommit(true);
            Statement companyTableStatement = connection.createStatement();
            companyTableStatement.execute(Queries.companyTable);
            companyTableStatement.close();

            Statement carTableStatement = connection.createStatement();
            carTableStatement.execute(Queries.carTable);
            carTableStatement.close();

            Statement customersTableStatement = connection.createStatement();
            customersTableStatement.execute(Queries.customersTable);
            customersTableStatement.close();
        }catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e){
            e.printStackTrace();
        }
        this.dataLevel = new DataLevel();
    }
    public ArrayList<String> getAllData(String dataType) throws SQLException{
        Statement statement = connection.createStatement();
        ArrayList<String> data = new ArrayList<>();

        switch (dataType){
            case ("companies"):
                data = dataLevel.selectAllData(statement, Queries.selectCompanies);
                break;
            case ("customers"):
                data = dataLevel.selectAllData(statement, Queries.selectCustomers);
                break;
            case ("cars"):
                data = dataLevel.selectAllData(statement, Queries.selectCars);
                break;
            default:
                break;
        }
        statement.close();
        return data;
    }
    public String selectUniqueElement(String dataType, int id) throws SQLException{
        PreparedStatement statement = null;
        String data = null;

        switch (dataType){
            case ("companies"):
                statement = connection.prepareStatement(Queries.selectCompany);
                data = dataLevel.selectUniqueElement(statement, id);
                break;
            case ("customers"):

                break;
            case ("cars"):
                statement = connection.prepareStatement(Queries.selectCars);
                data = dataLevel.selectUniqueElement(statement, id);
                break;
            default:
                break;
        }
        statement.close();
        return data;
    }
    public int selectCustomerId(String name) throws SQLException{
        PreparedStatement selectCarId = connection.prepareStatement(Queries.selectCustomerId);
        int id = dataLevel.selectCustomerId(selectCarId, name);
        selectCarId.close();
        return id;
    }
    public String selectCompanyName(int companyId) throws SQLException{
        PreparedStatement selectCarsStatement = connection.prepareStatement(Queries.selectCompany);
        var companyName = dataLevel.selectCompanyName(selectCarsStatement, companyId);
        selectCarsStatement.close();
        return companyName;
    }
    public int insertNewElement(String dataType, String name, Integer id) throws SQLException{
        PreparedStatement insertStatement = null;
        int count = 0;
        switch (dataType){
            case ("company"):
                insertStatement = connection.prepareStatement(Queries.insertNewCompany);
                count = dataLevel.insertNewElement(insertStatement, name, null);
                break;
            case ("car"):
                insertStatement = connection.prepareStatement(Queries.insertCar);
                dataLevel.insertNewElement(insertStatement, name, id);
                break;
            case ("customer"):
                insertStatement = connection.prepareStatement(Queries.insertCustomer);
                count = dataLevel.insertNewElement(insertStatement, name, null);
                break;
            default:
                break;
        }
        insertStatement.close();
        return count;
    }
    public ArrayList<String> selectAllCars(int companyId) throws SQLException{
        PreparedStatement selectCarsStatement = connection.prepareStatement(Queries.selectCars);
        var cars = dataLevel.selectAllCars(selectCarsStatement, companyId);
        selectCarsStatement.close();
        return cars;

    }
    public ArrayList<String> selectAllFreeCars(int companyId) throws SQLException{
        PreparedStatement selectCarsStatement = connection.prepareStatement(Queries.selectFreeCars);
        var cars = dataLevel.selectAllFreeCars(selectCarsStatement, companyId);
        selectCarsStatement.close();
        return cars;
    }
    public boolean selectRentedCarForCheck(int customerId) throws SQLException{
        PreparedStatement selectRentedCarForCheck = connection.prepareStatement(Queries.selectRentedCarForCheck);
        boolean hasAlreadyCar = dataLevel.selectRentedCarForCheck(selectRentedCarForCheck, customerId);
        selectRentedCarForCheck.close();
        return hasAlreadyCar;
    }
    public int selectCarId(String name) throws SQLException{
        PreparedStatement selectCarId = connection.prepareStatement(Queries.selectCarId);
        int id = dataLevel.selectCarId(selectCarId, name);
        selectCarId.close();
        return id;
    }
    public boolean updateRentedCar(int carId, int customerId) throws SQLException{
        PreparedStatement updateRentedCar = connection.prepareStatement(Queries.updateRentedCar);
        boolean data = dataLevel.updateRentedCar(updateRentedCar, carId, customerId);
        updateRentedCar.close();
        return data;
    }
    public boolean updateReturnRentedCar(int customerId )throws SQLException{
        PreparedStatement updateReturnRentedCar = connection.prepareStatement(Queries.updateReturnRentedCar);
        boolean data = dataLevel.updateReturnRentedCar(updateReturnRentedCar, customerId);
        updateReturnRentedCar.close();
        return data;
    }
    public ArrayList<String> selectInfoAboutUserCar(int customerId)throws SQLException{
        PreparedStatement selectRentedCarStatement = connection.prepareStatement(Queries.selectInfoAboutUserCar);
        ArrayList<String> data = dataLevel.selectInfoAboutUserCar(selectRentedCarStatement, customerId);
        selectRentedCarStatement.close();
        return data;
    }
    public void exitDatabaseConnection(){
        try{
            /*
            Statement dropCustomerStatement = connection.createStatement();
            dropCustomerStatement.execute("DROP TABLE IF EXISTS customer");
            dropCustomerStatement.close();

            Statement dropCarStatement = connection.createStatement();
            dropCarStatement.execute("DROP TABLE IF EXISTS car");
            dropCarStatement.close();

            Statement dropCompanyStatement = connection.createStatement();
            dropCompanyStatement.execute("DROP TABLE IF EXISTS company");
            dropCompanyStatement.close();
             */
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}