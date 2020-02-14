package com.armakuni.event_sourcing_example;

import com.armakuni.event_sourcing_example.errors.*;
import com.armakuni.event_sourcing_example.events.ItemAdded;
import com.armakuni.event_sourcing_example.events.OrderCreated;
import com.armakuni.event_sourcing_example.events.OrderPlaced;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Order {
    private ArrayList<OrderEvent> newEvents = new ArrayList<>();

    private OrderID id;
    private HashMap<ItemCode, Quantity> lines = new HashMap<>();
    private boolean placed = false;

    public static Order create(OrderID orderID) {
        var orderCreated = new OrderCreated(orderID);
        var events = Collections.singletonList(orderCreated);
        var order = new Order(new ArrayList<>(events));
        order.newEvents.add(orderCreated);
        return order;
    }

    public static Order fromEventStream(ArrayList<OrderEvent> events) {
        return new Order(events);
    }

    private Order(ArrayList<OrderEvent> events) {
        if (events.size() == 0) {
            throw new EmptyEventStream();
        }

        if (!(events.get(0) instanceof OrderCreated)) {
            throw new InvalidEvent("The first event must be OrderCreated");
        }

        for (OrderEvent event : events) {
            applyEvent(event);
        }
    }

    public List<OrderEvent> retrieveNewEvents() {
        return newEvents;
    }

    public void addItem(ItemCode item) {
        OrderEvent event = new ItemAdded(item);
        applyEvent(event);
        newEvents.add(event);
    }

    public void place() {
        OrderEvent event = new OrderPlaced();
        applyEvent(event);
        newEvents.add(event);
    }

    public void print(OrderPrinter printer) {
        if (!placed) {
            throw new OrderHasNotBeenPlaced(id);
        }

        var orderLines = lines.entrySet().stream()
                .map((entry) -> new OrderLine(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        printer.print(orderLines);
    }

    private void applyEvent(OrderEvent event) {
        if (event instanceof OrderCreated) {
            this.applyEvent((OrderCreated) event);
        } else if (event instanceof ItemAdded) {
            this.applyEvent((ItemAdded) event);
        } else if (event instanceof OrderPlaced) {
            this.applyEvent((OrderPlaced) event);
        }
    }

    private void applyEvent(OrderCreated event) {
        id = event.id;
    }

    private void applyEvent(ItemAdded event) {
        if (placed) {
            throw new OrderHasAlreadyBeenPlaced(id);
        }

        var quantity = lines.containsKey(event.itemCode)
                ? lines.get(event.itemCode).increment()
                : Quantity.of(1);

        lines.put(event.itemCode, quantity);
    }

    private void applyEvent(OrderPlaced event) {
        if (placed) {
            throw new OrderHasAlreadyBeenPlaced(id);
        }

        if (lines.isEmpty()) {
            throw new OrderHasNoItems(id);
        }

        placed = true;
    }
}
