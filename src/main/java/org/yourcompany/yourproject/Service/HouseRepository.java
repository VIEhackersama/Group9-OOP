package org.yourcompany.yourproject.Service;

import org.yourcompany.yourproject.Entity.House;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseRepository extends MongoRepository<House, String> {
    // List<House> findByBedroomNumber(int bedroomCount);
    // List<House> findByPriceBetween(double minPrice, double maxPrice);
}