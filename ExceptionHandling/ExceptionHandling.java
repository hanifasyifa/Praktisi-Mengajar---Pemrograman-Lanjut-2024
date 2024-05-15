import java.util.ArrayDeque;
import java.util.Queue;
import java.util.NullPointerException;

public class ExceptionHandling {
    public static void main(String[] args) {
        Queue<Pasien> queue = new ArrayDeque<>(10); // Initialize the queue with a capacity of 10

        // Add patients to the queue
        queue.offer(new Pasien("Lala", "RS_01"));
        queue.offer(new Pasien("Gia", "RS_02"));

        // Process patients in the queue with exception handling
        try {
            processQueue(queue);
        } catch (NullPointerException e) {
            System.out.println("The queue is empty. No patients to process.");
        }
    }

    private static void processQueue(Queue<Pasien> queue) throws NullPointerException {
        while (!queue.isEmpty()) {
            Pasien pasien = queue.poll();
            
            if (pasien != null) {
                System.out.println("Nomor Antrean: " + pasien.getAntrean());
                System.out.println("Sedang Memeriksa " + pasien.getNama() + " dengan nomor antrean " + pasien.getAntrean());

                // Simulate patient examination process
                System.out.println("Selesai memeriksa " + pasien.getNama() + "\n");
            }
        }
        // Handle case where queue is empty (this part is now redundant because the while loop handles empty queue cases)
        System.out.println("The queue is empty. No patients to process.");
 
    }
}
