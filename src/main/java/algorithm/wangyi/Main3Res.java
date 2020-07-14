package algorithm.wangyi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 类说明:这问题提问次数会很多次，如果每次提问都查询肯定会超时，
 * 所以用一个数组nx[i]来存放第i个苹果在第几堆就行，时间复杂度O(n+m+sum)
 *
 * @author 曾鹏
 */
public class Main3Res {
    public static void main(String[] args) throws IOException
    {
        BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] appleLists =new int[n];
        String appleStr = br.readLine();
        String[] appleStrList = appleStr.split(" ");
        int appleSum = 0;
        for(int i=0;i<n;i++)
        {
            appleSum += Integer.parseInt(appleStrList[i]);
            appleLists[i] = appleSum  ;
        }
        int inquiry = Integer.parseInt(br.readLine());
        String inquiryStr = br.readLine();
        int[] inquiryLists =new int[inquiry];
        String[] inquiryStrList = inquiryStr.split(" ");
        for(int i=0;i<inquiry;i++)
        {
            inquiryLists[i] = Integer.parseInt(inquiryStrList[i]);
        }

        for(int i=0;i<inquiry;i++)
        {
            int appleIndex = inquiryLists[i];
            System.out.println(findAppleInHeap(appleIndex,appleLists));
        }
    }

    public static int findAppleInHeap(int appleIndex,int[] appleLists)
    {
        int start = 0;
        int end = appleLists.length-1;
        while(start<=end)
        {

            int mid = (start+end+1)/2;
            if(appleIndex<appleLists[mid])
            {
                end = mid-1;
            }else if(appleIndex>appleLists[mid])
            {
                start = mid+1;

            } else
                return (mid+1);

        }
        return Math.max(start+1,end+1);
    }
}
