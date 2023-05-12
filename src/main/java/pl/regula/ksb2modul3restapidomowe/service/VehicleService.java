package pl.regula.ksb2modul3restapidomowe.service;

import org.springframework.stereotype.Service;
import pl.regula.ksb2modul3restapidomowe.model.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    private List<Vehicle> vehicleList;

    public VehicleService() {
        this.vehicleList = new ArrayList<>();
        vehicleList.add(new Vehicle(1L, "Ford", "Fiesta", "Red"));
        vehicleList.add(new Vehicle(2L, "Fiat", "126p", "Blue"));
        vehicleList.add(new Vehicle(3L, "Opel", "Astra", "White"));
    }

    public Optional<Vehicle> addVehicle(Vehicle vehicle) {
        boolean isVehicleExists = vehicleList.stream().anyMatch(v -> v.getId().equals(vehicle.getId()));
        return isVehicleExists ? Optional.empty() : Optional.of(saveProduct(vehicle));
    }

    private Vehicle saveProduct(Vehicle product) {
        vehicleList.add(product);
        return product;
    }

    public Optional<Vehicle> findById(Long vehicleId) {
        return vehicleList.stream().filter(element -> element.getId() == vehicleId).findFirst();
    }

    public Optional<Vehicle> deletedById(Long vehicleId) {
        Optional<Vehicle> byId = findById(vehicleId);
        vehicleList.remove(byId);
        return findById(vehicleId);

    }

    public List<Vehicle> findAllVehicles() {
        return vehicleList;
    }

    public List<Vehicle> findAllVehiclesByColor(String color) {
        return vehicleList.stream().filter(vehicle -> vehicle.getColor().equalsIgnoreCase(color)).collect(Collectors.toList());
    }

    public Optional<Vehicle> updateVehicle(Vehicle newVehicle) {
        return vehicleList.stream()
                .filter(element -> element.getId() == newVehicle.getId())
                .findFirst()
                .map(element -> {
                    element.setMark(newVehicle.getMark());
                    element.setModel(newVehicle.getModel());
                    element.setColor(newVehicle.getColor());
                    return element;
                });
    }

    public Optional<Vehicle> updateVehicleByColor(Long id, String color) {
        return vehicleList.stream()
                .filter(element -> element.getId() == id)
                .findFirst()
                .map(element -> {
                    element.setColor(color);
                    return element;
                });
    }
}
