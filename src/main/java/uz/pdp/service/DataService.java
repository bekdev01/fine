package uz.pdp.service;

import uz.pdp.connet.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataService {
    public static String getUsersByPassportCode(String code) throws SQLException {
        Connection connection = DbConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select json_build_object('a',u)->'a' from users u where passport_code=?");
        preparedStatement.setString(1, code);
        long start = System.currentTimeMillis();
        ResultSet resultSet = preparedStatement.executeQuery();
        long end = System.currentTimeMillis();
        if (resultSet.next())
            return resultSet.getString(1).concat("\nDB Time: "+(end-start)+" ms");
        return null;
    }

    public static String getCarsByNumber(String number) throws SQLException {
        Connection connection = DbConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select json_build_object('a',u)->'a' from car u where number =?");
        preparedStatement.setString(1, number);
        long start = System.currentTimeMillis();
        ResultSet resultSet = preparedStatement.executeQuery();
        long end = System.currentTimeMillis();
        if (resultSet.next())
            return resultSet.getString(1).concat("\nDB Time: "+(end-start)+" ms");
        return null;
    }

    public static String getFineByMonthAndCarId(int month, String carNumber) throws SQLException {
        Connection connection = DbConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select json_agg(json_build_object('a',f)->'a') from fine f\n" +
                "inner join car c on f.car_id = c.id\n" +
                "where c.number=? and date_part('month',f.created_date::date)=?");
        preparedStatement.setString(1, carNumber);
        preparedStatement.setInt(2, month);
        long start = System.currentTimeMillis();
        ResultSet resultSet = preparedStatement.executeQuery();
        long end = System.currentTimeMillis();
        if (resultSet.next())
            return resultSet.getString(1).concat("\nDB Time: "+(end-start)+" ms");
        return null;
    }

    public static String getFineByCarNumber(String number) throws SQLException {
        Connection connection = DbConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select json_agg(json_build_object('a',f)->'a') from fine f\n" +
                "inner join car c on f.car_id = c.id\n" +
                "where c.number=?");
        preparedStatement.setString(1, number);
        long start = System.currentTimeMillis();
        ResultSet resultSet = preparedStatement.executeQuery();
        long end = System.currentTimeMillis();
        if (resultSet.next())
            return resultSet.getString(1).concat("\nDB Time: "+(end-start)+" ms");
        return null;
    }

    public static String getFineByUserIdNumber(long id, boolean isPaid) throws SQLException {
        Connection connection = DbConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select u.name,u.passport_code,c.name,c.number,f.amount,f.created_date,f.paid_date from users u\n" +
                "inner join car c on u.id = c.user_id\n" +
                "inner join fine f on c.id = f.car_id\n" +
                "where u.id=? and f.is_paid=? order by f.created_date " + (isPaid ? "asc" : "desc"));
        preparedStatement.setLong(1, id);
        preparedStatement.setBoolean(2, isPaid);
        long start = System.currentTimeMillis();
        ResultSet resultSet = preparedStatement.executeQuery();
        long end = System.currentTimeMillis();
        StringBuilder builder = new StringBuilder();
        builder.append("NAME\t").append("PASSPORT\t").append("NAME\t").append("NUMBER\t").append("AMOUNT\t").append("CREATED DATE\t").append("PAID DATE\n");
        while (resultSet.next()) {
            builder.append(resultSet.getString(1)).append("\t")
                    .append(resultSet.getString(2)).append("\t")
                    .append(resultSet.getString(3)).append("\t")
                    .append(resultSet.getString(4)).append("\t")
                    .append(resultSet.getDouble(5)).append("\t")
                    .append(resultSet.getDate(6)).append("\t")
                    .append(resultSet.getDate(7)).append("\n");
        }
        builder.append("DB Time: ").append(end-start).append(" ms");
        return builder.toString();
    }
}
