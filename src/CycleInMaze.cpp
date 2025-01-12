//https://codeforces.com/contest/769/problem/C - Accepted

#include <iostream>
#include <string>
#include <stack>
#include <memory>
#include <vector>
#include <queue>

struct node
{
    int pos, depth;
    char move;
    int parentIndex;
    node(int p, int d, char m, int i) : pos(p), depth(d), move(m), parentIndex(i) {}
};


int main()
{
    int n, m, k;
    std::cin >> n >> m >> k;
    if(k % 2 != 0)
    {
        std::cout << "IMPOSSIBLE" << std::endl;
        return 0;
    }
    std::vector<bool> obstacle(n * m);
    int start = -1;

    for (int i = 0; i < n; i++)
    {
        std::string line;
        std::cin >> line;
        for (int j = 0; j < m; j++)
        {
            char square = line[j];
            if (square == 'X') start = i * m + j;
            obstacle[i * m + j] = square == '*';
        }
    }

    int end = -1;
    std::string firstHalf(k / 2, ' ');

    std::stack<node> dfs;
    dfs.push(node(start, 0, ' ', 0));
    std::vector<node> parentNodes;
    parentNodes.push_back(dfs.top());
    while (!dfs.empty())
    {
        node u = dfs.top();
        dfs.pop();
        if (u.depth < k / 2)
        {
            int i = u.pos / m;
            int j = u.pos % m;
            int down = (i == n - 1) ? -1 : (i + 1) * m + j;
            int left = (j == 0) ? -1 : i * m + (j - 1);
            int right = (j == m - 1) ? -1 : i * m + (j + 1);
            int up = (i == 0) ? -1 : (i - 1) * m + j;
            if (down >= 0 && !obstacle[down])
            {
                parentNodes.push_back(u);
                dfs.push(node(down, u.depth + 1, 'D', parentNodes.size() - 1));
            }
            else if (left >= 0 && !obstacle[left])
            {
                parentNodes.push_back(u);
                dfs.push(node(left, u.depth + 1, 'L', parentNodes.size() - 1));
            }
            else if (right >= 0 && !obstacle[right])
            {
                parentNodes.push_back(u);
                dfs.push(node(right, u.depth + 1, 'R', parentNodes.size() - 1));
            }
            else if (up >= 0 && !obstacle[up])
            {
                parentNodes.push_back(u);
                dfs.push(node(up, u.depth + 1, 'U', parentNodes.size() - 1));
            }
        }
        else
        {
            dfs = std::stack<node>();
            end = u.pos;
            for(int i = 1; i <= k / 2; i++)
            {
                firstHalf[k / 2 - i] = u.move;
                u = parentNodes[u.parentIndex];
            }
            parentNodes.clear();
            break;
        }
    }
    if(end == -1)
    {
        std::cout << "IMPOSSIBLE" << std::endl;
        return 0;
    }
    std::vector<int> startDist(n * m, -1);
    startDist[start] = 0;
    std::queue<int> q;
    std::vector<bool> visited(n * m, false);
    q.push(start);
    visited[start] = true;
    while(!q.empty())
    {
        int u = q.front();
        q.pop();
        int i = u / m;
        int j = u % m;
        int down = (i == n - 1) ? -1 : (i + 1) * m + j;
        int left = (j == 0) ? -1 : i * m + (j - 1);
        int right = (j == m - 1) ? -1 : i * m + (j + 1);
        int up = (i == 0) ? -1 : (i - 1) * m + j;
        if (up >= 0 && !obstacle[up] && !visited[up])
        {
            visited[up] = true;
            startDist[up] = startDist[u] + 1;
            q.push(up);
        }
        if (right >= 0 && !obstacle[right] && !visited[right])
        {
            visited[right] = true;
            startDist[right] = startDist[u] + 1;
            q.push(right);
        }
        if (left >= 0 && !obstacle[left] && !visited[left])
        {
            visited[left] = true;
            startDist[left] = startDist[u] + 1;
            q.push(left);
        }
        if (down >= 0 && !obstacle[down] && !visited[down])
        {
            visited[down] = true;
            startDist[down] = startDist[u] + 1;
            q.push(down);
        }
    }
    std::string secondHalf(k / 2, ' ');
    dfs.push(node(end, 0, ' ', 0));
    parentNodes.push_back(dfs.top());
    while (!dfs.empty())
    {
        node u = dfs.top();
        dfs.pop();
        if (u.depth < k / 2)
        {
            int i = u.pos / m;
            int j = u.pos % m;
            int down = (i == n - 1) ? -1 : (i + 1) * m + j;
            int left = (j == 0) ? -1 : i * m + (j - 1);
            int right = (j == m - 1) ? -1 : i * m + (j + 1);
            int up = (i == 0) ? -1 : (i - 1) * m + j;
            if (down >= 0 && !obstacle[down] && startDist[down] < k / 2 - u.depth)
            {
                parentNodes.push_back(u);
                dfs.push(node(down, u.depth + 1, 'D', parentNodes.size() - 1));
            }
            else if (left >= 0 && !obstacle[left] && startDist[left] < k / 2 - u.depth)
            {
                parentNodes.push_back(u);
                dfs.push(node(left, u.depth + 1, 'L', parentNodes.size() - 1));
            }
            else if (right >= 0 && !obstacle[right] && startDist[right] < k / 2 - u.depth)
            {
                parentNodes.push_back(u);
                dfs.push(node(right, u.depth + 1, 'R', parentNodes.size() - 1));
            }
            else if (up >= 0 && !obstacle[up] && startDist[up] < k / 2 - u.depth)
            {
                parentNodes.push_back(u);
                dfs.push(node(up, u.depth + 1, 'U', parentNodes.size() - 1));
            }
        }
        else
        {
            for(int i = 1; i <= k / 2; i++)
            {
                secondHalf[k / 2 - i] = u.move;
                u = parentNodes[u.parentIndex];
            }
            parentNodes.clear();
            break;
        }
    }
    std::cout << firstHalf + secondHalf << std::endl;
    return 0;
}
