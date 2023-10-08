package carsharing;

import java.sql.SQLException;
import java.util.ArrayList;

public class DataProcessing {
    private final DAO dao;
    private DataLevel dataLevel;
    DataProcessing(String dbName){
        this.dao = new DAO(dbName);
        this.dataLevel = new DataLevel();
    }
    public boolean selectAllData(String dataType){
        try{
            ArrayList<String> data = dao.getAllData(dataType);
            if(data.get(0) == null){
                switch (dataType){
                    case ("companies"):
                        System.out.println("The company list is empty!");
                        break;
                    case ("customers"):
                        System.out.println("The customer list is empty!\n");
                        break;
                    case ("cars"):
                        System.out.println("The car list is empty!");
                        break;
                    default:
                        break;
                }
                return true;
            }else{
                switch (dataType){
                    case ("companies"):
                        System.out.println("\nChoose the company:");
                        break;
                    case ("customers"):
                        System.out.println("\nThe customer list:");
                        break;
                    case ("cars"):
                        System.out.println("\nChoose a car:");
                        break;
                    default:
                        break;
                }
                printAllData(data);
                System.out.println("0. Back");
                return false;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public void selectUniqueElement(String dataType, int id){
        try {
            switch (dataType) {
                case ("companies"):
                    System.out.println("'" + dao.selectUniqueElement("companies", id) + "' company\n");
                    break;
                case ("customers"):

                    break;
                default:
                    break;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void selectAllCars(int companyId){
        try {
        ArrayList<String> carsList = dao.selectAllCars(companyId);
            if(carsList.size() == 0){
                System.out.println("The car list is empty!\n");
            }else{
                System.out.println("Car list:");
                printAllData(carsList);
                System.out.println("");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public boolean selectAllFreeCars(int companyId){
        try {
            ArrayList<String> carsList = dao.selectAllFreeCars(companyId);
            if(carsList.size() == 0){
                String companyName = dao.selectCompanyName(companyId);
                System.out.println("No available cars in the '" + companyName + "'\n");
                return true;
            }else{
                System.out.println("Car list:");
                printAllData(carsList);
                System.out.println("");
                return false;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public String selectCarName(int companyId, int carId){
        try {
            ArrayList<String> carsList = dao.selectAllFreeCars(companyId);
            return carsList.get(carId-1);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public int selectCarId(String carName){
        try {
            int id = dao.selectCarId(carName);
            return id;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
    public void insertNewElement(String dataType, String name, Integer id){
        try {
            switch (dataType) {
                case ("company"):
                    dao.insertNewElement("company", name, null);
                    System.out.println("The company was created!");
                    break;
                case ("car"):
                    dao.insertNewElement("car", name, id);
                    System.out.println("The car was added!\n");
                    break;
                case ("customer"):
                    dao.insertNewElement("customer", name, null);
                    System.out.println("The customer was added!\n");
                    break;
                default:
                    break;

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public boolean selectRentedCarForCheck(int customerId){
        try {
            ArrayList<String> customersNames = dao.getAllData("customers");
            String customersName = customersNames.get(customerId-1);
            int realCustomerId = dao.selectCustomerId(customersName);
            return dao.selectRentedCarForCheck(realCustomerId);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public void updateRentedCarStatus(int companyId, int carId, int customerId){
       try{
           ArrayList<String> customersNames = dao.getAllData("customers");

           String customersName = customersNames.get(customerId-1);
           int realCustomerId = dao.selectCustomerId(customersName);
           String carName = selectCarName(companyId, carId);
           int realCarId = selectCarId(carName);
           if(dao.updateRentedCar(realCarId, realCustomerId)){
               System.out.println("You rented '" +carName+ "'");
           }
       }catch (SQLException e){
           e.printStackTrace();
       }
    }
    public void updateReturnRentedCar(int customerId){
        try {
            if(dao.updateReturnRentedCar(customerId)){
                System.out.println("You've returned a rented car!");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void selectInfoAboutUserCar(int customerId){
        try {
            ArrayList<String> customersNames = dao.getAllData("customers");
            String customersName = customersNames.get(customerId-1);
            int realCustomerId = dao.selectCustomerId(customersName);
            ArrayList<String> data = dao.selectInfoAboutUserCar(realCustomerId);
            if(data.get(0) == null){
                System.out.println("You didn't rent a car!");
            }else{
                System.out.println("Your rented car:");
                System.out.println(data.get(0));
                System.out.println("Company:");
                System.out.println(data.get(1)+"\n");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void printAllData(ArrayList<String> data){
        for(int i=0; i<data.size(); i++){
            System.out.println(i+1 + ". " + data.get(i));
        }
    }
    public void closeConnections(){
        dao.exitDatabaseConnection();
    }
}