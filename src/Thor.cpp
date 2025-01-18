#include <iostream>
#include <vector>
#include <queue>
#include <cmath>

int sum(int k, const std::vector<int>& tree)
{
    int s = 0;
    while(k >= 1)
    {
        s += tree[k];
        k -= k&-k;
    }
    return s;
}

void add(int k, int x, int n, std::vector<int>& tree)
{
    while(k <= n)
    {
        tree[k] += x;
        k += k&-k;
    }
}


int main()
{
    int n, q;
    std::cin >> n >> q;
    std::vector<int> notifs(n + 1, 0);
    std::vector<int> read(n + 1, 0);
    std::vector<int> tree(n + 1, 0);
    std::vector<int> app;
    int tMax = 0;
    while(q--)
    {
        int type;
        std::cin >> type;
        if(type == 1)
        {
            int x;
            std::cin >> x;
            app.push_back(x);
            notifs[x] += 1;
            add(x, 1, n, tree);
        }
        else if(type == 2)
        {
            int x;
            std::cin >> x;
            add(x, -notifs[x], n, tree);
            read[x] += notifs[x];
            notifs[x] = 0;
        }
        else if(type == 3)
        {
            int t;
            std::cin >> t;
            if(t > tMax)
            {
                for(int i = tMax; i < t; i++)
                {
                    if(read[app[i]] > 0)
                    {
                        read[app[i]]--;
                    }
                    else
                    {
                        notifs[app[i]]--;
                        add(app[i], -1, n, tree);
                    }
                }
                tMax = t;
            }

        }
        std::cout << sum(n, tree) << std::endl;
    }
    return 0;
}