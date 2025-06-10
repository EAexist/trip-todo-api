package com.matchalab.trip_todo_api.model.DTO;

import java.util.List;

import com.matchalab.trip_todo_api.model.Link;
import com.matchalab.trip_todo_api.model.Trip;

import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class AccomodationDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "trip_id")
    // private Trip trip;
    private String title;
    private String roomTitle;
    private int numberofClient;
    private String clientName;
    private String checkinDateISOString;
    private String checkoutDateISOString;
    private String checkinStartTimeISOString;
    private String checkinEndTimeISOString;
    private String checkoutTimeISOString;
    private String region;
    private String type;
    private List<Link> links;
}
