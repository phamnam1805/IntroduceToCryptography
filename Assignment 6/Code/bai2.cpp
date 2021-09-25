#include <bits/stdc++.h>
#include "gmp.h"

using namespace std;

int main()
{
    mpz_t n, a, a_pow2, x, p, q, check;

    //init
    mpz_init_set_str(n, "648455842808071669662824265346772278726343720706976263060439070378797308618081116462714015276061417569195587321840254520655424906719892428844841839353281972988531310511738648965962582821502504990264452100885281673303711142296421027840289307657458645233683357077834689715838646088239640236866252211790085787877", 10);
    mpz_init(a);
    mpz_init(x);
    mpz_init(a_pow2);
    mpz_init(p);
    mpz_init(q);
    mpz_init(check);

    //calculate
    mpz_sqrt(a, n); // a = can n
    int i = 0, cmp;
    do
    {
        mpz_add_ui(a, a, 1);      // a = a+1
        mpz_pow_ui(a_pow2, a, 2); // a = a^2
        mpz_sub(x, a_pow2, n);    // x = a^2-n
        mpz_sqrt(x, x);           // x = can x
        mpz_sub(p, a, x);         // p = a-x
        mpz_add(q, a, x);         // p = a+x
        mpz_mul(check, p, q);     // check = p*q
        cmp = mpz_cmp(n, check);
        i += 1;
    } while (cmp != 0);
    gmp_printf("%Zd\n", p);
    gmp_printf("%Zd\n", q);
    cout << "thoa man p*q = n sau " << i << " buoc lap" << endl;
    return 0;
}