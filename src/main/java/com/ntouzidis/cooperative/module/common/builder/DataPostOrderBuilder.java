package com.ntouzidis.cooperative.module.common.builder;

import com.ntouzidis.cooperative.module.common.enumeration.OrderType;
import com.ntouzidis.cooperative.module.common.enumeration.Side;
import com.ntouzidis.cooperative.module.common.enumeration.Symbol;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class DataPostOrderBuilder {

    private Symbol symbol;
    private String clOrdID;
    private Side side;
    private OrderType orderType;
    private String orderQty;
    private Integer displayQty;
    private String price;
    private String execInst;
    private String stopPrice;
    private String text = "BITMEXCALLBOT";

    public DataPostOrderBuilder() {
    }

    public DataPostOrderBuilder withClOrdId(String clOrdID) {
        this.clOrdID = clOrdID;
        return this;
    }

    public DataPostOrderBuilder withSymbol(Symbol symbol) {
        this.symbol = symbol;
        return this;
    }

    public DataPostOrderBuilder withSide(Side side) {
        this.side = side;
        return this;
    }

    public DataPostOrderBuilder withOrderType(OrderType orderType) {
        this.orderType = orderType;
        return this;
    }

    public DataPostOrderBuilder withOrderQty(String orderQty) {
        this.orderQty = orderQty;
        return this;
    }

    public DataPostOrderBuilder withPrice(String price) {
        this.price = price;
        return this;
    }

    public DataPostOrderBuilder withExecInst(String execInst) {
        this.execInst = execInst;
        return this;
    }

    public DataPostOrderBuilder withStopPrice(String stopPrice) {
        this.stopPrice = stopPrice;
        return this;
    }

    public DataPostOrderBuilder withText(String text) {
        this.text = text;
        return this;
    }

    public DataPostOrderBuilder withDisplayQty(Integer displayQty) {
        this.displayQty = displayQty;
        return this;
    }

    public String get() {
        AtomicReference<String> data = new AtomicReference<>();
        data.set("");
        Optional.ofNullable(symbol).ifPresent(i -> data.set(data.get() + "symbol=" + i.name()));
        Optional.ofNullable(clOrdID).ifPresent(i -> data.set(data.get() + "&clOrdID=" + i));
        Optional.ofNullable(side).ifPresent(i -> data.set(data.get() + "&side=" + i.getValue()));
        Optional.ofNullable(orderType).ifPresent(i -> data.set(data.get() + "&ordType=" + i.getValue()));
        Optional.ofNullable(orderQty).ifPresent(i -> data.set(data.get() + "&orderQty=" + i));
        Optional.ofNullable(displayQty).ifPresent(i -> data.set(data.get() + "&displayQty=" + i));
        Optional.ofNullable(price).ifPresent(i -> data.set(data.get() + "&price=" + i));
        Optional.ofNullable(execInst).ifPresent(i -> data.set(data.get() + "&execInst=" + i));
        Optional.ofNullable(stopPrice).ifPresent(i -> data.set(data.get() + "&stopPrice=" + i));
        Optional.ofNullable(text).ifPresent(i -> data.set(data.get() + "&text=" + i));
        return data.get();
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public String getClOrdID() {
        return clOrdID;
    }

    public Side getSide() {
        return side;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public String getOrderQty() {
        return orderQty;
    }

    public String getPrice() {
        return price;
    }

    public String getExecInst() {
        return execInst;
    }

    public String getStopPrice() {
        return stopPrice;
    }

    public String getText() {
        return text;
    }
}
