package uz.pdp.bot.service;

import com.google.gson.Gson;
import uz.pdp.bot.connet.DbConnection;
import uz.pdp.model.Car;
import uz.pdp.model.User;

import java.awt.font.ShapeGraphicAttribute;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataService {
    public static String getUsersById(long id) throws SQLException {
        Connection connection = DbConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select json_build_object('a',u)->'a' from users u where id=?");
        preparedStatement.setLong(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next())
            return resultSet.getString(1);
        return null;
    }
    public static String getCarsById(long id) throws SQLException {
        Connection connection = DbConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select json_build_object('a',u)->'a' from car u where id=?");
        preparedStatement.setLong(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next())
            return resultSet.getString(1);
        return null;
    }
}
