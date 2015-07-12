package com.wirelust.experiment.hibernatesearch.repositories;

import com.wirelust.experiment.hibernatesearch.model.City;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

/**
 * Date: 12-Jul-2015
 *
 * @author T. Curran
 */
@Repository
public interface CityRepository extends EntityRepository<City, Long> {

}
