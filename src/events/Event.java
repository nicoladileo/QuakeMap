/*
 * Copyright (C) 2015 Nicola Dileo
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package events;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Nicola
 */
public class Event {
    private StringProperty location;
    private StringProperty time;
    private DoubleProperty longitude;
    private DoubleProperty latitude;
    private DoubleProperty depth;
    private DoubleProperty magnitude;
    private StringProperty magType;
    
    public Event() {
        
    }
    
    public Event(String text, String time, double longitude, double latitude, 
                double depth, double magnitude, String magType) {
        this.location = new SimpleStringProperty(text);
        this.time = new SimpleStringProperty(time);
        this.longitude = new SimpleDoubleProperty(longitude);
        this.latitude = new SimpleDoubleProperty(latitude);
        this.depth = new SimpleDoubleProperty(depth);
        this.magnitude = new SimpleDoubleProperty(magnitude);
        this.magType = new SimpleStringProperty(magType);
    }
    
    public String getLocation() {
        return location.get();
    }

    public String getTime() {
        return time.get();
    }

    public double getLongitude() {
        return longitude.get();
    }

    public double getLatitude() {
        return latitude.get();
    }

    public double getDepth() {
        return depth.get();
    }

    public double getMagnitude() {
        return magnitude.get();
    }

    public String getMagType() {
        return magType.get();
    }

    public void setText(String text) {
        this.location.set(text);
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public void setLongitude(double longitude) {
        this.longitude.set(longitude);
    }

    public void setLatitude(double latitude) {
        this.latitude.set(latitude);
    }

    public void setDepth(double depth) {
        this.depth.set(depth);
    }

    public void setMagnitude(double magnitude) {
        this.magnitude.set(magnitude);
    }

    public void setMagType(String magType) {
        this.magType.set(magType);
    }

    @Override
    public String toString() {
        return "Event{" + "text=" + location + ", time=" + time + ", longitude=" + longitude + ", latitude=" +
                latitude + ", depth=" + depth + ", magnitude=" + magnitude + ", magType=" + magType + '}';
    }    
}
