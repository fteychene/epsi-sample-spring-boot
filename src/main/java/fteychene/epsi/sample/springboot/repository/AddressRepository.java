package fteychene.epsi.sample.springboot.repository;

import fteychene.epsi.sample.springboot.entity.Address;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by fteychene on 1/15/16.
 */
public interface AddressRepository extends CrudRepository<Address, Long> {
}
