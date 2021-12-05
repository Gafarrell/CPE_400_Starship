package io.github.gafarrell.events;

import io.github.gafarrell.Connection;
import io.github.gafarrell.Drone;
import io.github.gafarrell.Main;
import io.github.gafarrell.Vendor;

import java.util.Random;

public class Event {
    public enum Action{
        WAIT_FOR_CUSTOMER("Order"),
        DRIVE_TO("Driving"),
        CANCEL_ACTION("Action Cancelled"),
        UNMAPPED_OBJECT("Routed around Unmapped Object"),
        POSSIBLE_VANDALISM("Possible Vandalism"),
        END_OF_DAY("End of day Report"),
        DRIVE_REPORT("Drive Report");

        private String msg;
        Action(String msg){this.msg = msg;}
    }

    private Vendor destination;
    private Action action;

    // Used for robot DRIVE_TO event or robot DRIVE_REPORT event.
    public Event(Drone d, Connection<Vendor> c)
    {
        this.destination = c.getDest();
        d.driveTo(c);
        this.action = Action.DRIVE_TO;
    }

    // All other events use this type.
    public Event(Action action)
    {
        this.action = action;
    }

    public Event(){
        Random rando = new Random();
        switch (rando.nextInt(7)) {
            case 0 -> {
                this.action = Action.WAIT_FOR_CUSTOMER;
            }
            case 2 -> {
                this.action = Action.CANCEL_ACTION;
            }
            case 3 -> {
                this.action = Action.UNMAPPED_OBJECT;
            }
            case 4 -> {
                this.action = Action.POSSIBLE_VANDALISM;
                this.destination = Main.Hub;
            }
            case 6 -> {
                this.action = Action.DRIVE_REPORT;
            }
            default -> {
                this.action = Action.DRIVE_TO;
                this.destination = Vendor.allVendors.get(rando.nextInt(Vendor.allVendors.size()));
            }
        }
    }

    public Action getType(){
        return this.action;
    }
}
