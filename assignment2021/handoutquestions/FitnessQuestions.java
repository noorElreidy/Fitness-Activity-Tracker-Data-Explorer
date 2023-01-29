package assignment2021.handoutquestions;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.*;
import java.text.DecimalFormat;

//import org.junit.jupiter.api.Assertions;

import assignment2021.codeprovided.fitnesstracker.Participant;
import assignment2021.codeprovided.fitnesstracker.Tracker;
import assignment2021.codeprovided.fitnesstracker.measurements.Measurement;
import assignment2021.codeprovided.fitnesstracker.measurements.MeasurementType;
import assignment2021.codeprovided.handoutquestions.AbstractFitnessQuestions;
import assignment2021.dataloading.DataLoader;


public class FitnessQuestions extends AbstractFitnessQuestions {

	public FitnessQuestions(Collection<Participant> participants) {
		super(participants);
	}
	
	/**
	 * @return  the answers to the assignment brief's questions
	 */
	@Override
	public String toString() {
		 String output = "";
		 DecimalFormat df = new DecimalFormat("0.00");
		 int total = getTotalParticipants();
		 String outOf = " out of " + total ; 
		 
		 //Q1
		 output += "Q1. Total number of participants : " + total + "\r\n" + "\r\n";
		 
		 //Q2
		 output += "Q2. Number of participants with heart rate measurements : "
		     + getParticipantsNumberWithHRM() + outOf + "\r\n" + "\r\n";
		 
		 //Q3
		 output += "Q3. Number of participants with step measurements : "
		     + getParticipantsNumberWithStepsM() + outOf + "\r\n"  + "\r\n";
		 
		 //Q4
		 output += "Q4. Number of participants with distance measurements : "
		     + getParticipantsNumberWithDistanceM() + outOf + "\r\n" + "\r\n";
		 
		 //Q5
		 output += "Q5. Number of participants with energy expenditure measurem"
		     + "ents : " + getParticipantsNumberWithEEM() + outOf + "\r\n" + "\r\n" ;
		 
		 //Q6
		 output += "Q6. Total number of heart rate measurements"
		     + " : " + getTotalHRMCount() + "\r\n"  + "\r\n";
		 
		 //Q7
		 output += "Q7. Total number of step measurements"
		     + " : " + getTotalStepsCount() + "\r\n"  + "\r\n" ;
		 
		 //Q8
		 output += "Q8. Total number of distance measurements"
		     + " : " + getTotalDistanceCount() + "\r\n"  + "\r\n";
		 
		 //Q9
		 output += "Q9. Total number of energy expenditure measurements"
		   + " : " + getTotalEECount() + "\r\n" + "\r\n" ;
		 
		 //Q.10 starts
		 output += "Q10. "; 
		 List<String> heartRateNames = getTrackerNamesbyMtType(MeasurementType.HEART_RATE);
		 List<Integer> heartRatePerFT = getHRMCountPerFT();
		 for (int i = 0 ; i < heartRateNames.size() ; i ++) {
			 output += "Total count of heart rate measurements for " + 
		        heartRateNames.get(i) + " : " + heartRatePerFT.get(i) + "\r\n";
			 output += "     ";
		 }
		 //removes extra spaces added by loop
		 output = output.substring(0, output.length()-5) + "\r\n"; 
		 //Q.10 Ends 
		 
		 
		 //Q11
		 output += "Q11. Total count of energy expenditure measurements for "
		   + "FT1:" + getEEMCountForFT1() + "\r\n"  + "\r\n"; 
		  
		 //Q.12 starts
		 List<Integer> steps234 = getStepsMCountForFT234();
		 output += "Q12. Total count of steps measurements for FT2: "  
		   + steps234.get(0) + "\r\n"  + "     "; 
		 output += "Total count of steps measurements for FT3: "  
		   + steps234.get(1) + "\r\n"  + "     "; 
		 output += "Total count of steps measurements for FT4: "  
				   + steps234.get(2) + "\r\n"  + "\r\n"; 
		 //Q.12 Ends 
		 
		 output += "Q13. Total count of distance measurements for FT5: " + 
		   getDistanceMCountForFT5() + "\r\n"  + "\r\n";
		 
		 //Q.14 starts
		 String names = "";
		 for (String n : getHighestNumberOfStepsParticipants() ) {
			 names += n + " , " ; 
		 }
		 names = names.substring(0,names.length()-2);
		 output += "Q14. list of participants with the highest single measurem"
		 		+ "ent of steps ( " + getHighestReadingbyMeasurementType(MeasurementType.STEPS)  
		 		+ " ): " + names  + "\r\n" + "\r\n" ;
		 //Q14 ends 
		 
		 
		 //Q.15 starts 
		 String names2 = "";
		 for (String n : getHighestWalkedDistanceParticipants()) {
			 names2 += n + " , " ; 
		 }
		 names2 = names2.substring(0,names2.length()-2);
		 output += "Q15. list of participants with the highest single measurem"
		     + "ent of distance ( " + getHighestReadingbyMeasurementType(MeasurementType.DISTANCE)  
			 + " ): " + names2  + "\r\n" + "\r\n";
		 //Q.15 Ends 
		 
		 
		 //Q.16
		 output += "Q16. Global average number of steps for FT1 : "
				 +df.format(getGlobalAverageStepsForFT1()) + "\r\n" + "\r\n" ;
		 	
		 
		 //Q17 starts
	     String q17 = "";
	     String[] trckr = {"FT1"};
	     for (String s :getAvgStepsAboveGlobalParticipantsForFT1() ) {
	    	 q17 +=   "     " + s + " : " + 
	             df.format(getParticipantAvgByName(s, MeasurementType.STEPS, trckr)) + "\r\n" ; 
	     }
		 output += "Q17. List of participants with an average number of steps above the"
		     + " global average number of steps for FT1 : " + "\r\n" + q17 + "\r\n" ; 
		 //Q17 ends
		 
		 
		//Q18 starts
	     String q18 = "";
	     for (String s :getAvgStepsBelowGlobalParticipantsForFT1() ) {
	    	 q18 +=   "     " + s +  " : " +
	            df.format(getParticipantAvgByName(s, MeasurementType.STEPS, trckr)) +  "\r\n" ; 
	     }
		 output += "Q18. List of participants with an average number of steps below the"
		     + " global average number of steps for FT1 : " + "\r\n" + q18 + "\r\n"; 
		 //Q18 ends
		 
		 
		 //Q19
		 output += "Q.19 Global average energy expenditure for FT2 and FT3 : " + 
		     df.format(getGlobalAverageEEForFT2FT3()) + "\r\n" + "\r\n";
		 
		 
		 //Q20 starts
		 String q20 = "";
		 String[] trckr2 = {"FT2", "FT3"};
	     for (String s : getAvgEEAboveGlobalParticipantsForFT2FT3() ) {
	    	 q20 +=   "     " + s +  " : " + 
	             df.format(getParticipantAvgByName(s, MeasurementType.ENERGY_EXPENDITURE, trckr2)) + "\r\n"; 
	     }
		 output += "Q20. List of participants with an average Energy Expenditure above the"
		 		+ " global average for FT2 and FT3 : " + "\r\n" + q20 + "\r\n";
		 //Q20 ends 
		 
		 
		//Q21 starts
		 String q21 = "";
	     for (String s : getAvgEEBelowGlobalParticipantsForFT2FT3() ) {
	    	 q21 +=   "     " + s +   " : " +
	             df.format(getParticipantAvgByName(s, MeasurementType.ENERGY_EXPENDITURE, trckr2)) + "\r\n" ; 
	     }
		 output += "Q21. List of participants with an average Energy Expenditure above the"
		     + " global average for FT2 and FT3 : " + "\r\n" + q21 + "\r\n";
		 //Q21 ends 
		 
		 //Q22
		 output += "Q22. Global Average Heart Rate measurement : " + df.format(getGlobalAverageHR())
		     + "\r\n" + "\r\n";
		 
		//Q23 starts
		 String q23 = "";
		 String[] trckr3 = {};
	     for (String s : getAvgHRAboveGlobalParticipants() ) {
	    	 q23 +=   "     " + s + " : " + 
	             df.format(getParticipantAvgByName(s, MeasurementType.HEART_RATE, trckr3)) + "\r\n"; 
	     }
		 output += "Q23. List of participants with an average Heart rate measurement "
		     + "above the global average : " + "\r\n" + q23 +"\r\n" ;
		 //Q23 ends 
		 
		 
		//Q24 starts
		 String q24 = "";
	     for (String s : getAvgHRBelowGlobalParticipants() ) {
	    	 q24 +=   "     " + s + " : " +
	    			 df.format(getParticipantAvgByName(s, MeasurementType.HEART_RATE, trckr3)) + "\r\n" ; 
	     }
		 output += "Q24. List of participants with an average Heart rate measurement "
		 		+ "below the global average : " + "\r\n" + q24 + "\r\n";
		 //Q24 ends 
		 
		 return output;
	}

