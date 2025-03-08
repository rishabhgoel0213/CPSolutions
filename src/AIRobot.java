import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AIRobot
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        Map<String, Integer> directions = new HashMap<>();
        directions.put("E", 0);
        directions.put("N", 90);
        directions.put("W", 180);
        directions.put("S", 270);
        while(t-- > 0)
        {
            int x = in.nextInt();
            int y = in.nextInt();
            int direction = directions.get(in.next());
            String path = in.next();
            for(char c : path.toCharArray())
            {
                if(c == 'R') direction -= 90;
                else if(c == 'L') direction += 90;
                else
                {
                    x += (int) Math.cos(Math.toRadians(direction));
                    y += (int) Math.sin(Math.toRadians(direction));
                }
            }
            char finalDir = ' ';
            if(Math.cos(Math.toRadians(direction)) == 1.)
            {
                finalDir = 'E';
            }
            if(Math.sin(Math.toRadians(direction)) == 1.)
            {
                finalDir = 'N';
            }
            if(Math.cos(Math.toRadians(direction)) == -1)
            {
                finalDir = 'W';
            }
            if(Math.sin(Math.toRadians(direction)) == -1)
            {
                finalDir = 'S';
            }
            System.out.println(x + " " + y + " " + finalDir);
        }

    }
}
