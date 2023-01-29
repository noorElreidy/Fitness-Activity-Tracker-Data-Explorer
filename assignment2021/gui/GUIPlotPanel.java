package assignment2021.gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.JPanel;

import assignment2021.codeprovided.fitnesstracker.Participant;
import assignment2021.codeprovided.fitnesstracker.Tracker;
import assignment2021.codeprovided.fitnesstracker.measurements.Measurement;
import assignment2021.codeprovided.fitnesstracker.measurements.MeasurementType;
import assignment2021.codeprovided.gui.AbstractGUIPanel;
import assignment2021.codeprovided.gui.BasicGUIPlotPanel;

/**
 * A class to represent the GUI panel where the selected curves will be plot
 * using Java 2D. You are expected to write this class.
 *
 */

public class GUIPlotPanel extends BasicGUIPlotPanel {

	/**
	 * Generated Serial version UID
	 */
	private static final long serialVersionUID = -1482643158587603732L;
	private Map<Participant, Collection<Tracker>> toGraph ;
	private GUIPanel guiPanel = (GUIPanel)this.parentGUIPanel;
	int widthG = getBounds().width ;
	int heightG = getBounds().height;
	int spacingT = 5 ;
	int spacingP = 10;




	Collection<Participant> participants = guiPanel.getParticipants();
	final int marginTop = 40 ;
	final int marginSides = 80 ;
	JPanel graphPanel ;


	public GUIPlotPanel(AbstractGUIPanel parentGUIPanel) {
		super(parentGUIPanel);
		setBackground(Color.white);
		toGraph = new HashMap<>();
	}



	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D)g ;
		int wid = getBounds().width ;
		int hei= getBounds().height;
		int parCount = getParCount();
		int count = 0;

		g2D.translate(marginSides, hei-marginTop);
		drawAxis(g2D);

		if (!toGraph.isEmpty()) {
			if (guiPanel.getSelectedMeasurementType().equals(MeasurementType.DISTANCE)) {
				g2D.setColor(Color.CYAN);
				count = getTrackerCount();
				double highest = getHighest(MeasurementType.DISTANCE);

				int barWidth = (int)Math.floor(((wid - (count - 5 - parCount - 2)*5 - (parCount-1)*10)/count));
				for (Collection<Tracker> tr : toGraph.values()) {
					int i = 0;
					
					for (Tracker tracker : tr ) {
						int x = 5 ;
						System.out.println(tracker.getName());
						i ++ ;
						Collection<Measurement> meas = tracker.getMeasurementsMap().get(MeasurementType.DISTANCE);
						if (meas.size() > 0) {
							int barHeight = (int)Math.floor((meas.iterator().next().getValue().doubleValue()*hei - 2*marginTop)/highest);
							
							if ( i == tr.size())
								x = 10 ;

							g2D.drawRect(80+x,0,barWidth, -barHeight);
						}
						meas.clear();
					}

				}

			}
		}
	}

	public void clear() {
		Graphics g = getGraphics();
		toGraph.clear();
		g.clearRect(0,0, getWidth(), getHeight());
	}

	public double highestMeasurement(MeasurementType t, Participant p, Collection<Set<Tracker>> tr ) {
		double highest = 0 ;
		for (Collection<Tracker> trackers : tr) {
			for (Tracker trckrs : trackers) {
				Collection<Measurement> meas = 
				    trckrs.getMeasurementsForType(t);
				for (Measurement m : meas) {
					if (m.getValue().doubleValue() > highest)
						highest = m.getValue().doubleValue();
				}
			}
		}
		return highest;
	}

	public double lowestMeasurement(MeasurementType t, Participant p, Collection<Set<Tracker>> tr ) {
		double lowest = 9999999 ;
		for (Collection<Tracker> trackers : tr) {
			for (Tracker trckrs : trackers) {
				Collection<Measurement> meas = 
						trckrs.getMeasurementsForType(t);
				for (Measurement m : meas) {
					if (m.getValue().doubleValue() < lowest)
						lowest = m.getValue().doubleValue();
				}
			}
		}
		return lowest;
	}

	public int measurementsNumber(MeasurementType t, Participant p, Collection<Set<Tracker>> tr ) {
		int count = 0;
		for (Collection<Tracker> trackers : tr) {
			for (Tracker trckrs : trackers) {
				Collection<Measurement> meas = 
						trckrs.getMeasurementsForType(t);
				count += meas.size();

			}
		}
		return count;
	}


	public Participant getParticipantbyName(String name) {
		Participant p = 
				Participant.filterParticipantsByName(participants, name).iterator().next();
		return p;

	}


	public void graph(String name, String tracker) {
		//		Collection<Participant> participants = guiPanel.getParticipants();
		Participant p = getParticipantbyName(guiPanel.getSelectedParticipantName());
		Collection<Tracker> trackers = new ArrayList<Tracker>(Arrays.asList(new Tracker[] {}));
		if (tracker.toUpperCase().equals("ALL"))
			trackers = p.getAllTrackers() ;
		else 
			trackers = Tracker.filterTrackersByName(p.getAllTrackers(), tracker);



		if (toGraph.containsKey(p)) {
			if ( !toGraph.get(p).contains(trackers))
				toGraph.get(p).addAll(trackers);
		}
		else 
			toGraph.put(p, trackers);

		repaint();	
	}

	//	public void createGraphPanel() {
	//		JPanel graphPanel = new JPanel();
	//		JPanel xAxis = new JPanel();
	//		JPanel yAxis = new JPanel();
	//		graphPanel.setPreferredSize(new Dimension(widthG - 2*marginSides , heightG - 2*marginTop));
	//		xAxis.setPreferredSize(new Dimension(widthG - marginSides , marginTop));
	//		yAxis.setPreferredSize(new Dimension(marginSides, heightG - marginTop));
	//		
	//		graphPanel.setBackground(Color.BLUE);
	//		xAxis.setBackground(Color.CYAN);
	//		yAxis.setBackground(Color.MAGENTA);
	//		this.add(graphPanel, BorderLayout.CENTER);
	//		this.add(xAxis, BorderLayout.SOUTH);
	//		this.add(yAxis, BorderLayout.WEST);
	//		
	//	}


	public void drawAxis(Graphics2D g2D) {
		g2D.setStroke(new BasicStroke(3));
		g2D.setColor(Color.BLACK);

		g2D.drawLine(0, 0, 0, -getSize().height + 2*marginTop);
		g2D.drawLine(0, 0, getSize().width - 2*marginSides, 0);

		//		g2D.drawLine(marginSides, 0 , widthG- 2*marginSides, 0);
		//		g2D.drawLine(marginSides, -marginTop, marginSides, -heightG - 2*marginTop);
	}

	public int getTrackerCount() {
		int count = 0;
		for (Collection<Tracker> t : toGraph.values()) {
			count += t.size();
		}
		return count;
	}

	public int getParCount() {
		return toGraph.keySet().size();
	}

	public double getHighest(MeasurementType t) {
		double highest = 0 ;
		for (Collection<Tracker> tr : toGraph.values()) {
			for (Tracker tracker : tr ) {
				for ( Measurement meas : tracker.getMeasurementsForType(t)) {
					if (meas.getValue().doubleValue() > highest)
						highest = meas.getValue().doubleValue() ;
				}
			}
		}
		return highest;
	}







}
