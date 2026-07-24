package upo.algotechniques;

import java.util.ArrayList;
import java.util.Comparator;


public class Greedy {
	/** Trova lo scheduling massimale, utilizzando l'algoritmo di Moore, tra i job identificati dai vettori duration e deadline
	 * (duration[i] e deadline[i] sono, rispettivamente, la durata e la scadenza del job L_i). Il risultato contiene, nell'ordine
	 * selezionato dall'algoritmo, gli indici dei job nello scheduling massimale.
	 * 
	 * @param duration il vettore delle durate
	 * @param deadline il vettore delle scadenze
	 * @return un vettore contenente gli indici dei job in uno scheduling massimale
	 */
	
	static private class Job {
        int duration;
        int deadline;

        Job(int deadline, int duration) {
        	this.deadline = deadline;
            this.duration = duration;
        }
 }
	
	private static Job trovaJobPiuLungo(ArrayList<Job> lista) {
		Job max = lista.get(0);
		for(Job elemento : lista) {
			if(elemento.duration >= max.duration) {
				max = elemento;
			}
		}
		return max;
	}
	public static Integer[] getMooreMaxJobs(Integer[] duration, Integer[] deadline) {
		//NON POSSO USARE Hash maps, con quelle non potrei avere jobs con stessa durata o scadenza (a seconda di cosa metti come chiave)

		 
		ArrayList<Job> order = new ArrayList<>();		
		for(int i=0;i<duration.length;i++) {
			order.add(new Job(deadline[i],duration[i]));
		}
		order.sort(Comparator.comparingInt(j->j.deadline));
		for(int i=0;i<deadline.length;i++) {
		}
		ArrayList<Job> Sol = new ArrayList<>();
		int time=0;
		for(Job elemento : order) {
			Job currentJob = elemento;
			Sol.add(currentJob);
			time += currentJob.duration;
			if(time>currentJob.deadline) {
				Sol.remove(trovaJobPiuLungo(Sol));
				time -= trovaJobPiuLungo(Sol).duration;
			}
		}
		Integer [] result = new Integer[Sol.size()];
		for(int i=0; i<Sol.size();i++) {
			result[i] = order.indexOf(Sol.get(i));
		}
		return result;
	}

}
