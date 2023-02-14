package cap11;

import java.util.*;

public class Ex01_11 {
    
    public static List<Integer> binarySearch(int [] arr, int value){
        
        List<Integer> result = new ArrayList<Integer>();

        int low =0, high = arr.length-1, mid = (low+high)/2;

        while((low<=high) && (arr[mid] != value)){

            if(value <arr[mid]){
                high = mid-1;
            }else {
                low = mid+1;
            }
            mid = (low+high)/2;
            
        }
        
        
        result.add(low);
        result.add(high);
        

        return result;
    }
    public static void main(String[] args) {
        int [] arr={10, 15, 25, 30, 33, 34, 46, 55, 78, 84, 96, 99};
        
        System.out.println(binarySearch(arr,23).toString());
    }
}
