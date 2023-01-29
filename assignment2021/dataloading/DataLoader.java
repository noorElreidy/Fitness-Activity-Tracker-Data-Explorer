package assignment2021.dataloading;

import java.util.Arrays;
import java.util.List;

//import org.junit.platform.commons.util.StringUtils;

import java.util.*;

import assignment2021.codeprovided.dataloading.AbstractDataLoader;
import assignment2021.codeprovided.dataloading.DataParsingException;
import assignment2021.codeprovided.fitnesstracker.Participant;
import assignment2021.codeprovided.fitnesstracker.measurements.MeasurementFactory;
import assignment2021.codeprovided.fitnesstracker.measurements.MeasurementType;

public class DataLoader extends AbstractDataLoader {


	/**
	 * @param a List of type string of files in order 
	 * @return a participant from the data file
	 */
	@Override
	public Participant loadDataLines(List<String> lines) throws DataParsingException {
		//gets participant's details 
		String[] prtcpntVars = lines.get(0).split(",");
		
		if (prtcpntVars.length != 3)
			throw new DataParsingException("User details missing");
		if (!prtcpntVars[2].toUpperCase().equals("F") && !prtcpntVars[2].toUpperCase().equals("M"))
			throw new DataParsingException("Gender incorrect");
		if (!isNumeric(prtcpntVars[1]))
			throw new DataParsingException("Age is not an Integer");
		
		int age = Integer.valueOf(prtcpntVars[1]);
		Participant participant = 
				new Participant(prtcpntVars[0],age,prtcpntVars[2]);
		
		
		int i = 1 ;
		try {
			while (i < lines.size()) {
				if (lines.get(i).equals(""))
					throw new DataParsingException(prtcpntVars[0] + "'s file contains an empty line");

				//assumes new measurement type if no ";" in line
				if  (!(lines.get(i).contains(CELL_SEPARATOR))) {
					String measurementName = lines.get(i);
					MeasurementType type = 
							MeasurementType.fromMeasurementName(measurementName);
					List<String> trackers = new ArrayList<>() ;
					//gets tracker names from next line
					trackers.addAll(Arrays.asList(lines.get(i+1).split(CELL_SEPARATOR))); 

					int numTrackers = trackers.size();
					int j = i + 2 ; 
					
                    //gets trackers' measurements from next line onwards 
					while (j < lines.size() &&  lines.get(j).contains(CELL_SEPARATOR)) {

						List<String> measurements = new ArrayList<>();
						measurements.addAll(Arrays.asList(lines.get(j).split(CELL_SEPARATOR))) ;
						int count = Integer.valueOf(measurements.get(0));

                        //gets measurement starting from second element in split array 
						for(int r = 1 ; r < numTrackers ; r ++) {
							participant.addMeasurementToTracker(trackers.get(r),
									MeasurementFactory.createMeasurement(type, count,
											measurements.get(r)));
						}
						j ++ ;
						measurements.clear(); 
					}
					i = j ;
					trackers.clear();
				}
			}
		} catch (IndexOutOfBoundsException | NumberFormatException e) {
			System.out.println("Error in " + prtcpntVars[0] + "'s file");
			
		}

		return participant;
	}
	
	public static boolean isNumeric(String str) {
	    try {
	        int num = Integer.parseInt(str);
	        return true;
	    } catch (Exception e) {
	        return false;
	    }
	}

}
