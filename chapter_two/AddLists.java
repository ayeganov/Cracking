package chapter_two;

public class AddLists {

	public static void main(String[] args)
	{
		LinkedList<Integer> ll1 = new LinkedList<>();
		LinkedList<Integer> ll2 = new LinkedList<>();
		
		ll1.add(1);
		ll1.add(2);
		
		ll2.add(3);
		ll2.add(2);
		
		System.out.println(ll1.addLists(ll2));
	}

}