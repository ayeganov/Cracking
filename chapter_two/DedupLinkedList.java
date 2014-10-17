package chapter_two;

import java.util.Random;
/**
 * @author ayeganov
 *
 */
public class DedupLinkedList {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LinkedList<Integer> ll = new LinkedList<Integer>();
		Random random = new Random();
		int old_number = random.nextInt(100000);
		int threshold = 666;
		
		System.out.println("Threshold: " + threshold);
		int added_new = 0;
		int added_old = 0;
		for(int i = 0; i < 10000; i++)
		{
			int new_number = random.nextInt(100000);
			if(new_number >= threshold)
			{
				ll.add(new_number);
				old_number = new_number;
				added_new++;
			}
			else
			{
				ll.add(old_number);
				added_old++;
			}
		}
		System.out.println("New: " + added_new + " old: " + added_old);
		//ll.print_list();

		long start = System.currentTimeMillis();
		int dups = ll.dedup_no_buffer();
		System.out.println("Dedup took to execute: " + (System.currentTimeMillis() - start) + " to remove " + dups + " duplicates");
		//ll.print_list();
	}
}