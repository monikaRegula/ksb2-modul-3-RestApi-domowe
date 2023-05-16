package pl.regula.ksb2modul3restapidomowe.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.regula.ksb2modul3restapidomowe.model.Vehicle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class VehicleDaoImpl implements VehicleDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public VehicleDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int saveVehicle(long id, String mark, String model, String color, Date productionDate) {
    String sql = "INSERT INTO vehicles VALUES (?,?,?,?,?)";
    return jdbcTemplate.update(sql,id, mark, model, color, productionDate);
    }

    @Override
    public List<Vehicle> findAllVehicles() {
        String sql = "SELECT * FROM vehicles";
        List<Vehicle> vehicles = new ArrayList<>();
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        maps.stream().forEach(elem -> vehicles.add(new Vehicle(
                Long.parseLong(String.valueOf(elem.get("vehicle_id"))),
                String.valueOf(elem.get("mark")),
                String.valueOf(elem.get("model")),
                String.valueOf(elem.get("color")),
                //todo konwersja daty
                (Date) elem.get("production_date")
        )));
        return vehicles;
    }

    @Override
    public List<Vehicle> findAllByProductionYearRange(long start, long end) {
        String sql = "SELECT * FROM VEHICLES WHERE (YEAR(production_date) BETWEEN ? AND ?)";
        List<Vehicle> vehicles = new ArrayList<>();
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, start, end);
        maps.stream().forEach(elem -> vehicles.add(new Vehicle(
                Long.parseLong(String.valueOf(elem.get("vehicle_id"))),
                String.valueOf(elem.get("mark")),
                String.valueOf(elem.get("model")),
                String.valueOf(elem.get("color")),
                (Date) elem.get("production_date")
        )));
        return vehicles;
    }
}
