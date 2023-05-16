package pl.regula.ksb2modul3restapidomowe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.regula.ksb2modul3restapidomowe.dao.VehicleDao;
import pl.regula.ksb2modul3restapidomowe.model.Vehicle;

import java.util.List;

@Service
public class VehicleService {

    private VehicleDao vehicleDao;

    @Autowired
    public VehicleService(VehicleDao vehicleDao) {
        this.vehicleDao = vehicleDao;
    }


    public int addVehicle(Vehicle vehicle) {
        return vehicleDao.saveVehicle(
                vehicle.getId(),
                vehicle.getMark(),
                vehicle.getModel(),
                vehicle.getColor(),
                vehicle.getProductionDate());
    }


    public List<Vehicle> findAllVehicles() {
        return vehicleDao.findAllVehicles();
    }


    public List<Vehicle> findAllByProductionYearRange(long startYear, long endYear) {
        return vehicleDao.findAllByProductionYearRange(startYear, endYear);
    }

}
