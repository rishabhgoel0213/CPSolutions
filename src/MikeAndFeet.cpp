//https://codeforces.com/problemset/problem/547/B - Accepted

#include <iostream>
#include <numeric>
#include <algorithm>

int findRoot(int v, int parent[])
{
    int u = parent[v];
    while(u != v)
    {
        parent[v] = parent[u];
        v = u;
        u = parent[u];
    }
    return v;
}
void unionSets(int v, int u, int parent[], int size[])
{
    if(parent[v] != -1 && parent[u] != -1)
    {
        int rootV = findRoot(v, parent);
        int rootU = findRoot(u, parent);
        if(rootV != rootU)
        {
            if(size[rootV] < size[rootU])
            {
                parent[rootV] = rootU;
                size[rootU] += size[rootV];
            }
            else
            {
                parent[rootU] = rootV;
                size[rootV] += size[rootU];
            }
        }
    }
}
int main()
{
    int n;
    std::cin >> n;

    int a[n];
    int parent[n];
    int size[n];
    int maxStrength[n];
    for(int i = 0; i < n; i++)
    {
        std::cin >> a[i];
        parent[i] = -1;
        maxStrength[i] = 0;
        size[i] = -1;
    }

    int sortedIndexes[n];
    std::iota(&sortedIndexes[0], &sortedIndexes[n], 0);
    std::sort(&sortedIndexes[0], &sortedIndexes[n], [&a](int i, int j){return a[i] > a[j];});
    for(int i : sortedIndexes)
    {
        parent[i] = i;
        size[i] = 1;
        if(i != n - 1)
            unionSets(i, i + 1, parent, size);
        if(i != 0)
            unionSets(i, i - 1, parent, size);
        int x = size[findRoot(i, parent)];
        if(maxStrength[x - 1] < a[i])
        {
            maxStrength[x - 1] = a[i];
        }
    }

    int prevMax = maxStrength[n - 1];
    for(int i = n - 2; i >= 0; i--)
    {
        if(maxStrength[i] < prevMax)
        {
            maxStrength[i] = prevMax;
        }
        else
        {
            prevMax = maxStrength[i];
        }
    }
    std::string out;
    for(int i = 0; i < n; i++)
    {
        out.append(std::to_string(maxStrength[i]));
        out.append(" ");
    }
    std::cout << out << std::endl;


}
