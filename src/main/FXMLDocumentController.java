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
package main;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.InfoWindowOptions;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import events.Event;
import events.EventMarker;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import netscape.javascript.JSObject;
import utils.Utility;
import xml.XMLLoader;

/**
 *
 * @author Nicola
 */
public class FXMLDocumentController implements Initializable, MapComponentInitializedListener {
    
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label headerLbl;
    @FXML
    private GoogleMapView mapView;
    @FXML
    private TableView<Event> tableView;
    @FXML
    private TableColumn<Event,String> locCol;
    @FXML
    private TableColumn<Event,String> timeCol;
    @FXML
    private TableColumn<Event,Double> latCol;
    @FXML
    private TableColumn<Event,Double> lonCol;
    @FXML
    private TableColumn<Event,Double> magCol;
    @FXML
    private TableColumn<Event,Double> depthCol;
    
    private GoogleMap map;
    private ObservableList<Event> events;
    private ArrayList<EventMarker> markers;
    @FXML
    private Label subHeaderLbl;
    @FXML
    private Label lblMap;
    @FXML
    private Label lblTable;
    @FXML
    private Label lblLegend;
    @FXML
    private Label lblBlue;
    @FXML
    private Label lblGreen;
    @FXML
    private Label lblYellow;
    @FXML
    private Label lblRed;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapView.addMapInializedListener(this);
       
        XMLLoader loader = new XMLLoader();
        loader.getXmlFile("http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_day.xml");
        events = FXCollections.observableArrayList(loader.parseDocument());
        markers = new ArrayList<>();
        subHeaderLbl.setText(subHeaderLbl.getText() + Utility.getTime());
        
        locCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        latCol.setCellValueFactory(new PropertyValueFactory<>("latitude"));
        lonCol.setCellValueFactory(new PropertyValueFactory<>("longitude"));
        magCol.setCellValueFactory(new PropertyValueFactory<>("magnitude"));
        depthCol.setCellValueFactory(new PropertyValueFactory<>("depth"));
        tableView.setItems(events);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                int oldSelected = oldValue.intValue();
                int newSelected = newValue.intValue();
                
                EventMarker selectedMarker = markers.get(newSelected);
                selectedMarker.setUnderline();
                
                if (oldSelected != -1 && oldSelected != newSelected) {
                    EventMarker oldMarker = markers.get(oldSelected);
                    oldMarker.removeUnderline();
                }
                
                mapView.setCenter(selectedMarker.getLatitude(), selectedMarker.getLongitude());
            }
        }
        );
    }    
        
    @Override
    public void mapInitialized() {
        MapOptions mapOptions = new MapOptions();
        mapOptions.center(new LatLong(0,0))
                .overviewMapControl(true)
                .mapType(MapTypeIdEnum.HYBRID)
                .panControl(true)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(true)
                .zoom(1);
                   
        map = mapView.createMap(mapOptions);
        
        events.stream().forEach((e) -> {
            MarkerOptions opt = new MarkerOptions();           
            EventMarker em = new EventMarker(e,opt);
            markers.add(em);
            map.addMarker(em);
            map.addUIEventHandler(em, UIEventType.click, (JSObject jso) -> {
                InfoWindowOptions infoOpt = new InfoWindowOptions();
                infoOpt.content(String.format("<p>%s</p><p>Magnitude: %s</p>", e.getLocation(),e.getMagnitude()));
                InfoWindow wind = new InfoWindow(infoOpt);
                wind.open(map,em);
                tableView.getSelectionModel().clearAndSelect(em.getNumber());
                tableView.scrollTo(em.getNumber());
                tableView.requestFocus();
            });
        });  
    }   
}
    

