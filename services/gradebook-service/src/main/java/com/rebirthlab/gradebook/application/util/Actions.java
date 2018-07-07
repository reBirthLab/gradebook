package com.rebirthlab.gradebook.application.util;

/**
 * Created by Anastasiy
 */
public enum Actions {
    CREATE,
    UPDATE;

    @Override
    public String toString() {
        return this.toString().toLowerCase();
    }
}