	/**
	 * @return  total number of participants
	 */
	@Override
	public int getTotalParticipants() {
		return getParticipants().size() ;
	}

	/**
	 * @return  number of participants with heart rate measurements
	 */
	@Override
	public int getParticipantsNumberWithHRM() {
		int count = 0 ;
		MeasurementType t = MeasurementType.HEART_RATE ; 
		
		//loops over participants 
		for (Participant p : getParticipants()) {
			//loops over participant's trackers
			for (Tracker tr : p.getAllTrackers()) {
				if (!tr.getMeasurementsForType(t).isEmpty()) {
				    count ++ ;
				    break ;
				}
			}
		}
		return count;
	}

	/**
	 * @returns number of participants with step measurements
	 */
	@Override
	public int getParticipantsNumberWithStepsM() {
        int count = 0 ;
		MeasurementType t = MeasurementType.STEPS;
		
		//loops over participants 
		for (Participant p : getParticipants()) {
			//loops over participant's trackers
			for (Tracker tr : p.getAllTrackers()) {
				if (!tr.getMeasurementsForType(t).isEmpty()) {
				    count ++ ;
				    break ;
				}
			}
		}
		return count;
	}

	/**
	 * @return number of participants with distance measurements
	 */
	@Override
	public int getParticipantsNumberWithDistanceM() {
        int count = 0 ;
		MeasurementType t = MeasurementType.DISTANCE;
		
		//loops over participants 
		for (Participant p : getParticipants()) {
			//loops over participant's trackers
			for (Tracker tr : p.getAllTrackers()) {
				if (!tr.getMeasurementsForType(t).isEmpty()) {
				    count ++ ;
				    break ;
				}
			}
		}
		
		return count;
	}

