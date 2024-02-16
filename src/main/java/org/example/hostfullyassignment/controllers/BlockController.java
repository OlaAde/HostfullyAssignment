package org.example.hostfullyassignment.controllers;


import lombok.RequiredArgsConstructor;
import org.example.hostfullyassignment.dtos.BlockDto;
import org.example.hostfullyassignment.dtos.BlockPayloadDto;
import org.example.hostfullyassignment.exceptions.InvalidPayloadException;
import org.example.hostfullyassignment.exceptions.OperationNotPermittedException;
import org.example.hostfullyassignment.exceptions.ResourceNotFoundException;
import org.example.hostfullyassignment.services.BlockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/properties/{propertyId}/blocks")
@RequiredArgsConstructor
public class BlockController {
    private final BlockService blockService;

    @PostMapping
    public ResponseEntity<BlockDto> createBlock(@PathVariable UUID propertyId, @RequestBody BlockPayloadDto payload) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(blockService.createBlock(propertyId, payload));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getLocalizedMessage())).build();
        } catch (InvalidPayloadException e) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getLocalizedMessage())).build();
        } catch (OperationNotPermittedException e) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, e.getLocalizedMessage())).build();
        }
    }

    @PutMapping("/{blockId}")
    public ResponseEntity<BlockDto> updateBlock(@PathVariable UUID propertyId, @PathVariable UUID blockId,
                                                @RequestBody BlockPayloadDto payload) {
        try {
            return ResponseEntity.ok(blockService.updateBlock(propertyId, blockId, payload));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getLocalizedMessage())).build();
        } catch (InvalidPayloadException e) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getLocalizedMessage())).build();
        } catch (OperationNotPermittedException e) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, e.getLocalizedMessage())).build();
        }
    }

    @DeleteMapping("/{blockId}")
    public void deleteBlock(@PathVariable UUID blockId) {
        blockService.deleteBlock(blockId);
    }
}
