package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.dto.Models.RideModel;
import com.example.easy_flow_backend.entity.Status;
import com.example.easy_flow_backend.error.BadRequestException;
import com.example.easy_flow_backend.error.NotFoundException;
import com.example.easy_flow_backend.error.ResponseMessage;
import com.example.easy_flow_backend.repos.PassengersRepo;
import com.example.easy_flow_backend.repos.StationaryTurnstileRepo;
import com.example.easy_flow_backend.repos.TripRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class StationeryTurnstileServiceImplementation implements StationeryTurnstileService, TurnstileService {
    @Autowired
    private TripRepo tripRepo;
    @Autowired
    private PassengersRepo passengersRepo;
    @Autowired
    private StationaryTurnstileRepo stationaryTurnstileRepo;
    @Autowired
    WalletService walletService;

    @Autowired
    TicketService ticketService;

    @Autowired
    TripService tripService;

    @Autowired
    TokenValidationService tokenValidationService;

    private void inRideValidation(RideModel rideModel, String machineUsername) throws BadRequestException {
        if (!tokenValidationService.validatePassengerToken(rideModel.getToken(), rideModel.getUsername())
                || !tokenValidationService.validateGenerationTime(rideModel.getGenerationTime(), rideModel.getUsername()))
            throw new BadRequestException("Illegal QR");
        //validate authintication
        if (machineUsername == null || machineUsername.equalsIgnoreCase("anonymous")) {
            throw new BadRequestException("Not Authenticated");
        }

        //check if there is exists a pending trip for this user
        if (tripRepo.existsByPassengerUsernameAndStatus(rideModel.getUsername(), Status.Pending)) {
            throw new BadRequestException("You Can not make Ride as you have pending Request");
        }
        //validate the existence of the  passenger
        if (!passengersRepo.existsByUsernameIgnoreCase(rideModel.getUsername())) {
            throw new BadRequestException("Passenger Not found!");
        }
        // validate that the machine is Stationary turnstile machine
        if (!stationaryTurnstileRepo.existsByUsernameIgnoreCase(machineUsername)) {
            throw new BadRequestException("Access Denied!");
        }


    }


    @Override
    public ResponseMessage inRide(RideModel rideModel) throws BadRequestException, NotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String machineUsername = auth.getPrincipal().toString();

        inRideValidation(rideModel, machineUsername);


        return tripService.makePendingTrip(rideModel, machineUsername);
    }


    private void outRideValidation(RideModel rideModel, String machineUsername) throws BadRequestException {

        if (!tokenValidationService.validatePassengerToken(rideModel.getToken(), rideModel.getUsername()))
            throw new BadRequestException("Illegal QR");

        if (machineUsername == null || machineUsername.equalsIgnoreCase("anonymous")) {
            throw new BadRequestException("Not Authenticated");
        }
        if (!tripRepo.existsByPassengerUsernameAndStatus(rideModel.getUsername(), Status.Pending)) {
            throw new BadRequestException("Failed No Binding trips");
        }
        //validate that machine is movingTurn stile machine
        if (!stationaryTurnstileRepo.existsByUsernameIgnoreCase(machineUsername)) {
            throw new BadRequestException("Access Denied!");
        }
    }

    @Override
    public ResponseMessage outRide(RideModel rideModel) throws BadRequestException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String machineUsername = auth.getPrincipal().toString();

        //validate
        outRideValidation(rideModel, machineUsername);

        return tripService.makeFinalTrip(rideModel, machineUsername);
    }
}
