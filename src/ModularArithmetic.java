public class ModularArithmetic
{
    //mod must be a prime number for certain optimizations to work
    static long mod = 1000000007L;
    public static long powMod(long base, long exp)
    {
        long b = 1;
        long A = base;
        if((exp & 1) == 1)
        {
            b = base % mod;
        }
        exp  = (exp >> 1);
        while(exp > 0)
        {
            A = (A * A) % mod;
            if((exp & 1) == 1)
            {
                b = (A * b) % mod;
            }
            exp = (exp >> 1);
        }

        return b % mod;
    }
}
