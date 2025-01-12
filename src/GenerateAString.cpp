//https://codeforces.com/contest/710/problem/E - Not Accepted

#include <iostream>
#include <cmath>
#include <stack>
#include <vector>

struct state
{
    long long time, length;
    state(long long t, long long l)
    {
        time = t;
        length = l;
    }
};

int main()
{
    const long long maxLLong = 9223372036854775807;
    long long n, x, y;
    std::cin >> n;
    std::cin >> x;
    std::cin >> y;
    std::stack<state> dfs;
    dfs.push(state(0, 0));
    std::vector<long long> minTime;
    for(long long i = 0; i <= n * 2; i++)
    {
        minTime.push_back(maxLLong);
    }
    while(!dfs.empty())
    {
        state first = dfs.top();
        dfs.pop();
        std::cout << first.time << " " << first.length << std::endl;
        if(minTime.size() <= first.length)
        {
            minTime.insert(minTime.begin() + minTime.size(), first.length, maxLLong);
        }

        if(first.length <= n && first.time < minTime[first.length])
        {
            minTime[first.length] = first.time;
            if(first.length != 0)
            {
                dfs.push(state(first.time + x, first.length - 1));
            }
            if(first.length < n)
            {
                if(x * first.length <= y || y + x * abs(n - first.length * 2) > x * abs(n - first.length))
                {
                    dfs.push(state(first.time + x, first.length + 1));
                }
                else if((int) log2((double)n / first.length) == log2((double)n / first.length))
                {
                    dfs.push(state(first.time + y, first.length * 2));
                }
                else if((int) log2(n - first.length + 1) == log2(n - first.length - 1))
                {
                    dfs.push(state(first.time + y, first.length * 2));
                }
                else
                {
                    dfs.push(state(first.time + x, first.length + 1));
                    dfs.push(state(first.time + y, first.length * 2));
                }
            }
        }
    }
    std::cout << minTime[n] << std::endl;
}