import java.util.*;

public class BestPrice
{

    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        for(int i = 0; i < t; i++)
        {
            int n = in.nextInt();
            int k = in.nextInt();
            int[] a = new int[n + 1];
            int[] b = new int[n + 1];
            int[] bSort = new int[n + 1];
            for(int j = 1; j <= n; j++)
            {
                a[j] = in.nextInt();
            }
            for(int j = 1; j <= n; j++)
            {
                b[j] = in.nextInt();
                bSort[j] = b[j];
            }
            Arrays.sort(bSort);
            int[] priceIndex = new int[n + 1];
            int l = 1;
            for(int j = 2; j <= n; j++)
            {
                if(bSort[j] != bSort[j - 1])
                {
                    l++;
                }
                priceIndex[l] = bSort[j];
            }
            long maxPrice = -1;
            for(int price = 1; price <= (int) 2e9; price++)
            {
                int index = Arrays.binarySearch(bSort, price);
                if(index < 0)
                {
                    index = -(index + 1);
                }
                if(index < k)
                {
                    maxPrice = price;
                }
            }
            long totalEarnings = 0;
            for(int j = 1; j <= n; j++)
            {
                if(a[j] >= maxPrice)
                {
                    totalEarnings += a[j];
                }
                else if(b[j] >= maxPrice)
                {
                    totalEarnings += b[j];
                }
            }
            System.out.println(totalEarnings);
        }
    }
}
