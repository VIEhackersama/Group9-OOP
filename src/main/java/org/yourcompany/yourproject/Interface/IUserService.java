package org.yourcompany.yourproject.Interface;

import java.util.List;

import org.yourcompany.yourproject.Entity.PricePrediction;
import org.yourcompany.yourproject.Entity.User;

public interface IUserService {
    public User findUserByEmail(String email);

    public void saveUser(User user);

    public int getNextUserId();

    public void savePrediction(PricePrediction prediction);

    public int getNextPredictionId();

    public List<PricePrediction> getPredictionsByUserId(int userId);
}
