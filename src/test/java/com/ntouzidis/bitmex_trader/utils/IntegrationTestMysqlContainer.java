package com.ntouzidis.bitmex_trader.utils;

import org.testcontainers.containers.MySQLContainer;

public class IntegrationTestMysqlContainer extends MySQLContainer<IntegrationTestMysqlContainer> {

    private static final String IMAGE_VERSION = "mysql:latest";
    private static IntegrationTestMysqlContainer container;
 
    private IntegrationTestMysqlContainer() {
        super(IMAGE_VERSION);
    }
 
    public static IntegrationTestMysqlContainer getInstance() {
        if (container == null) {
            container = new IntegrationTestMysqlContainer();
        }
        return container;
    }
 
    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }
 
    @Override
    public void stop() {
        super.stop();
    }
}