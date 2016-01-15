package fteychene.epsi.sample.springboot.controller;

import fteychene.epsi.sample.springboot.entity.Address;
import fteychene.epsi.sample.springboot.entity.User;
import fteychene.epsi.sample.springboot.repository.AddressRepository;
import fteychene.epsi.sample.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by fteychene on 1/15/16.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepositoy;
    @Autowired
    private AddressRepository addressRepository;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<User> getAll() {
        return userRepositoy.findAll();
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public User get(@PathVariable("id") Long id) {
        return userRepositoy.findOne(id);
    }

    @RequestMapping(value="/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        user.setAddresses(user.getAddresses().stream().map(address -> addressRepository.save(address)).collect(Collectors.toList())) ;
        return userRepositoy.save(user);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Iterable<User> create(@RequestBody Iterable<User> users) {
        for (User user : users) {
            user.getAddresses().stream().map(address -> addressRepository.save(address)) ;
        }
        return userRepositoy.save(users);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public User update(@PathVariable("id") Long id, @RequestBody User user) {
        user.setId(id);
        addressRepository.save(user.getAddresses());
        return userRepositoy.save(user);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public User patch(@PathVariable("id") Long id, @RequestBody User user) {
        User databaseUser  = userRepositoy.findOne(id);
        if (user.getUsername() != null) {
            databaseUser.setUsername(user.getUsername());
        }
        if (user.getPassword() != null) {
            databaseUser.setPassword(user.getPassword());
        }
        if (user.getFirstName() != null) {
            databaseUser.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
            databaseUser.setLastName(user.getLastName());
        }
        if (user.getAddresses() != null) {
            databaseUser.setAddresses(user.getAddresses());
            addressRepository.save(user.getAddresses());
        }
        return userRepositoy.save(databaseUser);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        userRepositoy.delete(id);
    }

    public UserRepository getUserRepositoy() {
        return userRepositoy;
    }

    public void setUserRepositoy(UserRepository userRepositoy) {
        this.userRepositoy = userRepositoy;
    }

    public AddressRepository getAddressRepository() {
        return addressRepository;
    }

    public void setAddressRepository(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }
}
