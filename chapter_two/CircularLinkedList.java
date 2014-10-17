package chapter_two;

public class CircularLinkedList {

	public static void main(String[] args) {
		LinkedList<Integer> ll1 = new LinkedList<>();
		
		for(int i = 0; i < 10; i++)
		{
			ll1.add(i);
		}

		LinkedList<Integer>.Node loop_start = ll1.getNthNode(7);
		LinkedList<Integer>.Node loop_end = ll1.getNthNode(1);
		
		System.out.println("Start " + loop_start + " end " + loop_end);
		
		System.out.println("Before corruption: " + ll1.findBeginning());
		loop_end.m_next = loop_start;
		System.out.println("After corruption: " + ll1.findBeginning());
	}
}
