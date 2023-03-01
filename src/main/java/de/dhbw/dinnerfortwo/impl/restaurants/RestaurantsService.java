package de.dhbw.dinnerfortwo.impl.restaurants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The PersonService contains the operations related to managing Persons.
 */
@Service
public class RestaurantsService {
    public RestaurantsService(RestaurantsRepository restaurantsRepository) {
        this.restaurantsRepository = restaurantsRepository;
    }

    private final RestaurantsRepository restaurantsRepository;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Transactional
    public RestaurantTO getResto(long id) {
        log.info("Looking for a restaurant with id {}", id);
        Restaurants restaurantsById = restaurantsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Could not find restaurants with Id " + id));

        RestaurantTO getRestaurantsById = restaurantsById.toDTO();

        return getRestaurantsById;
    }

    @Transactional
    public List<RestaurantTO> getAllResto() {
        log.info("Get all restaurants");
        List<RestaurantTO> getAllResto = ((List<Restaurants>) restaurantsRepository.findAll())
                .stream()
                .map(Restaurants::toDTO)
                .collect(Collectors.toList());;

        return getAllResto;
    }
    @Transactional
    public RestaurantTO create(RestaurantTO restaurants) {
        log.info("Save or update restaurant {}", restaurants);

        Restaurants restaurantsTO = Restaurants.toEntity(restaurants);
        Restaurants savedEntity = restaurantsRepository.save(restaurantsTO);

        return savedEntity.toDTO();
    }
}
