#include <bits/stdc++.h>
#include "gmp.h"

using namespace std;

int main()
{
    mpz_t n, a, a_pow2, x, p, q, check, delta, delta_sqrt;

    //init
    mpz_init_set_str(n, "720062263747350425279564435525583738338084451473999841826653057981916355690188337790423408664187663938485175264994017897083524079135686877441155132015188279331812309091996246361896836573643119174094961348524639707885238799396839230364676670221627018353299443241192173812729276147530748597302192751375739387929", 10);
    // mpz_init_set_str(n, "648455842808071669662824265346772278726343720706976263060439070378797308618081116462714015276061417569195587321840254520655424906719892428844841839353281972988531310511738648965962582821502504990264452100885281673303711142296421027840289307657458645233683357077834689715838646088239640236866252211790085787877", 10);
    mpz_init(a);
    mpz_init(x);
    mpz_init(a_pow2);
    mpz_init(p);
    mpz_init(q);
    mpz_init(check);
    mpz_init(delta);
    mpz_init(delta_sqrt);

    //calculate
    mpz_mul_ui(a, n, 6); // a =6*n
    mpz_sqrt(a, a);      // a = can (6*n)
    mpz_mul_ui(a, a, 2); // a = 2*can(6*n) = 3p + 2q
    int i = 0, cmp;
    do
    {
        mpz_add_ui(a, a, 1);      // a = a+1
        mpz_pow_ui(a_pow2, a, 2); // a = a^2
        mpz_mul_ui(delta, n, 24);
        mpz_sub(delta, a_pow2, delta);
        if (mpz_cmp_ui(delta, 0) >= 0)
        {
            mpz_sqrt(delta_sqrt, delta);

            mpz_add(p, a, delta_sqrt);
            mpz_div_ui(p, p, 6);
            mpz_div(q, n, p);

            mpz_mul(check, p, q); // check = p*q
            cmp = mpz_cmp(n, check);
            if (cmp != 0)
            {
                mpz_sub(p, a, delta_sqrt);
                mpz_div_ui(p, p, 6);
                mpz_div(q, n, p);
                mpz_mul(check, p, q); // check = p*q
                cmp = mpz_cmp(n, check);
            }
        }
        else
        {
            continue;
        }
        i += 1;
    } while (cmp != 0);
    gmp_printf("%Zd\n", p);
    gmp_printf("%Zd\n", q);
    cout << "thoa man p*q = n sau " << i << " buoc lap" << endl;
    return 0;
}