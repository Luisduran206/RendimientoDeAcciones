import java.util.Arrays;
import java.util.Random;


public class PriceVariationGenerator {

	public static void main(String[] args) {
        double[] priceVariations = generatePriceVariations(100, 5.0);
		for (int i = 0; i < priceVariations.length; i++) {
            System.out.printf("Día %d: %.2f%%\n", (i+1), priceVariations[i]);
        }
        System.out.println("\n\t\t FUERZA BRUTA"); 
        double sumaBruta = fuerzaBruta(priceVariations);
        
        System.out.println("\n\t\t KADANE");
        double kadane = kadane(priceVariations);
    }
	
	public static double kadane(double[] priceVariations) {
		double[] segundo = Arrays.copyOf(priceVariations, priceVariations.length);
		double maxEndingHere;
		double maxSoFar = segundo[0];
		int startIdx;
        int endIdx;
        int tempStartIdx;
		for(int vez = 1; vez < 6; vez++) {
			maxEndingHere = segundo[0];
	        maxSoFar = segundo[0];
	        
	        startIdx = 0;
	        endIdx = 0;
	        tempStartIdx = 0;
	        
	        for (int i = 1; i < segundo.length; i++) {
	            if (segundo[i] > maxEndingHere + segundo[i]) {
	                maxEndingHere = segundo[i];
	                tempStartIdx = i;
	            } else {
	                maxEndingHere = maxEndingHere + segundo[i];
	            }
	            
	            if (maxEndingHere > maxSoFar) {
	                maxSoFar = maxEndingHere;
	                startIdx = tempStartIdx;
	                endIdx = i;
	            }
	        }
	        if(maxSoFar <= 0) {
	        	break;
	        }
	        int inicio = startIdx++, fin = endIdx++;
	        System.out.printf("\nIntervalo #%d de Mayor Rendimiento: Día " + inicio + " a Día " + fin + " con una ganancia acumulada de %.2f%%", vez,  maxSoFar);
			for(int k = startIdx; k < endIdx; k++) {
				segundo[k] = 0;
			}
	        
		}
		return maxSoFar;
	}
	
	public static double fuerzaBruta(double[] priceVariations) {
		double [] nuevo = Arrays.copyOf(priceVariations, priceVariations.length);
		double maxSum = Double.NEGATIVE_INFINITY;
        int startIndex;
        int endIndex;
        
        for(int vez = 1; vez < 6; vez++) {
        	maxSum = Double.NEGATIVE_INFINITY;
            startIndex = -1;
            endIndex = -1;
        	for (int i = 0; i < nuevo.length; i++) {
                double sum = 0;
                for (int j = i; j < nuevo.length; j++) {
                    sum += nuevo[j];
                    if (sum > maxSum) {
                        maxSum = sum;
                        startIndex = i;
                        endIndex = j;
                    }
                }
            }
        	if(maxSum <= 0) {
	        	break;
	        }
        	System.out.printf("\nIntervalo #%d de Mayor Rendimiento: Día " + startIndex + " a Día " + endIndex + " con una ganancia acumulada de %.2f%%", vez,  maxSum);
            for(int x = startIndex; x < endIndex; x++) {
            	nuevo[x] = 0;
            }
        }
        return maxSum;
	}
	
    public static double[] generatePriceVariations(int days, double maxVariationPercent) {
        double[] variations = new double[days];
        Random random = new Random();
 
        for (int i = 0; i < days; i++) {
            // Genera una variación aleatoria entre -maxVariationPercent y +maxVariationPercent
            double variation = (2 * random.nextDouble() - 1) * maxVariationPercent;
            variations[i] = Math.round(variation * 100.0) / 100.0; // Redondea a 2 decimales
        }
 
 
        return variations;
    }
}
