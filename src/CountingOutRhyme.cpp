#include <iostream>
#include <vector>

int main()
{
    int t;
    std::cin >> t;
    while(t--)
    {
        int n, k;
        std::cin >> n >> k;
        std::vector<int> dp(n);
        for(int i = 1; i <= n; i++)
        {
            dp[i - 1] = i;
        }
        for(int r = n; r > 1; r--)
        {
            std::vector<int> newDP;
            for(int i = 0; i < dp.size(); i++)
            {
                if(dp[i] + (k % r - 1) != 1)
                {
                    if((k % r - 1) == 0)
                    {
                        newDP.push_back(dp[i + 1 == dp.size() - 1 ? i + 1 : i + 1 % r]);
                    }
                    else
                    {
                        newDP.push_back(dp[i]);
                    }
                }
            }
            dp = std::vector<int>();
            for(int i = 0; i < newDP.size(); i++)
            {
                dp.push_back(newDP[i]);
            }
        }
        std::cout << dp[0] << std::endl;
    }

}