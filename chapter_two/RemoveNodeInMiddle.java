package chapter_two;

public class RemoveNodeInMiddle {

	public static void main(String[] args) {
		LinkedList<Integer> ll = new LinkedList<Integer>();

		for(int i = 1; i <= 10; i++)
		{
			ll.add(i);
		}
		
		LinkedList<Integer>.Node n = ll.getNthNode(10);
		System.out.println("Node to delete " + n);
		System.out.println(ll);
		
		ll.removeNode(n);
		System.out.println("After removal");
		System.out.println(ll);		
	}
}