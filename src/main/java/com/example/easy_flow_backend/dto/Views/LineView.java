package com.example.easy_flow_backend.dto.Views;

import com.example.easy_flow_backend.entity.Owner;

import java.util.Set;

public interface LineView {
    String getName();
    String getType();
    OwnerView getOwner();
    //Set<StationView> getStations();
}
