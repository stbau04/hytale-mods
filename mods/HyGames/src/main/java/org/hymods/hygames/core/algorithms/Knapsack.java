package org.hymods.hygames.core.algorithms;

import java.util.*;
import java.lang.*;
import java.io.*;

public class Knapsack
{
    public static int[] solve(Integer[] weights, Integer[] values, int maxWeight)
    {
        var visited = new int[weights.length];
        knapsack(maxWeight, weights, values, weights.length, visited);
        return visited;
    }

    private static int max(int a, int b) {
        return Math.max(a, b);
    }

    private static int knapsack(int W, Integer[] wt, Integer[] val, int n, int[] visited)
    {
        if (n == 0 || W == 0)
            return 0;

        if (wt[n-1] > W)
        {
            return knapsack(W, wt, val, n-1,visited);
        } else {
            var v1 =new int[visited.length];
            System.arraycopy(visited, 0, v1, 0, v1.length);

            var v2 =new int[visited.length];
            System.arraycopy(visited, 0, v2, 0, v2.length);
            v1[n-1]=1;

            int ans1 = val[n-1] + knapsack(W-wt[n-1], wt, val, n-1,v1);
            int ans2 = knapsack(W, wt, val, n-1,v2);
            if(ans1>ans2){
                System.arraycopy(v1, 0, visited, 0, v1.length);
                return ans1;
            } else {
                System.arraycopy(v2, 0, visited, 0, v2.length);
                return ans2;
            }
        }
    }
}
