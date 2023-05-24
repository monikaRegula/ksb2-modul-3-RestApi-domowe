package pl.regula.ksb2modul3restapidomowe.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.regula.ksb2modul3restapidomowe.model.Vehicle;
import pl.regula.ksb2modul3restapidomowe.service.VehicleService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService service;

    public VehicleController(VehicleService service) {
        this.service = service;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Vehicle>> getVehicles() {
        List<Vehicle> vehicleList = service.findAllVehicles();
        return new ResponseEntity<>(vehicleList, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity addVehicle(@RequestBody Vehicle vehicle) {
        int isAdded = service.addVehicle(vehicle);
        return ResponseEntity.ok(isAdded);
    }


    @GetMapping("/start/{start}/end/{end}")
    public ResponseEntity<List<Vehicle>> getVehiclesByProductionYearRange(@PathVariable Long start, @PathVariable Long end) {
        List<Vehicle> vehicles = service.findAllByProductionYearRange(start, end);
        return ResponseEntity.ok(vehicles);
    }

}
