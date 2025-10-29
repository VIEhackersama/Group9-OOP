package org.yourcompany.yourproject.Service;

import java.util.*;

import org.yourcompany.yourproject.Entity.House;

public interface HouseService {
    House createHouse(HouseDTO houseDTO);
    List<House> getAllHouses();
    Optional<House> getHouseById(String id);
    Optional<House> updateHouse(String id, HouseDTO houseDTO);
    boolean deleteHouse(String id);
}
