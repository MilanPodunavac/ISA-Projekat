package code.service;

import code.exceptions.provider_registration.EmailTakenException;
import code.exceptions.provider_registration.UnauthorizedAccessException;
import code.exceptions.provider_registration.UserNotFoundException;
import code.model.cottage.Cottage;
import code.model.cottage.CottageOwner;
import code.model.report.YearlyProfitReport;

import java.util.List;

public interface CottageOwnerService {
    void save(CottageOwner cottageOwner) throws EmailTakenException;
    List<Cottage> getCottageOwnerCottages() throws UnauthorizedAccessException, UserNotFoundException;
    YearlyProfitReport calculateYearlyProfitReport() throws UnauthorizedAccessException, UserNotFoundException;
}
