package pl.regula.ksb2modul3restapidomowe.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.regula.ksb2modul3restapidomowe.entity.Vehicle;
import pl.regula.ksb2modul3restapidomowe.service.VehicleService;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/vehicles")
public class VehicleConroller {

    private VehicleService service;

    public VehicleConroller(VehicleService service) {
        this.service = service;
    }


    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
//    @RequestMapping("/{all}")
    //fixme
    public ResponseEntity<CollectionModel<Vehicle>> getVehicles() {
        List<Vehicle> vehicleList = service.findAllVehicles();
        vehicleList.forEach(v -> v.addIf(!v.hasLinks(), ()-> linkTo(VehicleConroller.class).slash(v.getId()).withSelfRel()));
        Link link = linkTo(VehicleConroller.class).withSelfRel();
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

    //fixme
//    @GetMapping
//    public ResponseEntity<List<Vehicle>> getVehicleByColor(@RequestParam(defaultValue = "") String color ) {
//        List<Vehicle> vehicles = vehicleList.stream().filter(vehicle -> vehicle.getColor().equals(color)).collect(Collectors.toList());
//        if (vehicles.size() != 0) {
//            return new ResponseEntity<>(vehicles, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

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
        vehicle.add(linkTo(VehicleConroller.class).slash(vehicle.getId()).withSelfRel());
    }

}
