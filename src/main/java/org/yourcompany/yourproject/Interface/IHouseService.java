package org.yourcompany.yourproject.Interface;

import java.util.List;

import org.yourcompany.yourproject.Entity.House;

public interface IHouseService {
    public List<House> loadHousesFromDb();
}
