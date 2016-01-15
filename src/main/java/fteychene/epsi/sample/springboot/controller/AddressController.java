package fteychene.epsi.sample.springboot.controller;

import fteychene.epsi.sample.springboot.entity.Address;
import fteychene.epsi.sample.springboot.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

/**
 * Created by fteychene on 1/15/16.
 */
@RestController
@RequestMapping("/adress")
public class AddressController {

    @Autowired
    private AddressRepository repositoy;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Address> getAll() {
        return repositoy.findAll();
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Address get(@PathVariable("id") Long id) {
        return repositoy.findOne(id);
    }

    @RequestMapping(value="/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Address create(@RequestBody Address user) {
        return repositoy.save(user);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Iterable<Address> create(@RequestBody Iterable<Address> user) {
        return repositoy.save(user);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Address update(@PathVariable("id") Long id, @RequestBody Address user) {
        user.setId(id);
        return repositoy.save(user);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public Address patch(@PathVariable("id") Long id, @RequestBody Address address) {
        Address databaseAddress  = repositoy.findOne(id);
        if (address.getAdress() != null) {
            databaseAddress.setAdress(address.getAdress());
        }
        if (address.getComplement1() != null) {
            databaseAddress.setComplement1(address.getComplement1());
        }
        if (address.getCity() != null) {
            databaseAddress.setCity(address.getCity());
        }
        if (address.getZipCode() != null) {
            databaseAddress.setZipCode(address.getZipCode());
        }
        if (address.getCountry() != null) {
            databaseAddress.setCountry(address.getCountry());
        }
        return repositoy.save(databaseAddress);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathParam("id") Long id) {
        repositoy.delete(id);
    }

    public AddressRepository getRepositoy() {
        return repositoy;
    }

    public void setRepositoy(AddressRepository repositoy) {
        this.repositoy = repositoy;
    }
}
