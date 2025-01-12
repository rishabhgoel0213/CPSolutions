//https://codeforces.com/contest/638/problem/B - Accepted

#include <iostream>
#include <numeric>
#include <algorithm>
#include <string.h>
#include <stack>
#include <set>

int main()
{
    int n;
    std::cin >> n;
    int child[26];
    for(int i = 0; i < 26; i++)
    {
        child[i] = -1;
    }

    std::set<int> roots;
    for(int i = 0; i < n; i++)
    {
        std::string s;
        std::cin >> s;
        for(int j = 0; j < s.length(); j++)
        {
            roots.insert(((int)s[j]) - 97);
            if(j != s.length() - 1)
            {
                child[((int)s[j]) - 97] = ((int) s[j + 1]) - 97;
            }
        }
    }
    for(int i = 0; i < 26; i++)
    {
        if(child[i] != -1)
        {
            roots.erase(child[i]);
        }
    }
    std::stack<int> dfs;
    for(int i = 0; i < 26; i++)
    {
        if(roots.count(i) > 0)
        {
            dfs.push(i);
        }
    }
    while(!dfs.empty())
    {
        int m = dfs.top();
        dfs.pop();
        if(m != -1)
        {
            dfs.push(child[m]);
            std::cout << (char)(m + 97);
        }
    }
}