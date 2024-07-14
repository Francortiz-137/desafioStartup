package cl.praxis.startup.services.impl;


import cl.praxis.startup.dao.VehicleDAO;
import cl.praxis.startup.dao.impl.VehicleDAOImpl;
import cl.praxis.startup.models.VehicleDTO;
import cl.praxis.startup.services.VehicleService;

public class VehicleServiceImpl implements VehicleService {
    private VehicleDAO vehicleDAO;

    public VehicleServiceImpl() {
        vehicleDAO = new VehicleDAOImpl();
    }

    @Override
    public VehicleDTO insertVehicle(VehicleDTO vehicle) {
        return vehicleDAO.insertVehicle(vehicle);
    }
}
