package org.example.hostfullyassignment.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.hostfullyassignment.dtos.BlockDto;
import org.example.hostfullyassignment.dtos.BlockPayloadDto;
import org.example.hostfullyassignment.entities.Block;
import org.example.hostfullyassignment.entities.Property;
import org.example.hostfullyassignment.exceptions.InvalidPayloadException;
import org.example.hostfullyassignment.exceptions.OperationNotPermittedException;
import org.example.hostfullyassignment.exceptions.ResourceNotFoundException;
import org.example.hostfullyassignment.repositories.BlockRepository;
import org.example.hostfullyassignment.repositories.PropertyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BlockService {
    private final BlockRepository blockRepository;
    private final PropertyRepository propertyRepository;
    private final ValidatorService validatorService;
    private final ModelMapper modelMapper;

    @Transactional
    public BlockDto createBlock(UUID propertyId, BlockPayloadDto payload) throws ResourceNotFoundException, InvalidPayloadException, OperationNotPermittedException {
        Property property = propertyRepository.findById(propertyId).orElseThrow(ResourceNotFoundException::new);
        validatorService.validateRangeIsFree(property, payload.startDate(), payload.endDate());
        validatorService.userIsPermitted(property);

        Block block = modelMapper.map(payload, Block.class);
        block.setProperty(property);
        block = blockRepository.save(block);

        return new BlockDto(block.getId(), block.getStartDate(), block.getEndDate());
    }

    @Transactional
    public BlockDto updateBlock(UUID propertyId, UUID blockId, BlockPayloadDto payload) throws ResourceNotFoundException, InvalidPayloadException, OperationNotPermittedException {
        Property property = propertyRepository.findById(propertyId).orElseThrow(() -> new ResourceNotFoundException("Property not found"));

        Block block = blockRepository.findById(blockId).orElseThrow(() -> new ResourceNotFoundException("Block not found"));
        blockRepository.delete(block);

        validatorService.validateRangeIsFreeExcludingBlock(property, payload.startDate(), payload.endDate(), block);
        validatorService.userIsPermitted(property);

        block.setStartDate(payload.startDate());
        block.setEndDate(payload.endDate());
        block = blockRepository.save(block);

        return new BlockDto(block.getId(), block.getStartDate(), block.getEndDate());
    }

    @Transactional
    public void deleteBlock(UUID blockId) {
        blockRepository.deleteById(blockId);
    }

}
