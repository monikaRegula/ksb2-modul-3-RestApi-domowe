package pl.regula.ksb2modul3restapidomowe.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.regula.ksb2modul3restapidomowe.entity.Vehicle;
import pl.regula.ksb2modul3restapidomowe.service.VehicleService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private VehicleService service;

    public VehicleController(VehicleService service) {
        this.service = service;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Vehicle>> getVehicles() {
        List<Vehicle> vehicleList = service.findAllVehicles();
        return new ResponseEntity<>(vehicleList, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable Long id) {
        Optional<Vehicle> first = service.findById(id);
        return first.map(a -> {
                    return ResponseEntity.ok(a);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/color/{color}")
    //http://localhost:8080/vehicles/color/White
    public ResponseEntity<List<Vehicle>> getVehicleByColor(@PathVariable String color) {
        List<Vehicle> vehicles = service.findAllVehiclesByColor(color);
        if (vehicles.size() != 0) {
            return new ResponseEntity<>(vehicles, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity addVehicle(@RequestBody Vehicle vehicle) {
        Optional<Vehicle> added = service.addVehicle(vehicle);
        return added.map(a -> {
            return ResponseEntity.ok(a);
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @PutMapping
    public ResponseEntity modifyVehicle(@RequestBody Vehicle newVehicle) {
        Optional<Vehicle> first = service.updateVehicle(newVehicle);
        return first.map(a -> {
            return ResponseEntity.ok(a);
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PatchMapping("/id/{id}/color/{color}")
    public ResponseEntity<Vehicle> modifyVehicleColor(@PathVariable Long id, @PathVariable String color) {
        Optional<Vehicle> first = service.updateVehicleByColor(id, color);
        return first.map(vehicle -> {
            return ResponseEntity.ok(vehicle);
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removeVehicle(@PathVariable Long id) {
        Optional<Vehicle> first = service.deletedById(id);
        return first.map(vehicle -> {
            return ResponseEntity.ok(vehicle);
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

}
