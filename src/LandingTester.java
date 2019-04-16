import java.lang.reflect.Array;
import java.util.Arrays;

public class LandingTester {

	public static int[][] bounceRecord;

	public static void main(String[] args) {
//		int a = Integer.MAX_VALUE;
//		System.out.println(a);
//		System.out.println(a+1);

		int[] l = { 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0 };
		int v = 3;

		// 2D array filled with -1
		bounceRecord = new int[l.length][v + l.length];

		for (int i = 0; i < l.length; i++) {
			Arrays.fill(bounceRecord[i], -1);
		}

		int bounceNumber = nextBounce(3, v, l);
		System.out.println("The pod bounces " + bounceNumber + " times");
		
		int[] bounceLocations = new int[bounceNumber];
		
		for (int i = 0; i < l.length; i ++ ) {
			for (int j = 0; j < v + l.length; j++) {
				if (bounceRecord[i][j] != -1 && bounceRecord[i][j] != Integer.MAX_VALUE) {
					System.out.print(i + ",");
				}
			}
		}

	}

	public static int nextBounce(int x, int v, int[] l) {

		int bounceNumber;
		int minOf3;

		if ((x + v) > l.length) {
			return Integer.MAX_VALUE;
		}

		if (l[x] == 1) {
			return Integer.MAX_VALUE;
		}

		if (l[x] == 0 && v == 0) {
			return 0;
		}

		if (bounceRecord[x][v] != -1) {
			return bounceRecord[x][v];
		} else {
			minOf3 = Math.min(nextBounce(x + v - 1, v - 1, l),
					Math.min(nextBounce(x + v, v, l), nextBounce(x + v + 1, v + 1, l)));
			
			if (minOf3 == Integer.MAX_VALUE) {
				bounceNumber = minOf3;
			} else {
				bounceNumber = 1 + minOf3;
			}
			bounceRecord[x][v] = bounceNumber;
			return bounceNumber;

		}
	}
}
