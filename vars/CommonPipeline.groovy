#!/usr/bin/env groovy
import steps.Checkout

def call(String repo = 'Reflections') {
    Checkout c = new Checkout();
    c.checkout(repo);
}