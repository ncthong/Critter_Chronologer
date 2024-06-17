package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.Exception.NotFoundException;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@AllArgsConstructor @Service
@Transactional
public class PetService {
    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;
    public List<Pet> getAllPets() { return petRepository.findAll();}
    public Pet getPetById(Long id) {
        return petRepository.findById(id).orElseThrow(() -> new NotFoundException("Pet is not found with ID: " + id));}
    public Pet savePet(Pet pet) {
        Pet savedPet = petRepository.save(pet);

        Customer customer = savedPet.getCustomer();
        if (customer != null) {
            List<Pet> pets = customer.getPets();
            if (!pets.contains(savedPet)) {
                pets.add(savedPet);
            }
            customer.setPets(pets);
            customerRepository.save(customer);
        }
        return savedPet;
    }
}
