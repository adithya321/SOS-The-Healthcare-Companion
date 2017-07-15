/*
 * SOS
 * Copyright (C) 2016  zDuo (Adithya J, Vazbloke)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.zduo.sos.models;

import java.util.Arrays;

public class Reminder {

    // Reminder types
    public static final int ACTIVE = 1;
    public static final int INACTIVE = 2;

    // Repetition types
    public static final int DAILY = 0;
    public static final int WEEKLY = 1;
    public static final int MONTHLY = 2;
    public static final int SPECIFIC_DAYS = 3;
    public static final int ADVANCED = 4;

    private int id;
    private String title;
    private String content;
    private String dateAndTime;
    private int repeatType;
    private String foreverState;
    private int numberToShow;
    private int numberShown;
    private double dosage;
    private double quantity;
    private String icon;
    private String colour;
    private boolean[] daysOfWeek;
    private int interval;

    public int getId() {
        return id;
    }

    public Reminder setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Reminder setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Reminder setContent(String content) {
        this.content = content;
        return this;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public Reminder setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
        return this;
    }

    public String getDate() {
        return dateAndTime.substring(0, 8);
    }

    public int getRepeatType() {
        return repeatType;
    }

    public Reminder setRepeatType(int repeatType) {
        this.repeatType = repeatType;
        return this;
    }

    public String getForeverState() {
        return foreverState;
    }

    public Reminder setForeverState(String foreverState) {
        this.foreverState = foreverState;
        return this;
    }

    public int getNumberToShow() {
        return numberToShow;
    }

    public Reminder setNumberToShow(int numberToShow) {
        this.numberToShow = numberToShow;
        return this;
    }

    public int getNumberShown() {
        return numberShown;
    }

    public Reminder setNumberShown(int numberShown) {
        this.numberShown = numberShown;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public Reminder setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public String getColour() {
        return colour;
    }

    public Reminder setColour(String colour) {
        this.colour = colour;
        return this;
    }

    public boolean[] getDaysOfWeek() {
        return daysOfWeek;
    }

    public Reminder setDaysOfWeek(boolean[] daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
        return this;
    }

    public int getInterval() {
        return interval;
    }

    public Reminder setInterval(int interval) {
        this.interval = interval;
        return this;
    }

    public double getDosage() {
        return dosage;
    }

    public Reminder setDosage(double dosage) {
        this.dosage = dosage;
        return this;
    }

    public double getQuantity() {
        return quantity;
    }

    public Reminder setQuantity(double quantity) {
        this.quantity = quantity;
        return this;
    }

    @Override
    public String toString() {
        return "Reminder{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", dateAndTime='" + dateAndTime + '\'' +
                ", repeatType=" + repeatType +
                ", foreverState='" + foreverState + '\'' +
                ", numberToShow=" + numberToShow +
                ", numberShown=" + numberShown +
                ", dosage=" + dosage +
                ", quantity=" + quantity +
                ", icon='" + icon + '\'' +
                ", colour='" + colour + '\'' +
                ", daysOfWeek=" + Arrays.toString(daysOfWeek) +
                ", interval=" + interval +
                '}';
    }
}