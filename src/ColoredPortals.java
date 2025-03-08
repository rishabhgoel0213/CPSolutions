import java.util.*;

public class ColoredPortals
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        for(int i = 0; i < t; i++)
        {
            int n = in.nextInt();
            int q = in.nextInt();
            boolean[] blue = new boolean[n];
            boolean[] red = new boolean[n];
            boolean[] green = new boolean[n];
            boolean[] yellow = new boolean[n];
            for(int j = 0; j < n; j++)
            {
                String portals = in.next();
                if(portals.equals("BG"))
                {
                    blue[j] = true;
                    red[j] = false;
                    green[j] = true;
                    yellow[j] = false;
                }
                else if(portals.equals("BR"))
                {
                    blue[j] = true;
                    red[j] = true;
                    green[j] = false;
                    yellow[j] = false;
                }
                else if(portals.equals("BY"))
                {
                    blue[j] = true;
                    red[j] = false;
                    green[j] = false;
                    yellow[j] = true;
                }
                else if(portals.equals("GY"))
                {
                    blue[j] = false;
                    red[j] = false;
                    green[j] = true;
                    yellow[j] = true;
                }
                else if(portals.equals("GR"))
                {
                    blue[j] = false;
                    red[j] = true;
                    green[j] = true;
                    yellow[j] = false;
                }
                else if(portals.equals("RY"))
                {
                    blue[j] = false;
                    red[j] = true;
                    green[j] = false;
                    yellow[j] = true;
                }
            }
            Map<Integer, int[]> shortestPaths = new HashMap<>();
            for(int j = 0; j < q; j++)
            {
                int start = in.nextInt() - 1;
                int end = in.nextInt() - 1;
                if(shortestPaths.containsKey(start))
                {
                    System.out.println(shortestPaths.get(start)[end] == Integer.MAX_VALUE ? -1 : shortestPaths.get(start)[end]);
                    continue;
                }
                int[] dist = new int[n];
                Arrays.fill(dist, Integer.MAX_VALUE);
                dist[start] = 0;

                PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
                pq.offer(new int[]{0, start});

                boolean[] visited = new boolean[n];

                while (!pq.isEmpty()) {
                    int[] top = pq.poll();
                    int curDist = top[0];
                    int u = top[1];

                    if (visited[u]) continue;
                    visited[u] = true;

                    for(int v = 0; v < n; v++)
                    {
                        if(u != v && ((blue[u] && blue[v]) || (red[u] && red[v]) || (green[u] && green[v]) || (yellow[u] && yellow[v])))
                        {
                            int w = Math.abs(u - v);
                            if (dist[u] + w < dist[v])
                            {
                                dist[v] = dist[u] + w;
                                pq.offer(new int[]{dist[v], v});
                            }
                        }
                    }
                }
                shortestPaths.putIfAbsent(start, dist);
                System.out.println(dist[end] == Integer.MAX_VALUE ? -1 : dist[end]);
            }
        }
    }
}
