import java.util.Arrays;
import java.util.Scanner;

public class GoingInCircles
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        while(t-- > 0)
        {
            int N = in.nextInt();
            int S = in.nextInt();
            char[] circ = new char[S];
            Arrays.fill(circ, ' ');
            int readPos = 0;
            String s = in.next();
            for(int i = 0; i < N; i++)
            {
                if (s.equals("ADD"))
                {
                    String s2 = in.next();
                    int start = readPos;
                    while(circ[start % S] != ' ' && (start == readPos || start % S != readPos % S))
                    {
                        start++;
                    }
                    while (!s2.equals("ADD") && !s2.equals("SHOW") && !s2.equals("CONSUME"))
                    {
                        if(start - readPos >= S) readPos++;
                        circ[start % S] = s2.charAt(0);
                        s2 = in.next();
                        start++;
                    }
                    s = s2;
                }
                else if (s.equals("CONSUME"))
                {
                    int n = in.nextInt();
                    for(int j = 0; j < n; j++)
                    {
                        circ[readPos % S] = ' ';
                        readPos++;
                    }
                    s = in.next();
                }
                else if(s.equals("SHOW"))
                {
                    int end = readPos+1;
                    while(circ[end % S] != ' ' && end % S != readPos % S)
                    {
                        end++;
                    }
                    end--;
                    if(end - readPos + (readPos % S) >= S)
                    {
                        int div = end % S + S;
                        end = readPos % S;
                        System.out.println(end + " " + div);
                        if ((div - end) / 2. != (int) ((div - end) / 2.))
                        {
                            System.out.println(circ[(end + (div - end) / 2) % S] + " " + circ[(end + (div - end) / 2 + 1) % S]);
                        }
                        else
                        {
                            System.out.println(circ[(end + (div - end) / 2) % S]);
                        }
                    }
                    else
                    {
                        if ((end - readPos) / 2. != (int) ((end - readPos) / 2.))
                        {
                            System.out.println(circ[(end + (readPos - end) / 2) % S] + " " + circ[(end + (readPos - end) / 2 + 1) % S]);
                        }
                        else
                        {
                            System.out.println(circ[(end + (readPos - end) / 2) % S]);
                        }
                    }
                    s = in.next();
                }
                System.out.println(Arrays.toString(circ));
            }
        }
    }
}
