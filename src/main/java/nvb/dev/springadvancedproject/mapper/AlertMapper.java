package nvb.dev.springadvancedproject.mapper;

import nvb.dev.springadvancedproject.dto.response.AlertResponse;
import nvb.dev.springadvancedproject.model.AlertEntity;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AlertMapper {

    @Mapping(source = "alertEntity.id", target = "alertId")
    @Mapping(source = "alertEntity.message", target = "message")
    AlertResponse toAlertResponse(AlertEntity alertEntity);

}
