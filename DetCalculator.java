
/**
 * Finds the determinant of an nxn matrix represented by an int[][]
 *
 * @author Trevor Tsai and Eric Jin
 * @version November 4, 2019
 */
import java.util.*;

public class DetCalculator{

    public static void main(String[] args){
        int[][] tester = {{5,-3,9},{-1,0,4},{7,2,4}};
        System.out.println("The result should be -154.");
        System.out.println(determinant(tester));
        int[][] tester2 = {{1,2,3},{4,5,6},{7,8,9}};
        System.out.println("The result should be 0.");
        System.out.println(determinant(tester2));
        //Now on to testing 4x4 matrices
        int[][] tester3 = {{5,4,7,-1},{0,-4,2,7},{1,0,-3,9},{8,8,4,1}};
        System.out.println("The result should be 480.");
        System.out.println(determinant(tester3));
        //5x5
        int[][] tester4 = {{1,2,3,4,5},{6,7,8,9,10},{11,12,13,14,15},{16,17,18,19,20},{21,22,23,24,25}};
        System.out.println("The result should be");
        System.out.println(determinant(tester4));
    }

    public static int determinant(int[][] input){
        if(input.length != input[0].length)
            throw new IllegalArgumentException("Error. Requires a square matrix.");
        int sum = 0;
        if(input.length == 1)
            return input[0][0];
        else if(input.length == 2)
            return input[0][0] * input[1][1] - input[0][1] * input[1][0];
        else{
            for(int i = 0; i < input.length; i++){
                int[][] temp = new int[input.length - 1][input.length - 1];
                for(int j = 0;j < input.length - 1; j++){
                    for(int k = 0,l = 0;k < input.length - 1; k++){
                        if(k == i){
                            if(i == input.length - 1)
                                continue;
                            else
                                l++;
                        }
                        temp[j][k] = input[j+1][l];
                        l++;
                    }
                }
                if(i % 2 == 0)
                    sum+=input[0][i] * determinant(temp);
                else
                    sum-=input[0][i] * determinant(temp);
            }
        }
        return sum;
    }
}
