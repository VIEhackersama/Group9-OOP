package org.yourcompany.yourproject.Service;

import org.yourcompany.yourproject.Entity.House;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;

    public HouseServiceImpl(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    @Override
    public House createHouse(HouseDTO houseDTO) {
        House house = new House();
        mapDtoToEntity(houseDTO, house);
        return houseRepository.save(house);
    }

    @Override
    public List<House> getAllHouses() {
        return houseRepository.findAll();
    }

    @Override
    public Optional<House> getHouseById(String id) {
        return houseRepository.findById(id);
    }

    @Override
    public Optional<House> updateHouse(String id, HouseDTO houseDTO) {
        Optional<House> existingHouseOptional = houseRepository.findById(id);

        if (existingHouseOptional.isPresent()) {
            House existingHouse = existingHouseOptional.get();
            mapDtoToEntity(houseDTO, existingHouse);

            return Optional.of(houseRepository.save(existingHouse));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteHouse(String id) {
        if (houseRepository.existsById(id)) {
            houseRepository.deleteById(id);
            return true; 
        }
        return false; 
    }

    private void mapDtoToEntity(HouseDTO dto, House entity) {
        entity.setArea(dto.getArea());
        entity.setAddress(dto.getAddress());
        entity.setStreetInFrontOfHouse(dto.getStreetInFrontOfHouse());
        entity.setWidth(dto.getWidth());
        entity.setHeight(dto.getHeight());
        entity.setFloorNumber(dto.getFloorNumber());
        entity.setBedroomNumber(dto.getBedroomNumber());
        entity.setBathroomNumber(dto.getBathroomNumber());
        entity.setDirection(dto.getDirection());
        entity.setLaw(dto.getLaw());
        entity.setPrice(dto.getPrice());
    }
}