	/**
	 * @returns number of participants with Energy expenditure measurements 
	 */
	@Override
	public int getParticipantsNumberWithEEM() {
        int count = 0 ;
		MeasurementType t = MeasurementType.ENERGY_EXPENDITURE;
		
		//loops over participants 
		for (Participant p : getParticipants()) {
			//loops over participant's trackers
			for (Tracker tr : p.getAllTrackers()) {;
				if (!tr.getMeasurementsForType(t).isEmpty()) {
				    count ++ ;
				    break ;
				}
			}
		}
		return count;
	}

	/**
	 * @returns total number of heart rate measurements in data set
	 */
	@Override
	public int getTotalHRMCount() {
        int count = 0 ;
		MeasurementType t = MeasurementType.HEART_RATE;
		
		//loops over participants 
		for (Participant p : getParticipants()) {
			//loops over participant's trackers
			for (Tracker tr : p.getAllTrackers()) {
				if (!tr.getMeasurementsForType(t).isEmpty()) 
					count += tr.getMeasurementsForType(t).size();
			}
		}
		return count;
	}

	/**
	 * @returns total number of step measurements in whole data set
	 */
	@Override
	public int getTotalStepsCount() {
        int count = 0 ;
		MeasurementType t = MeasurementType.STEPS;
		
		//loops over participants 
		for (Participant p : getParticipants()) {
			//loops over participant's trackers
			for (Tracker tr : p.getAllTrackers()) {
				if (!tr.getMeasurementsForType(t).isEmpty())
					count += tr.getMeasurementsForType(t).size();
			}
		}
		return count;
	}

	/**
	 * @returns total number of distance measurements in whole data set
	 */
	@Override
	public int getTotalDistanceCount() {
        int count = 0 ;
		MeasurementType t = MeasurementType.DISTANCE;
		
		//loops over participants 
		for (Participant p : getParticipants()) {
			//loops over participant's trackers
			for (Tracker tr : p.getAllTrackers()) {
				if (!tr.getMeasurementsForType(t).isEmpty())
					count += tr.getMeasurementsForType(t).size();
			}
		}
		return count;
	}

