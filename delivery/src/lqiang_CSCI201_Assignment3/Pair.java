package lqiang_CSCI201_Assignment3;
import java.lang.Comparable;





public class Pair implements Comparable<Pair> {
	public int first;
	public String second;
	public String third;
	
	Pair(Integer one, String two, String three){
		this.first=one;
		this.second=two;
		this.third=three;
	}
	
	@Override
	public int compareTo(Pair p) {
		if(this.first==p.first)
		{
			return 0;
		}
		else if(this.first>p.first)
		{
			return 1;
		}
		else
		{
			return -1;
		}
	}
	
}
