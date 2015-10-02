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

import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;

/**
 *
 * @author Nicola
 */
public class EventMarker extends Marker {
    private static int counter = 0;
    private Event event;
    private int number;
    private MarkerOptions markerOptions;
    
    public EventMarker(Event event, MarkerOptions markerOptions) {
        super(markerOptions);
        this.markerOptions = markerOptions;
        String icon = "";
        if (event.getMagnitude() <= 5)
            icon = "green.png";
        else if (event.getMagnitude() > 5 && event.getMagnitude() <= 8)
            icon = "yellow.png";
        else
            icon = "red.png";
        
        markerOptions.icon(icon);
        markerOptions.position(new LatLong(event.getLatitude(),event.getLongitude()));
        this.setOptions(markerOptions);
        this.event = event;
        this.number = counter++;
    } 
    
    public double getLatitude() {
        return event.getLatitude();
    }
    
    public double getLongitude() {
        return event.getLongitude();
    }
    
    public int getNumber() {
        return number;
    }
    
    public void setUnderline() {
        String evident = "blue.png";
        this.markerOptions.icon(evident);
        this.setOptions(markerOptions);
    }
    
    public void removeUnderline() {
        String icon = "";
        if (event.getMagnitude() <= 5)
            icon = "green.png";
        else if (event.getMagnitude() > 5 && event.getMagnitude() <= 8)
            icon = "yellow.png";
        else
            icon = "red.png";
        
        this.markerOptions.icon(icon);
        this.setOptions(markerOptions);
    }
}
