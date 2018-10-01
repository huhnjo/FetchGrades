package script;

import java.util.ArrayList;

public class AvgCalculator {
	
	public static double calculateAverage(ArrayList<Modul> notenlist){
		if(notenlist != null){
			int ectsSum = 0;
			double notenSum = 0;
			for (int i = 0; i < notenlist.size(); i++) {
				notenSum += notenlist.get(i).getGrade() * notenlist.get(i).getEcts();
				ectsSum += notenlist.get(i).getEcts();
			}
			return notenSum / (double)ectsSum; 
		}else{
			return 0;
		}
	}	
}