	/**
	 * @return total number of energy expenditure
	 *  measurements in whole data set
	 */
	@Override
	public int getTotalEECount() {
        int count = 0 ;
		MeasurementType t = MeasurementType.ENERGY_EXPENDITURE;
		
		//loops over participants 
		for (Participant p : getParticipants()) {
			//loops over participant's trackers
			for (Tracker tr : p.getAllTrackers()) {
				if (!tr.getMeasurementsForType(t).isEmpty()) {
				    count += tr.getMeasurementsForType(t).size();
				}
			}
		}
		return count;
	}
	
	/**
	 * @param t of type measurementType
	 * @return an ArrayList of type string of tracker names 
	 * that have the MeasurementTYpe passed in as a parameter
	 */
	public ArrayList<String> getTrackerNamesbyMtType(MeasurementType t) {
	    ArrayList<String> names = new ArrayList<>();
	    for (Participant p : getParticipants()) {
			for (Tracker tr : p.getAllTrackers()) { 
				//only adds to list if tracker name not already in it 
				if (!(tr.getMeasurementsForType(t).isEmpty())) {
					if (!names.contains(tr.getName()))
						names.add(tr.getName());
				}	
			}
	    }
		return names;
	}

	/**
	 * @return a list of type integer of the number of heart 
	 * rate measurements per tracker 
	 */
	@Override
	public List<Integer> getHRMCountPerFT() {
		MeasurementType t = MeasurementType.HEART_RATE;
		ArrayList<String> trackerNames = getTrackerNamesbyMtType(t);
		int tCount = trackerNames.size();
		Integer[] trackerCount = new Integer[tCount]; //Integer to convert later
		for (int i = 0 ; i < tCount ; i ++)
			trackerCount[i] = 0 ; 
	    
	    for (Participant p : getParticipants()) {
			for (Tracker tr : p.getAllTrackers()) {
				if (!tr.getMeasurementsForType(t).isEmpty()) {
					//adds onto count of array trackerCount at same index
					//as the tracker's name in trackerNames
				    int i = trackerNames.indexOf(tr.getName());
				    trackerCount[i] += tr.getMeasurementsForType(t).size(); 
				}
	        }
	    }
		return Arrays.asList(trackerCount) ;
	}
	

	/**
	 * @return number of measurements for energy expenditure for tracker 1
	 */
	@Override
	public int getEEMCountForFT1() {
		MeasurementType t = MeasurementType.ENERGY_EXPENDITURE;
		int count = 0 ; 

		for (Participant p : getParticipants()) {
			Collection<Tracker> trFT1 = 
					Tracker.filterTrackersByName(p.getAllTrackers(), "FT1");
			if (!trFT1.isEmpty()) {
				for (Tracker tr : trFT1) {
					if (!tr.getMeasurementsForType(t).isEmpty())
						count += tr.getMeasurementsForType(t).size();
				}
			}
		}
		return count;
	}

    /**
     * @return a list of steps count for trackers 2,3,4 in order 	
     */
	@Override
	public List<Integer> getStepsMCountForFT234() {
		Integer[] stepsCount = {0,0,0};
		MeasurementType t = MeasurementType.STEPS;
		
	    for (Participant p : getParticipants()) {
	    	Collection<Tracker> trFT2 = 
	    		Tracker.filterTrackersByName(p.getAllTrackers(), "FT2");
	    	Collection<Tracker> trFT3 = 
	    		Tracker.filterTrackersByName(p.getAllTrackers(), "FT3");
	    	Collection<Tracker> trFT4 = 
	    		Tracker.filterTrackersByName(p.getAllTrackers(), "FT4");
	    	
	    	if (!trFT2.isEmpty()) {
	    		for (Tracker tr : trFT2) {
	    			if (!tr.getMeasurementsForType(t).isEmpty()) 
	    				stepsCount[0] += tr.getMeasurementsForType(t).size();
	    		}
	    	}
	    	if (!trFT3.isEmpty()) {
	    		for (Tracker tr : trFT3) {
	    			if (!tr.getMeasurementsForType(t).isEmpty()) 
	    				stepsCount[1] += tr.getMeasurementsForType(t).size();
	    		}
	    	}	 
	    	if (!trFT4.isEmpty()) {
	    		for (Tracker tr : trFT4) {
	    			if (!tr.getMeasurementsForType(t).isEmpty()) 
	    				stepsCount[2] += tr.getMeasurementsForType(t).size();
	    		}
	    	}
	    }	
		return Arrays.asList(stepsCount);
	}

