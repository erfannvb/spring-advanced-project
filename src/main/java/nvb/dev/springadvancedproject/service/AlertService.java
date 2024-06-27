package nvb.dev.springadvancedproject.service;

import nvb.dev.springadvancedproject.dto.request.AlertRequest;
import nvb.dev.springadvancedproject.dto.response.AlertResponse;

import java.util.List;
import java.util.Optional;

public interface AlertService {

    AlertResponse sendAlert(AlertRequest alertRequest);

    Optional<AlertResponse> getAlertById(String alertId);

    List<AlertResponse> getAllAlerts();

}
