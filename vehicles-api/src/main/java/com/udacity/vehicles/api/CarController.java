package com.udacity.vehicles.api;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.service.CarService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Implements a REST-based controller for the Vehicles API.
 */
@RestController
@RequestMapping(value = "/cars")
class CarController {

    private final CarService carService;
    private final CarResourceAssembler assembler;

    CarController(CarService carService, CarResourceAssembler assembler) {
        this.carService = carService;
        this.assembler = assembler;
    }

    /**
     * Creates a list to store any vehicles.
     * @return list of vehicles
     */
    @GetMapping
    Resources<Resource<Car>> list() {
        List<Resource<Car>> resources = carService.list().stream().map(assembler::toResource)
                .collect(Collectors.toList());
        return new Resources<>(resources,
                linkTo(methodOn(CarController.class).list()).withSelfRel());
    }

    /**
     * Gets information of a specific car by ID.
     * @param id the id number of the given vehicle
     * @return all information for the requested vehicle
     * http://localhost:8080/cars/?id=1
     * http://localhost:8080/cars?id=1
     */
    @GetMapping("/{id}")
    Resource<Car> get(@PathVariable Long id) {
        /**
         * TODO: Use the `findById` method from the Car Service to get car information.
         * TODO: Use the `assembler` on that car and return the resulting output.
         *   Update the first line as part of the above implementing.
         */
        Optional<Car> car = Optional.ofNullable(carService.findById(id));

        return assembler.toResource(car.orElse(new Car()));
    }

    /**
     * Posts information to create a new vehicle in the system.
     * @param car A new vehicle to add to the system.
     * @return response that the new vehicle was added to the system
     * @throws URISyntaxException if the request contains invalid fields or syntax
     */
    @PostMapping
    ResponseEntity<?> post(@Valid @RequestBody Car car) throws URISyntaxException {
        /**
         * TODO: Use the `save` method from the Car Service to save the input car.
         * TODO: Use the `assembler` on that saved car and return as part of the response.
         *   Update the first line as part of the above implementing.
         */
        if (car != null) {
            Car savedCar = carService.save(car);
            Resource<Car> resource = assembler.toResource(savedCar);
            return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
        }
        Resource<Car> resource = assembler.toResource(new Car());
        return ResponseEntity.badRequest().build();
    }

    /**
     * Updates the information of a vehicle in the system.
     * @param id The ID number for which to update vehicle information.
     * @param car The updated information about the related vehicle.
     * @return response that the vehicle was updated in the system
     */
    @PostMapping("update")
    ResponseEntity<?> update(@RequestParam(name = "id") Long id, @Valid @RequestBody Car car) {
        /**
         * TODO: Set the id of the input car object to the `id` input.
         * TODO: Save the car using the `save` method from the Car service
         * TODO: Use the `assembler` on that updated car and return as part of the response.
         *   Update the first line as part of the above implementing.
         */

        if (car != null) {
            car.setId(id);
            Car res = carService.save(car);
            Resource<Car> resource = assembler.toResource(res);
            return ResponseEntity.ok(resource);
//            return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
        }
        Resource<Car> resource = assembler.toResource(new Car());
        return ResponseEntity.ok(resource);
    }

    /**
     * Removes a vehicle from the system.
     * @param id The ID number of the vehicle to remove.
     * @return response that the related vehicle is no longer in the system
     */
    @PostMapping("delete")
    ResponseEntity<?> delete(@RequestParam(name = "id", required = true) Long id) {
        /**
         * TODO: Use the Car Service to delete the requested vehicle.
         */
        Optional<Car> optionalCar = Optional.ofNullable(carService.findById(id));
        if (optionalCar.isPresent()){
            carService.delete(id);
        }
        return ResponseEntity.noContent().build();
    }
}
