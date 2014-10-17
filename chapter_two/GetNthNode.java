package chapter_two;

public class GetNthNode {

	public static void main(String[] args) {
		LinkedList<Integer> ll = new LinkedList<>();

		for(int i = 1; i <= 10; i++)
		{
			ll.add(i);
		}
		
		int n = 7;
		System.out.println(n + "th node is " + ll.getNthNode(n));
		
		System.out.println("List size: " + ll.countList());
		
		ll.print_list();
		ll.recursiveReverse();
		ll.print_list();
	}
}