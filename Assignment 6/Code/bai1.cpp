#include <bits/stdc++.h>
#include "gmp.h"

using namespace std;

int main(){
    mpz_t n, a, a_pow2, x, p, q, check;

    //init
    mpz_init_set_str(n, "179769313486231590772930519078902473361797697894230657273430081157732675805505620686985379449212982959585501387537164015710139858647833778606925583497541085196591615128057575940752635007475935288710823649949940771895617054361149474865046711015101563940680527540071584560878577663743040086340742855278549092581", 10);
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