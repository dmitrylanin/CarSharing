package carsharing;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DataLevel {
    public ArrayList<String> selectAllData(Statement statement, String sql) throws SQLException{
        ArrayList<String> data = new ArrayList<>();
        ResultSet rs = statement.executeQuery(sql);
        if (rs.next()){
            do {
                data.add(rs.getString("name"));
            } while(rs.next());
        }else{
            data.add(null);
        }
        return data;
    }
    public String selectUniqueElement(PreparedStatement statement, int id) throws SQLException{
        String data;
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        while (rs.next()){
            return rs.getString("name");
        }
        return null;
    }
    public ArrayList<String> selectAllCars(PreparedStatement statement, int companyId) throws SQLException{
        ArrayList<String> cars = new ArrayList<>();
        statement.setInt(1, companyId);
        ResultSet rs = statement.executeQuery();
        while (rs.next()){
            cars.add(rs.getString("name"));
        }
        return cars;
    }

    public ArrayList<String> selectAllFreeCars(PreparedStatement statement, int companyId) throws SQLException{
        ArrayList<String> cars = new ArrayList<>();
        statement.setInt(1, companyId);
        ResultSet rs = statement.executeQuery();
        while (rs.next()){
            cars.add(rs.getString("name"));
        }
        return cars;
    }

    public int insertNewElement(PreparedStatement statement, String name, Integer id) throws SQLException{
        statement.setString(1, name);
        if(id != null){
            statement.setInt(2, id.intValue());
        }
        return statement.executeUpdate();
    }
    public boolean selectRentedCarForCheck(PreparedStatement statement, int customerId) throws SQLException{
        statement.setInt(1, customerId);
        ResultSet rs = statement.executeQuery();
        while (rs.next()){
            if(rs.getInt("rented_car_id")>0){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }
    public int selectCustomerId(PreparedStatement statement, String name) throws SQLException{
        int id = 0;
        statement.setString(1, name);
        ResultSet rs = statement.executeQuery();
        while (rs.next()){
            id = rs.getInt("id");
        }
        return id;
    }
    public int selectCarId(PreparedStatement statement, String name) throws SQLException{
        int id = 0;
        statement.setString(1, name);
        ResultSet rs = statement.executeQuery();
        while (rs.next()){
            id = rs.getInt("id");
        }
        return id;
    }
    public String selectCompanyName(PreparedStatement statement, int companyId) throws SQLException{
        statement.setInt(1, companyId);
        ResultSet rs = statement.executeQuery();
        while (rs.next()){
            return rs.getString("name");
        }
        return null;
    }
    public boolean updateRentedCar(PreparedStatement statement, int carId, int customerId) throws SQLException{
        statement.setInt(1, carId);
        statement.setInt(2, customerId);
        int updated = statement.executeUpdate();
        if(updated>0){
            return true;
        }
        return false;
    }
    public boolean updateReturnRentedCar(PreparedStatement statement, int customerId)throws SQLException{
        statement.setInt(1, customerId);
        int updated = statement.executeUpdate();
        if(updated>0){
            return true;
        }
        return false;
    }
    public ArrayList<String>selectInfoAboutUserCar(PreparedStatement statement, int customerId)throws SQLException{
        ArrayList<String> data = new ArrayList<>();
        statement.setInt(1, customerId);
        ResultSet rs = statement.executeQuery();
        if(rs.next()){
            do{
                data.add(rs.getString("car_name"));
                data.add(rs.getString("company_name"));
            }
            while (rs.next());
        }else {
            data.add(null);
        }
        return data;
    }

}
