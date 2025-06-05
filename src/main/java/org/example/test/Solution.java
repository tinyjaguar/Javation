package org.example.test;

import java.util.HashMap;

class Solution {

// [1,2,1,2,3]
// Subarrays with K Different Integers

    public int subarraysWithKDistinct(int[] nums, int k) {
        int kdistinct=   kdistinct(nums, k) - kdistinct(nums, k-1);
        return kdistinct;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        s.subarraysWithKDistinct(new int[]{1,2,1,2,3},2);
    }

    public int kdistinct(int[] nums, int k){
        HashMap<Integer,Integer> freqMap = new HashMap<Integer,Integer>();
        int begin=0;
        int end=0;
        int n = nums.length;
        int count=0;
        int tempk=k;
        while(end<n){
            freqMap.put(nums[end],freqMap.getOrDefault(nums[end],0)+1);
            if(freqMap.get(nums[end])==1){
                k--;
            }
            while(k<0){
                freqMap.put(nums[begin], freqMap.getOrDefault(nums[begin],0)-1);
                if(freqMap.get(nums[begin])<1){
                    freqMap.remove(nums[begin]);
                    k++;
                }
                begin++;
            }
            end++;
            count+=end-begin+1;
            System.out.print("k="+tempk);
            System.out.print(" array ");
            for(int i=begin; i<=end-1;i++){

                    System.out.print(",");
                    System.out.print(nums[i]);

            }
            System.out.print(", count : "+count);
            System.out.println();
        }
        return count;
    }

}