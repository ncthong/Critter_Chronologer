package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.Exception.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.Exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.services.ConvertDtoService;
import com.udacity.jdnd.course3.critter.services.CustomerService;
import com.udacity.jdnd.course3.critter.services.PetService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
@AllArgsConstructor
public class PetController {
    private final PetService petService;
    private final ConvertDtoService convertDtoService;
    private final CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = convertDtoService.convertDTOToPet(petDTO);
        pet = petService.savePet(pet);
        return convertDtoService.convertPetToDTO(pet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getPetById(petId);
        if (pet == null) {
            throw new PetNotFoundException("Pet not found with ID: " + petId);
        }
        return convertDtoService.convertPetToDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getAllPets(){
        List<PetDTO> petsDTO = new ArrayList<>();
        List<Pet> pets = this.petService.getAllPets();
        for(Pet pet:pets){
            petsDTO.add(convertDtoService.convertPetToDTO(pet));
        }
        return petsDTO;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        Customer customer = customerService.findCustomerById(ownerId);
        if (customer == null || customer.getPets() == null) {
            throw new CustomerNotFoundException("Customer or pets not found for ownerId: " + ownerId);
        }
        return customer.getPets().stream()
                .map(convertDtoService::convertPetToDTO)
                .collect(Collectors.toList());
    }

}