	/**
	 * @return count of distance measurements for tracker 5 
	 */
	@Override
	public int getDistanceMCountForFT5() {
		MeasurementType t = MeasurementType.DISTANCE;
		int count = 0 ; 

		for (Participant p : getParticipants()) {
			Collection<Tracker> trFT5 = 
				Tracker.filterTrackersByName(p.getAllTrackers(), "FT5");
			if (!trFT5.isEmpty()) {
				for (Tracker tr : trFT5) {
					if (!tr.getMeasurementsForType(t).isEmpty())
						count += tr.getMeasurementsForType(t).size();
				}
			}
		}
		return count;
	 }
	
	/**
	 * @param t of type measurement type 
	 * @return  the highest reading in the set of MeasuremenType t 
	 */
	private double getHighestReadingbyMeasurementType(MeasurementType t) {
		double highest = 0; 
		for (Participant p : getParticipants()) {
			//filters measurements
			Collection<Measurement> measurements = 
			    Measurement.filterMeasurementsByType(p.getAllMeasurements(),t);
			if (!measurements.isEmpty()) {
			    for (Measurement m : measurements) {
				    if (m.getValue().doubleValue() > highest) 
				    	highest = m.getValue().doubleValue();
			    }
			}
		}
		return highest; 
	}
	
	/**
	 * @param coll an array 
	 * @return the highest value in passed in array 
	 */
	private double getHighestOfArray(double[] coll) {
		double max = 0 ;
		for (double c : coll) {
			if (c > max)
				max = c ;
		}
		return max;
	}

	/**
	 * @return a set of participants who have reached the highest number of steps
	 */
	@Override
	public Set<String> getHighestNumberOfStepsParticipants() {
		Set<String> highestParticipants = new HashSet<>();
		MeasurementType t = MeasurementType.STEPS; 
		double highestMeasurement = getHighestReadingbyMeasurementType(t) ;
		for (Participant p : getParticipants()) {
			Collection<Measurement> measurements = 
			    Measurement.filterMeasurementsByType(p.getAllMeasurements(),t);
			if (!measurements.isEmpty()) {
			    for (Measurement m : measurements) {
				    if (m.getValue().doubleValue() == highestMeasurement)
				    	highestParticipants.add(p.getName());
			    }
			}
		}
		return highestParticipants;
	}

	/**
	 * @return a set of participants who have reached the highest walked distance 
	 */
	@Override
	public Set<String> getHighestWalkedDistanceParticipants() {
		Set<String> highestParticipants = new HashSet<>();
		MeasurementType t = MeasurementType.DISTANCE; 
		double highestMeasurement = getHighestReadingbyMeasurementType(t) ;
		for (Participant p : getParticipants()) {
			Collection<Measurement> measurements = 
			    Measurement.filterMeasurementsByType(p.getAllMeasurements(),t);
			if (!measurements.isEmpty()) {
			    for (Measurement m : measurements) {
				    if (m.getValue().doubleValue() == highestMeasurement) 
				    	highestParticipants.add(p.getName());
			    }
			}
		}
		return highestParticipants;
	}

	/**
	 * @return the global average steps for tracker 1 (cumulative readings)
	 */
	@Override
	public double getGlobalAverageStepsForFT1() {
		MeasurementType t = MeasurementType.STEPS;
        int count = 0 ; 
        double total = 0; 
	    
        for (Participant p : getParticipants()) {
        	Collection<Tracker> trFT1 = 
        			Tracker.filterTrackersByName(p.getAllTrackers(), "FT1");
        	if (!trFT1.isEmpty()) {
        		for (Tracker tr : trFT1) {
        			if (!tr.getMeasurementsForType(t).isEmpty()) {
        				
        				//getting max value instead of last measurement in 
        				// case of faulty tracker 
        				int numMeas = tr.getMeasurementsForType(t).size();
        				count ++; 
        				int i = 0 ;
        				double[] values = new double[numMeas];
        				for (Measurement m : tr.getMeasurementsForType(t)) { 
        					values[i] = m.getValue().doubleValue() ;
        					i++;
        				}
        				total += getHighestOfArray(values);
        			}
        		}
        	}
        }
        if (count == 0)
        	return 0 ; 
        
		return (total/count) ;
	}

