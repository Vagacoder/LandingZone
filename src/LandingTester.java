import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/*
 * CS180 Dynamic Programming 
 * Landing pod on zone with spike stone
 * 
 * 
 * by Qirui @ 2019/04/16
 */

public class LandingTester {

	public static int[][] bounceRecord;
public static int vmax;
	public static void main(String[] args) {

		
		// starting horizontal velocity of landing pod
		int v = 300;

		// This is a fixed lading zone
		// int[] l = {0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,1,0,1,0,1,0,0,0,0,1,1,0,0};

		// Generate a random landingzone, an int array of 0 and 1;
		// 0 means flat surface, pod can bounce, 1 means spiky surface, pod landing
		// fails;
		// for each bounce, v can change -1, 0 or +1. Bouncing out of landing zone cause
		// landing failure.

		Random rand = new Random();
		int landingZoneLength = 1000000;

		vmax = (int) ((-1 + Math.sqrt(1+8*landingZoneLength))/2.0 + 1);
		int[] l = new int[landingZoneLength];

		System.out.println("Landing Zone is:");

		// fill landing zone with random 0 and 1
		for (int i = 0; i < landingZoneLength; i++) {

			// ensure first landing point is a safe point
			if (i == v) {
				l[i] = 0;
			} else {
				int a = rand.nextInt(100);
				if (a > 80)
					l[i] = 1;
				else
					l[i] = 0;
			}
			// print landing zone
//			if (i != 0) {
//				System.out.print("," + l[i]);
//			} else {
//				System.out.print(l[i]);
//			}
		}
		System.out.println();

		// 2D array for bounce record which is filled with -1
		bounceRecord = new int[l.length][vmax];

		for (int i = 0; i < l.length; i++) {
			Arrays.fill(bounceRecord[i], -1);
		}

		// find the minimum bounce number
		int bounceNumber = nextBounce(v, v, l);

		if (bounceNumber != Integer.MAX_VALUE) {
			System.out.println("The pod bounces " + bounceNumber + " times");
		} else {
			System.out.println("No Path");
		}

		// find and print bouncing locations of minimum bounce number
		int trackingNumber = bounceNumber;
		ArrayList<Integer> locations = new ArrayList<Integer>();
		
		for (int i = 0; i < l.length; i++) {
			for (int j = 0; j < vmax; j++) {
				if (bounceRecord[i][j] != -1 && bounceRecord[i][j] != Integer.MAX_VALUE) {

					if (bounceRecord[i][j] == trackingNumber) {
						locations.add(i);
						// print bouncing locations
						if (bounceRecord[i][j] == bounceNumber) {
							System.out.print(i);
						} else {
							System.out.print("," + i);
						}
						trackingNumber--;
						continue;
					}
				}
			}
		}
		System.out.println("\nLocation numbers are " + locations.size());

	}

	// recursive methods to find the minimum bouncing number

	public static int nextBounce(int x, int v, int[] l) {
		if (v >= vmax) {
			return Integer.MAX_VALUE;
		}

		int bounceNumber;
		int minOf3;

		// 3 base cases
		// out of landing zone
		if ((x + v) > l.length) {
			return Integer.MAX_VALUE;
		}
		// hit the spiky surface
		if (l[x] == 1) {
			return Integer.MAX_VALUE;
		}
		// final landing point
		if (l[x] == 0 && v == 0) {
			return 0;
		}

		// check bounceRecord, if has record of minimum bouncing, read it, if not,
		// calculate it.
		if (bounceRecord[x][v] != -1) {
			return bounceRecord[x][v];
		} else {
			minOf3 = Math.min(nextBounce(x + v - 1, v - 1, l),
					Math.min(nextBounce(x + v, v, l), nextBounce(x + v + 1, v + 1, l)));

			// dealing with overflow of MAX_VALUE + 1
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
