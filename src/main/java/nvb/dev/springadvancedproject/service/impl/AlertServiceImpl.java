package nvb.dev.springadvancedproject.service.impl;

import lombok.RequiredArgsConstructor;
import nvb.dev.springadvancedproject.dto.request.AlertRequest;
import nvb.dev.springadvancedproject.dto.response.AlertResponse;
import nvb.dev.springadvancedproject.mapper.AlertMapper;
import nvb.dev.springadvancedproject.model.AlertEntity;
import nvb.dev.springadvancedproject.repository.AlertRepository;
import nvb.dev.springadvancedproject.service.AlertService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlertServiceImpl implements AlertService {

    private final AlertRepository alertRepository;
    private final AlertMapper alertMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AlertResponse sendAlert(AlertRequest alertRequest) {
        AlertEntity alert = AlertEntity.builder()
                .message(alertRequest.getMessage())
                .build();
        AlertEntity savedAlert = alertRepository.save(alert);
        return alertMapper.toAlertResponse(savedAlert);
    }

    @Override
    public Optional<AlertResponse> getAlertById(String alertId) {
        return Optional.ofNullable(alertRepository.findById(alertId)
                .map(alertMapper::toAlertResponse)
                .orElseThrow(IllegalCallerException::new));
    }

    @Override
    public List<AlertResponse> getAllAlerts() {
        List<AlertEntity> alertList = alertRepository.findAll();
        if (alertList.isEmpty()) throw new IllegalArgumentException();
        return alertList.stream().map(alertMapper::toAlertResponse).toList();
    }
}
