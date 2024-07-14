package cl.praxis.startup.dao.impl;

import cl.praxis.startup.connection.MySQLConnection;
import cl.praxis.startup.dao.VehicleDAO;
import cl.praxis.startup.models.VehicleDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VehicleDAOImpl implements VehicleDAO {

    private static final String INSERT_VEHICLE_SQL = "INSERT INTO vehiculos (nombre, url, id_usuario) VALUES (?, ?, ?)";

    @Override
    public VehicleDTO insertVehicle(VehicleDTO vehicle) {
        VehicleDTO newVehicle = new VehicleDTO();
        try(PreparedStatement preparedStatement = MySQLConnection.getInstance().getConnection().prepareStatement(INSERT_VEHICLE_SQL, PreparedStatement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, vehicle.getName());
            preparedStatement.setString(2, vehicle.getUrl());
            preparedStatement.setInt(3, vehicle.getUserId());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()){
                int id = rs.getInt(1);
                newVehicle = new VehicleDTO(id, vehicle.getName(), vehicle.getUrl(), vehicle.getUserId());
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return newVehicle;
    }
}
