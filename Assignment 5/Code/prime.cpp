#include <bits/stdc++.h>
#include <gmp.h>

using namespace std;
gmp_randstate_t randstate;
int first400Prime[400];

void generateRandomBits(mpz_t randomNumber, int bits)
{
    mpz_init(randomNumber);
    mpz_urandomb(randomNumber, randstate, bits);
}
int checkFermat(mpz_t n)
{
    mpz_t n_1, powNumber;
    mpz_init(n_1);
    mpz_init_set_ui(powNumber, 2);
    mpz_sub_ui(n_1, n, 1);
    mpz_powm(powNumber, powNumber, n_1, n);
    if (mpz_cmp_ui(powNumber, 1) == 0)
    {
        return 0;
    }
    return 1;
}

int witness(mpz_t a, mpz_t n)
{
    mpz_t n_1;
    mpz_init(n_1);
    mpz_sub_ui(n_1, n, 1);

    
    mpz_t u;
    mpz_init(u);
    unsigned long t = 0;
    do
    {
        t += 1;
        while (mpz_divisible_ui_p(n_1, pow(2, t)) == 0)
        {
            t++;
        }
        mpz_div_ui(u, n_1, pow(2, t));
    } while (mpz_odd_p(u) == 0);
    mpz_t xi, xi_1;
    mpz_init(xi_1);
    mpz_init(xi);
    mpz_powm(xi_1, a, u, n);
    for (int i = 1; i <= t; i++)
    {
        mpz_powm_ui(xi, xi_1, 2, n);
        if ((mpz_cmp_ui(xi, 1) == 0) && (mpz_cmp_ui(xi_1, 1) != 0) && (mpz_cmp(xi_1, n_1) != 0))
        {
            return 1;
        }
        mpz_set(xi_1, xi);
    }
    if (mpz_cmp_ui(xi, 1) != 0)
    {
        return 1;
    }
    return 0;
}
int rabin_Miller(mpz_t n, int s)
{
    for (int i = 1; i <= s; i++)
    {
        mpz_t a;
        generateRandomBits(a, 16);
        if (witness(a, n) == 1)
        {
            return 1;
        }
    }
    return 0;
}

int checkPrime(mpz_t n)
{

    for (int i = 0; i < 400; i++)
    {
        // if (mpz_cmp_ui(n, first400Prime[i]) == 0)
        // {
        //     return 0;
        // }
        if (mpz_divisible_ui_p(n, first400Prime[i]) != 0)
        {
            return 1;
        }
    }

    if (checkFermat(n) == 1)
    {
        return 1;
    }

    if (rabin_Miller(n, 64) == 1)
        return 1;
    else
    {
        return 0;
    }
}

void makePrime(mpz_t n, int bits)
{
    generateRandomBits(n, bits);
    mpz_setbit(n, 0);
    mpz_setbit(n, bits-1);
    while (checkPrime(n) == 1)
    {
        mpz_add_ui(n, n, 2);
    }
}
void generateFirst400Prime()
{
    first400Prime[0] = 2;
    int index = 1;
    while (index < 400)
    {
        int number = first400Prime[index - 1];
        bool isPrime;
        do
        {
            isPrime = true;
            number += 1;
            for (int i = 2; i < number; i++)
            {
                if (number % i == 0)
                {
                    isPrime = false;
                    break;
                }
            }
        } while (!isPrime);
        first400Prime[index] = number;
        index += 1;
    }

}

void init()
{
    gmp_randinit_mt(randstate);
    gmp_randseed_ui(randstate, (unsigned long)time(0));
    generateFirst400Prime();
}
int main()
{
    init();
    cout << "Start:" << endl;
    int i = 0;
    clock_t start = clock();
    while (i < 10)
    {
        i++;
        mpz_t n;
        makePrime(n, 3072);
    }
    clock_t end = clock();
    cout << (end - start) << endl;
    // gmp_printf("%Zd\n", n);
    return 0;
}