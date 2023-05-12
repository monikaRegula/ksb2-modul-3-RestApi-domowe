package pl.regula.ksb2modul3restapidomowe.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.regula.ksb2modul3restapidomowe.model.Vehicle;
import pl.regula.ksb2modul3restapidomowe.service.VehicleService;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private VehicleService service;

    public VehicleController(VehicleService service) {
        this.service = service;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<CollectionModel<Vehicle>> getVehicles() {
        List<Vehicle> vehicleList = service.findAllVehicles();
        vehicleList.forEach(v -> v.addIf(!v.hasLinks(), ()-> linkTo(VehicleController.class).slash(v.getId()).withSelfRel()));
        Link link = linkTo(VehicleController.class).withSelfRel();
        return new ResponseEntity<>( new CollectionModel(vehicleList, link), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable long id) {
        Optional<Vehicle> first = service.findById(id);
        return first.map(a -> {
            addLinkToVehicle(a);
            return ResponseEntity.ok(a);
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/color/{color}")
    //http://localhost:8080/vehicles/color/White
    public ResponseEntity<CollectionModel<Vehicle>> getVehicleByColor(@PathVariable String color) {

        List<Vehicle> vehicles = service.findAllVehiclesByColor(color);
        if (vehicles.size() != 0) {
            vehicles.forEach(car -> car.addIf(car.hasLinks(),()-> linkTo(VehicleController.class).slash("/color/"+car.getColor())
                    .withRel("all colors")));
            Link link = linkTo(VehicleController.class).withSelfRel();
            CollectionModel<Vehicle> carCollection = new CollectionModel<>(vehicles, link);
            return new ResponseEntity<>(carCollection, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity addVehicle(@RequestBody Vehicle vehicle) {
        Optional<Vehicle> added = service.addVehicle(vehicle);
        return added.map(a -> {
            addLinkToVehicle(a);
            return ResponseEntity.ok(a);
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @PutMapping
    public ResponseEntity modifyVehicle(@RequestBody Vehicle newVehicle) {
        Optional<Vehicle> first = service.updateVehicle(newVehicle);
        return first.map(vehicle -> {
            addLinkToVehicle(vehicle);
            return ResponseEntity.ok(vehicle);
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PatchMapping
    public ResponseEntity<Vehicle> modifyVehicleColor(@RequestParam Long id, @RequestParam String color) {
        Optional<Vehicle> first = service.updateVehicleByColor(id, color);
        return first.map(vehicle -> {
            addLinkToVehicle(vehicle);
            return ResponseEntity.ok(vehicle);
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removeVehicle(@PathVariable long id) {
        Optional<Vehicle> first = service.deletedById(id);
        return first.map(vehicle -> {
            addLinkToVehicle(vehicle);
            return ResponseEntity.ok(vehicle);
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    private void addLinkToVehicle(Vehicle vehicle) {
        vehicle.add(linkTo(VehicleController.class).slash(vehicle.getId()).withSelfRel());
    }

}
