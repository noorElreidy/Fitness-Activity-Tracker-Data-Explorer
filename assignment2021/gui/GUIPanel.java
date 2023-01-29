package assignment2021.gui;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import assignment2021.codeprovided.fitnesstracker.Participant;
import assignment2021.codeprovided.fitnesstracker.Tracker;
import assignment2021.codeprovided.fitnesstracker.measurements.Measurement;
import assignment2021.codeprovided.fitnesstracker.measurements.MeasurementType;
import assignment2021.codeprovided.gui.AbstractGUIPanel;

public class GUIPanel extends AbstractGUIPanel {

	private static final long serialVersionUID = -1257936627636425453L;

	final int MARGIN_BUTTOM = 40 ;
	final int MARGIN_SIDE = 20 ;
	
	DecimalFormat df = new DecimalFormat("0.00");
	public GUIPanel(Collection<Participant> participants) {
		super(participants);
		visualisedCurvesDetailsTextArea.setText("No curves added to display");
		
		int females = 0 ; 
		int largest = 0 ; 
		int youngest = 100 ;
		int measurements = 0 ; 
		for (Participant p : participants) {
			measurements += p.getAllMeasurements().size();
			if ( p.getAge() > largest)
				largest = p.getAge();
			if ( p.getAge() < youngest )
				youngest = p.getAge() ;
			if (p.getGender().toUpperCase().equals("F"))
				females ++ ;
		}
		
		String participantSummary = "There are " + participants.size() 
		    + " participants in this dataset. \r\n" + "There are "
			+ females + " females and " + (participants.size()- females) 
			+ " males.\r\nThe oldest participant is " + largest + " years "
			+ "old , while the youngest is " + youngest + " years old.\r\n"
		    + "There are " + measurements + " total measurements in this dataset." ; 
		datasetSummaryTextArea.setText(participantSummary);
		datasetSummaryTextArea.setEditable(false);
		System.out.println(participantSummary);
	}
	
	
	@Override
	protected void loadParticipantsNames() {
		int i = 0;
		participantsArray = new String[participants.size()];

		if (participants.contains(null)) {
			participantsArray = new String[1];
			participantsArray[0] = "No participants" ; 
			return;
		}
		i = 0;
		for (Participant p : participants) {
			participantsArray[i] = p.getName();
			i++;
		}
		System.out.println(Arrays.toString(participantsArray));
	}
	
	protected String getCurvesDetailsTextArea() {
		return visualisedCurvesDetailsTextArea.getText() ; 
	}
	
	@Override
	protected void loadTrackers() {

		if (participants.contains(null)) {
			trackersArray = new String[0];
			trackersArray[0] = "No trackers";
			return;
		}

		Collection<String> trackerNames = ((Participant) participants.toArray()[0]).getAllTrackerNames();
		trackersArray = new String[trackerNames.size() + 1];
		//adds an all option in combolist
		trackersArray[0] = "all";
		int i = 1;
		for (String trackerName : trackerNames) {
			trackersArray[i] = trackerName;
			i++;
		}
		System.out.println(Arrays.toString(trackersArray));
	}
	
	//allows getting participant in GUIPlotPanel
	public Collection<Participant> getParticipants(){
		return this.participants ; 
	}


