package uz.pdp.bot.service;

import com.google.gson.Gson;
import uz.pdp.bot.connet.DbConnection;
import uz.pdp.model.Car;
import uz.pdp.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataService {
    public static User[] getUsersById(long id) throws SQLException {
        Connection connection = DbConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select json_agg(json_build_object('a',u)->'a') from users u where id=1");
        preparedStatement.setLong(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        User[] users=null;
        if(resultSet.next())
            return new Gson().fromJson(resultSet.getString(1),User[].class);
        return null;
    }
    public static Car[] getCarsById(long id) throws SQLException {
        Connection connection = DbConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select json_agg(json_build_object('a',u)->'a') from car u where id=1");
        preparedStatement.setLong(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Car[] cars=null;
        if(resultSet.next())
            return new Gson().fromJson(resultSet.getString(1),Car[].class);
        return null;
    }
}
