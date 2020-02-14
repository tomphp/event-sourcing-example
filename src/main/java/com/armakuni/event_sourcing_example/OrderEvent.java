package com.armakuni.event_sourcing_example;

public abstract class OrderEvent {
    public abstract void apply(OrderStateProjection projection);
}
