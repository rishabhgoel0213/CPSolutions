#include <iostream>
#include <vector>

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
    int n, k;
    std::cin >> n >> k;
    std::vector<int> frequency(n + 1, 0);
    std::vector<int> tree(n + 1, 0);
    int total = 0;
    for(int i = 0; i < n; i++)
    {
        int a;
        std::cin >> a;
        frequency[a]++;
        add(a, 1, n, tree);
        if(i == k)
        {
            total = 1;
        }
        else if(i > k)
        {
            total += sum(a - 1, tree);
        }
    }
    std::cout << total << std::endl;
}