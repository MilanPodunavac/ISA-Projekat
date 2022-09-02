package code.service;

import code.exceptions.provider_registration.EmailTakenException;
import code.exceptions.provider_registration.UnauthorizedAccessException;
import code.exceptions.provider_registration.UserNotFoundException;
import code.model.boat.Boat;
import code.model.boat.BoatOwner;
import code.model.report.YearlyProfitReport;

import java.util.List;

public interface BoatOwnerService {
    void save(BoatOwner boatOwner) throws EmailTakenException;
    List<Boat> getBoatOwnerBoats() throws UnauthorizedAccessException, UserNotFoundException;
    YearlyProfitReport calculateYearlyProfitReport() throws UnauthorizedAccessException, UserNotFoundException;
}
