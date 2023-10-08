package carsharing;
import java.util.Scanner;

public class Menu {
    private boolean marker;
    private Scanner scanner;
    private  DataProcessing dataProcessing;
    Menu(boolean marker,  DataProcessing dataProcessing){
        this.marker = marker;
        this.scanner = new Scanner(System.in);
        this.dataProcessing = dataProcessing;
    }
    public void engine(){
        while (marker){
            mainMenu();
            int menuPoint = Integer.parseInt(scanner.nextLine());
            if(menuPoint == 0){
                break;
            }
        //Log in as a manager
            else if (menuPoint == 1){
                managerMenu();
            }
        //Log in as a customer
            else if(menuPoint == 2){
                printCustomersList();
            }
        //Create a customer
            else{
                newCustomerMenu();
            }
        }
        scanner.close();
        dataProcessing.closeConnections();
    }
    public void printCompanyList(){
        boolean isEmpty = dataProcessing.selectAllData("companies");
        if(isEmpty){
            managerMenu();
        }else{
            int companyId = Integer.parseInt(scanner.nextLine());
            if(companyId != 0){
                dataProcessing.selectUniqueElement("companies", companyId);
                carMenu(companyId);
            }
        }
    }
    public void printCustomersList(){
        boolean isEmpty = dataProcessing.selectAllData("customers");
        if(!isEmpty){
            customerMenu(Integer.parseInt(scanner.nextLine()));
        }
    }
    public void customerMenu(int customerId){
        System.out.println("\n1. Rent a car");
        System.out.println("2. Return a rented car");
        System.out.println("3. My rented car");
        System.out.println("0. Back\n");
        while (true){
            int menuPoint = Integer.parseInt(scanner.nextLine());

            //Rent a car
            if(menuPoint == 1){
                if (dataProcessing.selectRentedCarForCheck(customerId)) {
                    System.out.println("You've already rented a car!");
                }else{
                    dataProcessing.selectAllData("companies");
                    int companyId = Integer.parseInt(scanner.nextLine());
                    if(!dataProcessing.selectAllFreeCars(companyId)) {
                        int carId = Integer.parseInt(scanner.nextLine());
                        if(carId == 0){
                            customerMenu(customerId);
                        }else{
                            dataProcessing.updateRentedCarStatus(companyId, carId, customerId);
                        }
                    }else{
                        while (true){
                            dataProcessing.selectAllData("companies");
                            companyId = Integer.parseInt(scanner.nextLine());
                            if(!dataProcessing.selectAllFreeCars(companyId)) {
                                break;
                            }
                        }
                        dataProcessing.updateRentedCarStatus(companyId, Integer.parseInt(scanner.nextLine()), customerId);
                    }
                }
            }
            //Return a rented car
            else if(menuPoint == 2){
                if (!dataProcessing.selectRentedCarForCheck(customerId)) {
                    System.out.println("You didn't rent a car!");
                }else {
                    if (!dataProcessing.selectRentedCarForCheck(customerId)) {
                        System.out.println("You've returned a rented car!");
                    } else {
                        dataProcessing.updateReturnRentedCar(customerId);
                    }
                }
            }

            //My rented car
            else if(menuPoint == 3){
                dataProcessing.selectInfoAboutUserCar(customerId);
            }else if(menuPoint == 0){
                break;
            }
            System.out.println("1. Rent a car");
            System.out.println("2. Return a rented car");
            System.out.println("3. My rented car");
            System.out.println("0. Back\n");
        }
    }
    public void managerMenu(){
        while (true) {
            System.out.println("\n1. Company list");
            System.out.println("2. Create a company");
            System.out.println("0. Back");
            int menuPoint = Integer.parseInt(scanner.nextLine());
            //Company list
            if (menuPoint == 1) {
                printCompanyList();
                //Create a company
            } else if (menuPoint == 2) {
                addNewCompany();
            } else {
                break;
            }
        }
    }
    public void newCustomerMenu(){
        System.out.println("Enter the customer name:");
        dataProcessing.insertNewElement("customer", scanner.nextLine(), null);
    }
    public void mainMenu(){
        System.out.println("1. Log in as a manager");
        System.out.println("2. Log in as a customer");
        System.out.println("3. Create a customer");
        System.out.println("0. Exit");
    }
    public void carMenu(int companyId){
        System.out.println("1. Car list");
        System.out.println("2. Create a car");
        System.out.println("0. Back");
        while (true){
            int menuPoint = Integer.parseInt(scanner.nextLine());
            if(menuPoint == 1){
                dataProcessing.selectAllCars(companyId);
            }else if(menuPoint == 2){
                System.out.println("Enter the car name:");
                dataProcessing.insertNewElement("car", scanner.nextLine(), companyId);
            }else{
                break;
            }
            System.out.println("1. Car list");
            System.out.println("2. Create a car");
            System.out.println("0. Back");
        }
    }
    public void addNewCompany(){
        System.out.println("Enter the company name:");
        dataProcessing.insertNewElement("company", scanner.nextLine(), null);
    }
}