	/**
	 * @param p of type participant whose average is to be calculated
	 * @param t of type measurementType 
	 * @param trackerNames an array of tracker's names. Pass empty array to get all trackers 
	 * @return  the participant's average reading for the specified trackers and measurement type
	 */
	private double getParticipantAvg(Participant p ,MeasurementType t, String[] trackerNames) {
		int count = 0;
		double total = 0; 
		Collection<Tracker> trFT ; 
		int counter = 0 ;
		boolean noFilter = false;
		
		//if conditions used to allow both filtering and getting of all trackers
		//using same method
		if (trackerNames.length == 0 ) {
			counter = 1 ; 
		    noFilter = true ; 
	    }
		else 
			counter = trackerNames.length; 
		
		for (int i = 0 ; i < counter; i ++){
			if (noFilter)
				 trFT = p.getAllTrackers() ;
			else 
		        trFT = Tracker.filterTrackersByName(p.getAllTrackers(), trackerNames[i]);
			
			if (!trFT.isEmpty()) {
				for (Tracker tr : trFT) {
					if (!tr.getMeasurementsForType(t).isEmpty()) {
						//average for cumulative measurements
						if ((t.equals(MeasurementType.STEPS))||(t.equals(MeasurementType.ENERGY_EXPENDITURE))) {
							//getting max value incase of faulty tracker 
							int numMeas = tr.getMeasurementsForType(t).size();
							count ++; 
							int j = 0 ;
							double[] values = new double[numMeas];
							for (Measurement m : tr.getMeasurementsForType(t)) { 
								values[j] = m.getValue().doubleValue() ;
								j++;
							}
							total += getHighestOfArray(values);
						}	
						else {
							//average for normal measurements
							count += tr.getMeasurementsForType(t).size();
							for (Measurement m : tr.getMeasurementsForType(t)) 
								total += m.getValue().doubleValue();
						}
					}
				}
			}
		}
		if (count == 0)
		    return 0;
		return (total/count);
	}

	/**
	 * @return a list of participant names whose global steps average is above the total 
	 * global average for FT1
	 */
	@Override
	public List<String> getAvgStepsAboveGlobalParticipantsForFT1() {
		List<String> aboveAvgParticipants = new ArrayList<>();
		double globalAvg = getGlobalAverageStepsForFT1(); 
		MeasurementType t = MeasurementType.STEPS ; 
		
		for (Participant p : getParticipants()) {
		    String[] trckr = {"FT1"};
			double avg = getParticipantAvg(p , t , trckr);
			if (avg > globalAvg)
				aboveAvgParticipants.add(p.getName());
		}
		return aboveAvgParticipants;
	}

	/**
	 * @return a list of participants names whose global steps average is below the total 
	 * global average for FT1
	 */
	@Override
	public List<String> getAvgStepsBelowGlobalParticipantsForFT1() {
		List<String> belowAvgParticipants = new ArrayList<>();
		double globalAvg = getGlobalAverageStepsForFT1(); 
		MeasurementType t = MeasurementType.STEPS ; 
		
		for (Participant p : getParticipants()) {
		    String[] trckr = {"FT1"};
			double avg = getParticipantAvg(p , t , trckr);
			if (avg < globalAvg)
				belowAvgParticipants.add(p.getName());
		}
		return belowAvgParticipants;
	}

	/**
	 * @return the global average for energy expenditure for ft2 and ft3 
	 */
	@Override
	public double getGlobalAverageEEForFT2FT3() {
		MeasurementType t = MeasurementType.ENERGY_EXPENDITURE;
        int count = 0 ; 
        double total = 0; 
        String[] ft2FT3 = {"FT2" , "FT3"};
	    
        for (int i = 0 ; i < ft2FT3.length ; i ++ ) {
	        for (Participant p : getParticipants()) {
	            Collection<Tracker> trFT = 
	                Tracker.filterTrackersByName(p.getAllTrackers(), ft2FT3[i]);
	            if (!trFT.isEmpty()) {
	    	        for (Tracker tr : trFT) {
	    	            if (!tr.getMeasurementsForType(t).isEmpty()) {
	    	            	//getting max value instead of last measurement in 
	        				// case of faulty tracker 
	        				int numMeas = tr.getMeasurementsForType(t).size();
	        				count ++; 
	        				int j = 0 ;
	        				double[] values = new double[numMeas];
	        				for (Measurement m : tr.getMeasurementsForType(t)) { 
	        					values[j] = m.getValue().doubleValue() ;
	        					j++;
	        				}
	        				total += getHighestOfArray(values);
	    	            }
	    	        }
	            }
	       }
        }
        if (count == 0)
            return 0;
		return (total/count) ;
	}

