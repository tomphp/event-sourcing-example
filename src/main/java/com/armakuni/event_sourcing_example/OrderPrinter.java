package com.armakuni.event_sourcing_example;

import java.util.List;

public interface OrderPrinter {
    void print(List<OrderLine> items);
}
