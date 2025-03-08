//o3-mini-high wrote this solution in one shot (which is kind of crazy if you ask me)
//https://codeforces.com/problemset/problem/2030/E

#include <bits/stdc++.h>
using namespace std;

typedef long long ll;

const ll MOD = 998244353;
// Maximum value needed – since n ≤ 2e5 and frequencies are at most n.
const int MAX = 200005;

ll fact[MAX], invfact[MAX], pow2[MAX];

// Fast exponentiation modulo mod.
ll modexp(ll base, ll exp, ll mod) {
    ll res = 1 % mod;
    base %= mod;
    while(exp){
        if(exp & 1)
            res = (res * base) % mod;
        base = (base * base) % mod;
        exp >>= 1;
    }
    return res;
}

// Precompute factorials, inverse factorials, and powers of 2 modulo MOD.
void precompute() {
    fact[0] = 1;
    for (int i = 1; i < MAX; i++){
        fact[i] = (fact[i-1] * i) % MOD;
    }
    invfact[MAX-1] = modexp(fact[MAX-1], MOD-2, MOD);
    for (int i = MAX-2; i >= 0; i--){
        invfact[i] = (invfact[i+1] * (i+1)) % MOD;
    }
    pow2[0] = 1;
    for (int i = 1; i < MAX; i++){
        pow2[i] = (pow2[i-1] * 2) % MOD;
    }
}

// Compute binom(n,k) modulo MOD.
ll binom(int n, int k){
    if(k < 0 || k > n) return 0;
    return ((fact[n] * invfact[k]) % MOD * invfact[n-k]) % MOD;
}

// For a given frequency F and for d = 1,2,…,F+1, compute
// L(F,d) = 2^F - (binom(F,0) + binom(F,1) + ... + binom(F,d-1))  (mod MOD).
// (Note that L(F,d)=0 when d > F.)
vector<ll> computeL(int F){
    vector<ll> L(F+2, 0); // indices 1..F+1 (we set L[F+1]=0).
    ll tot = pow2[F]; // 2^F mod MOD.
    ll cumulative = 0;
    for (int d = 1; d <= F+1; d++){
        cumulative = (cumulative + binom(F, d-1)) % MOD;
        ll cur = (tot - cumulative) % MOD;
        if(cur < 0) cur += MOD;
        L[d] = cur;
    }
    return L;
}

// Main
int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    precompute();

    int t;
    cin >> t;
    while(t--){
        int n;
        cin >> n;
        vector<int> a(n);
        for (int i=0; i<n; i++){
            cin >> a[i];
        }

        // Compute F[x] = frequency of x in a (for x = 0,1,…, n-1)
        vector<int> Freq(n, 0);
        for (int i = 0; i < n; i++){
            Freq[a[i]]++;
        }

        // Let m = mex(a), i.e. the smallest x with Freq[x] == 0.
        int m = 0;
        while(m < n && Freq[m] > 0)
            m++;

        // If m == 0 then every subsequence has score 0.
        if(m == 0){
            cout << 0 << "\n";
            continue;
        }

        // Precompute a suffix–product: suff[i] = ∏_{x=i}^{n-1} 2^(Freq[x])
        // (these “free–choices” come from numbers x ≥ i).
        vector<ll> suff(n+1, 1);
        suff[n] = 1;
        for (int i = n-1; i >= 0; i--){
            suff[i] = (suff[i+1] * pow2[Freq[i]]) % MOD;
        }

        // For x in [0, m-1] (which occur in a) precompute the arrays Lvals[x]:
        // Lvals[x][d] = L(Freq[x], d) for d = 1,…,Freq[x]+1.
        vector<vector<ll>> Lvals(m);
        for (int x = 0; x < m; x++){
            Lvals[x] = computeL(Freq[x]);
        }

        // Now we “build” a DP for indices 0,1,…,m-1.
        // For 0 ≤ j < m let dp[j][d] = ∏_{i=0}^{j} L(Freq[i], d),
        // where d runs from 1 to M_j = min_{0≤i≤j} Freq[i] (since L(Freq[i], d) = 0 for d > Freq[i]).
        // Then the contribution from indices 0..j is S(j) = ∑_{d=1}^{M_j} dp[j][d],
        // and every subsequence which “uses” indices 0..j (i.e. has f_0,…,f_j ≥ 1)
        // gets an extra factor of ∏_{x=j+1}^{n-1}2^(Freq[x]).
        // Hence the total answer is
        //    ans = ∑_{j=0}^{m-1} S(j) * (∏_{x=j+1}^{n-1}2^(Freq[x])).

        ll ans = 0;
        int curM = Freq[0]; // current limit: for j = 0 we have d = 1,…,Freq[0].
        // dp for j = 0:
        // Let dp[d] = Lvals[0][d] for d = 1,…,Freq[0].
        vector<ll> dp(curM+1, 0); // using indices 1..curM (we will ignore index 0)
        for (int d = 1; d <= curM; d++){
            dp[d] = Lvals[0][d];
        }
        ll sum_dp = 0;
        for (int d = 1; d <= curM; d++){
            sum_dp = (sum_dp + dp[d]) % MOD;
        }
        ans = (ans + sum_dp * suff[1]) % MOD;

        // Now, for j = 1,2,…,m-1, update the dp.
        for (int j = 1; j < m; j++){
            int newM = min(curM, Freq[j]);
            vector<ll> newdp(newM+1, 0);
            for (int d = 1; d <= newM; d++){
                // For each allowed d, update dp by multiplying by Lvals[j][d].
                newdp[d] = (dp[d] * Lvals[j][d]) % MOD;
            }
            dp = move(newdp);
            curM = newM;
            ll curSum = 0;
            for (int d = 1; d <= curM; d++){
                curSum = (curSum + dp[d]) % MOD;
            }
            ans = (ans + curSum * suff[j+1]) % MOD;
        }

        ans %= MOD;
        cout << ans % MOD << "\n";
    }
    return 0;
}
