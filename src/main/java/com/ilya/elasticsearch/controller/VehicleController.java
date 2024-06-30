package com.ilya.elasticsearch.controller;

import com.ilya.elasticsearch.document.Person;
import com.ilya.elasticsearch.document.Vehicle;
import com.ilya.elasticsearch.service.PersonService;
import com.ilya.elasticsearch.service.VehicleService;
import com.ilya.elasticsearch.service.elasticsearch.ElasticsearchRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {
    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public void save(@RequestBody final Vehicle vehicle) {
        this.vehicleService.index(vehicle);
    }

    @PostMapping("/search")
    public List<Vehicle> search(@RequestBody final ElasticsearchRequestDto dto) {
        var a = 1;
        return this.vehicleService.search(dto);
    }

    @GetMapping("/{id}")
    public Vehicle findById(@PathVariable final String id) {
        return this.vehicleService.getById(id);
    }
}
