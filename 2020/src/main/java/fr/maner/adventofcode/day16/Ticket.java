package fr.maner.adventofcode.day16;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ticket {

    private final List<Long> ticketNumber = new ArrayList<>();

    public static Ticket parse(String line) {
        Ticket ticket = new Ticket();
        Arrays.asList(line.split(",")).forEach(el -> ticket.addTicket(Integer.parseInt(el)));
        return ticket;
    }

    public List<Long> getTicketNumber() {
        return this.ticketNumber;
    }

    public long getNumberByPosition(int position) {
        return this.ticketNumber.get(position);
    }

    public long getInvalidValue(List<Rule> rules) {
        for (long value : getTicketNumber()) {
            boolean valid = false;

            for (Rule rule : rules)
                if (rule.contains(value))
                    valid = true;

            if (!valid) {
                return value;
            }
        }

        return -1L;
    }

    public void addTicket(long value) {
        this.ticketNumber.add(value);
    }
}
