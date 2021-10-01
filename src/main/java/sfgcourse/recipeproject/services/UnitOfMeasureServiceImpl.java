package sfgcourse.recipeproject.services;

import org.springframework.stereotype.Service;
import sfgcourse.recipeproject.commands.UnitOfMeasureCommand;
import sfgcourse.recipeproject.converters.UnitOfMeasureToUnitOfMeasureCommand;
import sfgcourse.recipeproject.repositories.UnitOfMeasureRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    UnitOfMeasureRepository unitOfMeasureRepository;
    UnitOfMeasureToUnitOfMeasureCommand UToUCConverter;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, UnitOfMeasureToUnitOfMeasureCommand UToUCConverter) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.UToUCConverter = UToUCConverter;
    }

    @Override
    public Set<UnitOfMeasureCommand> listAllUoms() {
        Set<UnitOfMeasureCommand> unitOfMeasureCommands = new HashSet<>();
        unitOfMeasureRepository.findAll().forEach(uom -> unitOfMeasureCommands.add(UToUCConverter.convert(uom)));
        return unitOfMeasureCommands;
    }
}
