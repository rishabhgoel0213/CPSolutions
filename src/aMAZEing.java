import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class aMAZEing
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        while(t-- > 0)
        {
            int tempH = in.nextInt();
            int tempW = in.nextInt();
            in.nextLine();
            char[][] grid = new char[tempH][tempW];
            for(int i = 0; i < tempH; i++)
            {
                grid[i] = in.nextLine().toCharArray();
            }
            int h = (tempH - 1) / 2;
            int w = (tempW - 1) / 3;
            int startSquare = -1;
            int endSquare = - -1;
            boolean[][] validMove = new boolean[h * w][4];
            for(int i = 0; i < h; i++)
            {
                int pos = (i * 2);
                for(int j = 0; j < w; j++)
                {
                    int pos2 = j * 3;
//                    System.out.println(i + " " + j);

                    //TOP
                    if(grid[pos][pos2 + 1] == ' ')
                    {
                        validMove[j + i * w][0] = true;
                    }
                    else if(grid[pos][pos2 + 1] == 'v')
                    {
                        startSquare = j + i * w;
                    }
                    else if(grid[pos][pos2 + 1] == '^')
                    {
                        endSquare = j + i * w;
                    }

                    //LEFT
                    if(grid[pos + 1][pos2] == ' ')
                    {
                        validMove[j + i * w][1] = true;
                    }
                    else if(grid[pos + 1][pos2] == '>')
                    {
                        startSquare = j + i * w;
                    }
                    else if(grid[pos + 1][pos2] == '<')
                    {
                        endSquare = j + i * w;
                    }

                    //DOWN
                    if(grid[pos + 2][pos2 + 1] == ' ')
                    {
                        validMove[j + i * w][2] = true;
                    }
                    else if(grid[pos + 2][pos2 + 1] == '^')
                    {
                        startSquare = j + i * w;
                    }
                    else if(grid[pos + 2][pos2 + 1] == 'v')
                    {
                        endSquare = j + i * w;
                    }

                    //RIGHT
                    if(grid[pos + 1][pos2 + 3] == ' ')
                    {
                        validMove[j + i * w][3] = true;
                    }
                    else if(grid[pos + 1][pos2 + 3] == '<')
                    {
                        startSquare = j + i * w;
                    }
                    else if(grid[pos + 1][pos2 + 3] == '>')
                    {
                        endSquare = j + i * w;
                    }
                }
            }
//            System.out.println("HIIIII");
            Queue<int[]> bfs = new LinkedList<>();
            bfs.add(new int[]{startSquare, 1});
            boolean[] visited = new boolean[h * w];
            Arrays.fill(visited, false);
            while(!bfs.isEmpty())
            {
                int[] value = bfs.poll();
                int node = value[0];
                int dist = value[1];
                visited[node] = true;
                if(node == endSquare)
                {
                    System.out.println(dist);
                    break;
                }
                if(validMove[node][0] && !visited[node - w])
                {
                    bfs.add(new int[]{node - w, dist + 1});
                }
                if(validMove[node][1] && !visited[node - 1])
                {
                    bfs.add(new int[]{node - 1, dist + 1});
                }
                if(validMove[node][2] && !visited[node + w])
                {
                    bfs.add(new int[]{node + w, dist + 1});
                }
                if(validMove[node][3] && !visited[node + 1])
                {
                    bfs.add(new int[]{node + 1, dist + 1});
                }
            }

        }
    }
}
