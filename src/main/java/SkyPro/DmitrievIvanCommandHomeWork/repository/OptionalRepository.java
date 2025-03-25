package SkyPro.DmitrievIvanCommandHomeWork.repository;

import SkyPro.DmitrievIvanCommandHomeWork.modelAndConstants.Recommendation;

import java.util.Optional;
import java.util.UUID;

public interface OptionalRepository  {

    Optional<Recommendation>getRecommendation(UUID eserId);
}
