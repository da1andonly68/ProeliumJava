package util;

import java.util.Random;

public class RandomArray{
     //Random Array
     int[] storage;
     int num;
     Random random = new Random();
     int loops = 0;
 
     public int[] randomArray(int size){
         int[] a = new int[size];
         storage = new int[size];
         num = size;
         for(int i = 0; i < a.length; i++){
             a[i] = check(random.nextInt(size), i);
         }
         return a;
     }
 
     /**used in random Array generation */
     public int check(int init, int iteration){
         storage[loops] = init;
         if(iteration == 0){
             loops++;
             return storage[loops - 1];
         }else{
             for(int i = 0; i < iteration; i++){
                 if(storage[loops] == storage[i]){
                     storage[loops] = random.nextInt(num);
                     i = -1;
                 }
             }
             loops++;
             return storage[loops -1];
         }
     }
 
}