	/**
	 * @return a list of type String of participant names whose energy expenditure 
	 * average for FT2 and FT3 is above the global average
	 */
	@Override
	public List<String> getAvgEEAboveGlobalParticipantsForFT2FT3() {
		List<String> aboveAvgParticipants = new ArrayList<>();
		double globalAvg = getGlobalAverageEEForFT2FT3(); 
		MeasurementType t = MeasurementType.ENERGY_EXPENDITURE ; 
		
		for (Participant p : getParticipants()) {
			String[] trckr = {"FT2" , "FT3"};
			double avg = getParticipantAvg(p , t , trckr);
			
			if (avg > globalAvg)
				aboveAvgParticipants.add(p.getName());
		}
		return aboveAvgParticipants;
	}

	/**
	 * @return a list of type String of participant names whose energy expenditure 
	 * average for FT2 and FT3 is below the global average 
	 */
	@Override
	public List<String> getAvgEEBelowGlobalParticipantsForFT2FT3() {
		List<String> belowAvgParticipants = new ArrayList<>();
		double globalAvg = getGlobalAverageEEForFT2FT3(); 
		MeasurementType t = MeasurementType.ENERGY_EXPENDITURE ; 
		
		for (Participant p : getParticipants()) {
			String[] trckr = {"FT2" , "FT3"};
			double avg = getParticipantAvg(p , t , trckr);
			
			if (avg < globalAvg)
				belowAvgParticipants.add(p.getName());	
		}
		return belowAvgParticipants;
	}

	/**
	 * @returns the global average for heart rate measurements
	 */
	@Override
	public double getGlobalAverageHR() {
		MeasurementType t = MeasurementType.HEART_RATE;
        int count = 0 ; 
        double total = 0; 
	    
        for (Participant p : getParticipants()) {
        	for (Tracker tr : p.getAllTrackers()) {
        		if (!tr.getMeasurementsForType(t).isEmpty()) {
        			count += tr.getMeasurementsForType(t).size();
        			for (Measurement m : tr.getMeasurementsForType(t)) 
        				total += m.getValue().doubleValue();
        		}
        	}
        }
        if (count == 0)
        	return 0 ;
		return (total/count) ;
	}

	/**
	 * @returns a list of type string of participant names whose 
	 * heart rate average is above the global average 
	 */
	@Override
	public List<String> getAvgHRAboveGlobalParticipants() {
		List<String> aboveAvgParticipants = new ArrayList<>();
		double globalAvg = getGlobalAverageHR(); 
		MeasurementType t = MeasurementType.HEART_RATE ; 
		
		for (Participant p : getParticipants()) {
			//empty array to get all trackers
			String[] trckr = {};
			double avg = getParticipantAvg(p , t , trckr);
			
			if (avg > globalAvg)
				aboveAvgParticipants.add(p.getName());
		}
		return aboveAvgParticipants;
	}

	/**
	 * @returns a list of participant names whose heart rate 
	 * average is below the global average 
	 */
	@Override
	public List<String> getAvgHRBelowGlobalParticipants() {
		List<String> belowAvgParticipants = new ArrayList<>();
		double globalAvg = getGlobalAverageHR() ; 
		MeasurementType t = MeasurementType.HEART_RATE ; 
		
		for (Participant p : getParticipants()) {
			//empty array to get all trackers
			//see getParticipantAvg() in line 629 for details 
			String[] trckr = {};
			double avg = getParticipantAvg(p , t , trckr);
			
			if (avg < globalAvg)
				belowAvgParticipants.add(p.getName());
		}	
		return belowAvgParticipants;
	}
	
	/**
	 * @param name of participant
	 * @param t of type MeasurementType 
	 * @param trackers an array of the tracker names 
	 * @return the average of the readings
	 */
	public double getParticipantAvgByName(String name, MeasurementType t, String[] trackers) {
		Participant p = 
		    Participant.filterParticipantsByName(getParticipants(), name).iterator().next();
		return getParticipantAvg( p , t , trackers) ; 
	}
}