	/**
	 * adds action listeners to buttons
	 */
	@Override
	public void addListeners() {

		removeAllCurvesButton.addActionListener(e -> {
			System.out.println("Removing stuff");
			visualisedCurvesDetailsTextArea.setText("No curves added to dispaly");
		});
		 
		addCurvesButton.addActionListener(e ->{
			
    		visualisedCurvesPanel.graph(getSelectedParticipantName(),getSelectedTrackersNames());
			visualisedCurvesPanel.repaint();
			
			//clears visualisedCurvesDetailsTextArea from no curves to display text
			String detailsTextArea = visualisedCurvesDetailsTextArea.getText();
			if (detailsTextArea.contains("No curves added to display")){
				visualisedCurvesDetailsTextArea.setText("");
			}
			
			String name = getSelectedParticipantName();
			Participant p = 
				Participant.filterParticipantsByName(participants, name ).iterator().next();
			
			
			String out = "" ;
			Collection<Tracker> trackers = new ArrayList<Tracker>(Arrays.asList(new Tracker[] {}));
			trackers.clear();
			//only adds user details if not already in the curve details panel
			out = "Data for participant " + name + ", " + p.getGender() + ", " + p.getAge() + "\r\n";
		    if (!visualisedCurvesDetailsTextArea.getText().contains(out)) {
    	        visualisedCurvesDetailsTextArea.append(out);
		    }
		    //adds displaying all trackers if all is selected
			if (getSelectedTrackersNames().toUpperCase().equals("ALL")) {
			     trackers.addAll(p.getAllTrackers()); 
			     out = "Displaying all trackers for particpant " + name 
			         + " for " + getSelectedMeasurementType().toString() + " measurements \r\n";
			     if (!visualisedCurvesDetailsTextArea.getText().contains(out)) {
			    	 visualisedCurvesDetailsTextArea.append(out);
			     }
			    	 
			}
			else {
				trackers.add(p.getTracker(getSelectedTrackersNames()));
			}
			
			
			//loops through tracker in combolist
			if (trackers.size() != 0) {
	            for (Tracker t : trackers) {
                	out = "Participant " + name + "'s tracker " + t.getName() + " " +
                		getSelectedMeasurementType().toString() + " number of measurements : "; 
                	//only adds to visualisedCurvesDetailsTextArea if data not already in it
                	if (!visualisedCurvesDetailsTextArea.getText().contains(out)) {
			    	    visualisedCurvesDetailsTextArea.append(out);
			    	    System.out.println(t.getMeasurementsForType(getSelectedMeasurementType()).size());
		                if (t.getMeasurementsForType(getSelectedMeasurementType()).size() != 0) {
		                	Collection<Measurement> measureOneTracker =
		                	    t.getMeasurementsForType(getSelectedMeasurementType()); 
		                	 visualisedCurvesDetailsTextArea.append(measureOneTracker.size() + "\r\n");
		                	measureOneTracker.clear() ; 
		                }
		                else 
					       visualisedCurvesDetailsTextArea.append("0 \r\n");
   			        }
	            }
			}
			else {
				out = name + " doesn't have selected trackers \r\n" ;
			    if (!visualisedCurvesDetailsTextArea.getText().contains(out))
	    	        visualisedCurvesDetailsTextArea.append(out);
			}
		});
		
		cbIncrements.addItemListener(e ->{
			visualisedCurvesPanel.repaint();
		});
		
		cbAverageValue.addItemListener(e ->{
			visualisedCurvesPanel.repaint();
		});
		
		
		cbMaxMinValues.addItemListener(e ->{
			visualisedCurvesPanel.repaint();
		});
		
		comboListMeasurementType.addActionListener(e ->{
			
    		visualisedCurvesPanel.graph(getSelectedParticipantName(), 
	                getSelectedTrackersNames());
			visualisedCurvesPanel.repaint();
			
			visualisedCurvesDetailsTextArea.setText("");
			String name = getSelectedParticipantName();
			Participant p = 
				Participant.filterParticipantsByName(participants, name ).iterator().next();
			
			
			String out = "" ;
			Collection<Tracker> trackers = new ArrayList<Tracker>(Arrays.asList(new Tracker[] {}));
			trackers.clear();
			out = "Data for participant " + name + ", " + p.getGender() + ", " + p.getAge() + "\r\n";
		    if (!visualisedCurvesDetailsTextArea.getText().contains(out)) {
		    	visualisedCurvesDetailsTextArea.append(out);
		    }
    	        
			if (getSelectedTrackersNames().toUpperCase().equals("ALL")) {
			     trackers.addAll(p.getAllTrackers()); 
			     out = "Displaying all trackers for particpant " + name 
			         + " for " + getSelectedMeasurementType().toString() + " measurements \r\n";
			     if (!visualisedCurvesDetailsTextArea.getText().contains(out))
			    	 visualisedCurvesDetailsTextArea.append(out);
			    	 
			}
			else {
				trackers.add(p.getTracker(getSelectedTrackersNames()));
			}
			
			
			//loops through selected trackers
			if (trackers.size() != 0) {
	            for (Tracker t : trackers) {
                	out = "Participant " + name + "'s tracker " + t.getName() + " " +
                		getSelectedMeasurementType().toString() + " number of measurements : "; 
                	//only adds to visualisedCurvesDetailsTextArea if data not already in it
                	if (!visualisedCurvesDetailsTextArea.getText().contains(out)) {

			    	    visualisedCurvesDetailsTextArea.append(out);
			    	    System.out.println(t.getMeasurementsForType(getSelectedMeasurementType()).size());
		                if (t.getMeasurementsForType(getSelectedMeasurementType()).size() != 0) {
		                	Collection<Measurement> measureOneTracker =
		                	    t.getMeasurementsForType(getSelectedMeasurementType()); 
		                	 visualisedCurvesDetailsTextArea.append(measureOneTracker.size() + "\r\n");
		                	measureOneTracker.clear() ; 
		                }
		                else 
					       visualisedCurvesDetailsTextArea.append("0 \r\n");
   			        }
	            }
			}
			else {
				out = name + " doesn't have selected trackers \r\n" ;
			    if (!visualisedCurvesDetailsTextArea.getText().contains(out))
	    	        visualisedCurvesDetailsTextArea.append(out);
			}
		});		
	}

	/**
	 * @return the selected participant's name as a string
	 */
	@Override
	public String getSelectedParticipantName() {
		String value = comboListParticipants.getSelectedItem().toString() ; 
		return value;
	}

	/**
	 * @return the selected tracker's name as a string
	 */
	@Override
	public String getSelectedTrackersNames() {
		String value = comboListTrackers.getSelectedItem().toString() ; 
		return value;
	}

	/**
	 * @return the selected measurement type as a MeasurementType
	 */
	@Override
	public MeasurementType getSelectedMeasurementType() {
		String value = comboListMeasurementType.getSelectedItem().toString();
		return MeasurementType.fromMeasurementName(value) ;
	}

	/**
	 * @return if show average is selected 
	 */
	@Override
	public boolean isShowAverageSelected() {
		return cbAverageValue.isSelected();
	}

	/**
	 * @returns if showMinMax is selected
	 */
	@Override
	public boolean isShowMinMaxSelected() {
		return cbMaxMinValues.isSelected();
	}

	/**
	 * @returns if show increments is selected 
	 */
	@Override
	public boolean isShowIncrementsSelected() {
		return cbIncrements.isSelected();
	}

}
