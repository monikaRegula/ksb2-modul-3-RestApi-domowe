package pl.regula.ksb2modul3restapidomowe.dao;

import pl.regula.ksb2modul3restapidomowe.model.Vehicle;

import java.util.Date;
import java.util.List;

public interface VehicleDao {

    int saveVehicle(long id, String mark, String model, String color, Date productionDate);

    List<Vehicle> findAllVehicles();

    List<Vehicle> findAllByProductionYearRange(long start, long end);
}
