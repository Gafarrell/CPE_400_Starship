package io.github.gafarrell.events;

import io.github.gafarrell.Connection;
import io.github.gafarrell.Vendor;

import java.util.Random;

public class Event {
    enum Action{
        ORDER("Order"),
        DRIVE_TO("Driving"),
        CANCEL_ACTION("Action Cancelled"),
        UNMAPPED_OBJECT("Routed around Unmapped Object"),
        POSSIBLE_VANDALISM("Possible Vandalism"),
        END_OF_DAY("End of day Report"),
        DRIVE_REPORT("Drive Report");

        private String msg;
        Action(String msg){this.msg = msg;}
    }

    private Packet data;
    private String eventName;

    // Used for robot DRIVE_TO event or robot DRIVE_REPORT event.
    public Event(Connection<Vendor> c)
    {

    }

    // All other events use this type.
    public Event(Action action)
    {

    }

    public Event(){
        Random rando = new Random();
        switch (rando.nextInt(7)){
            case 0:
                this.eventName = Action.ORDER.msg;
        }

    }
}
