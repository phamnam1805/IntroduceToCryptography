import java.util.ArrayList;

public class BaiTap1 {
    public static void main(String[] args) {
        String s = "lrvmnir bpr sumvbwvr jx bpr lmiwv yjeryrkbi jx qmbm wi\n" +
                "bpr xjvni mkd ymibrut jx irhx wi bpr riirkvr jx\n" +
                "ymbinlmtmipw utn qmumbr dj w ipmhh but bj rhnvwdmbr bpr\n" +
                "yjeryrkbi jx bpr qmbm mvvjudwko bj yt wkbrusurbmbwjk\n" +
                "lmird jk xjubt trmui jx ibndt\n" +
                "\n" +
                "wb wi kjb mk rmit bmiq bj rashmwk rmvp yjeryrkb mkd wbi\n" +
                "iwokwxwvmkvr mkd ijyr ynib urymwk nkrashmwkrd bj ower m\n" +
                "vjyshrbr rashmkmbwjk jkr cjnhd pmer bj lr fnmhwxwrd mkd\n" +
                "wkiswurd bj invp mk rabrkb bpmb pr vjnhd urmvp bpr ibmbr\n" +
                "jx rkhwopbrkrd ywkd vmsmlhr jx urvjokwgwko ijnkdhrii\n" +
                "ijnkd mkd ipmsrhrii ipmsr w dj kjb drry ytirhx bpr xwkmh\n" +
                "mnbpjuwbt lnb yt rasruwrkvr cwbp qmbm pmi hrxb kj djnlb\n" +
                "bpmb bpr xjhhjcwko wi bpr sujsru msshwvmbwjk mkd\n" +
                "wkbrusurbmbwjk w jxxru yt bprjuwri wk bpr pjsr bpmb bpr\n" +
                "riirkvr jx jqwkmcmk qmumbr cwhh urymwk wkbmvb";

        int count[] = new int[128];
        int sum = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 97 && c <= 122) {
                count[c] += 1;
                sum += 1;
            }
        }

        char key[][] = new char[2][26]; // luu khoa
        key[0][0] = 'E';
        key[0][1] = 'T';
        key[0][2] = 'A';
        key[0][3] = 'O';
        key[0][4] = 'I';
        key[0][5] = 'N';
        key[0][6] = 'S';
        key[0][7] = 'H';
        key[0][8] = 'R';
        key[0][9] = 'D';
        key[0][10] = 'L';
        key[0][11] = 'C';
        key[0][12] = 'U';
        key[0][13] = 'M';
        key[0][14] = 'W';
        key[0][15] = 'F';
        key[0][16] = 'G';
        key[0][17] = 'Y';
        key[0][18] = 'P';
        key[0][19] = 'B';
        key[0][20] = 'V';
        key[0][21] = 'K';
        key[0][22] = 'X';
        key[0][23] = 'J';
        key[0][24] = 'Q';
        key[0][25] = 'Z';

        int sortFrequencyCiphertext[] = new int[26];
        for (int i = 0; i < 26; i++) {
            sortFrequencyCiphertext[i] = i + 97;
        }

        double frequencyCiphertext[] = new double[128];
        for (int i = 97; i <= 122; i++) {
            frequencyCiphertext[i] = count[i] * 1.0 / sum;
        }

        for (int i = 97; i <= 122; i++) {
            for (int j = i + 1; j <= 122; j++) {
                if (frequencyCiphertext[i] <= frequencyCiphertext[j]) {
                    double temp = frequencyCiphertext[i];
                    frequencyCiphertext[i] = frequencyCiphertext[j];
                    frequencyCiphertext[j] = temp;
                    int tempIndex = sortFrequencyCiphertext[i - 97];
                    sortFrequencyCiphertext[i - 97] = sortFrequencyCiphertext[j - 97];
                    sortFrequencyCiphertext[j - 97] = tempIndex;
                }
            }
        }

        for (int i = 0; i < 26; i++) {
            key[1][i] = (char) sortFrequencyCiphertext[i];
        }


        //tu bang tan suat co the giai duoc 1 vai ki tu la E,T,H,A.
        ArrayList<Character> trueLetters = new ArrayList<>();// List trueLetters luu nhung ki tu da doan dung
        trueLetters.add('E');
        trueLetters.add('T');
        trueLetters.add('H');
        trueLetters.add('A');
        trueLetters.add('S');
        trueLetters.add('R');

//         sau do doc doan van ban va giai dan cac ki tu con lai
        // thay w = I
        modifyKey(trueLetters, key, 'I', 'w');

        // thay q = K
        modifyKey(trueLetters, key, 'K', 'q');

        //thay t = Y
        modifyKey(trueLetters, key, 'Y', 't');

        //thay v = C
        modifyKey(trueLetters, key, 'C','v');

        //thay l = B
        modifyKey(trueLetters, key, 'B','l');

        //thay n = U
        modifyKey(trueLetters, key, 'U','n');

        //thay k = N
        modifyKey(trueLetters, key, 'N','k');

        //thay d = D
        modifyKey(trueLetters, key, 'D','d');

        //thay j = O
        modifyKey(trueLetters, key, 'O','j');

        //thay x = F
        modifyKey(trueLetters, key, 'F','x');

        //thay o = G
        modifyKey(trueLetters, key, 'G','o');

        //thay e = V
        modifyKey(trueLetters, key, 'V','e');

        //thay y = M
        modifyKey(trueLetters, key, 'M','y');


        //thay s = P
        modifyKey(trueLetters, key, 'P','s');

        //thay h = L
        modifyKey(trueLetters, key, 'L','h');

        //thay c = W
        modifyKey(trueLetters, key, 'W','c');

        //thay a = X
        modifyKey(trueLetters, key, 'X','a');

        //thay g = Z
        modifyKey(trueLetters, key, 'Z','g');

        trueLetters.add('Q');

        System.out.println("Key: ");
        for (int i = 0; i < 26; i++) {
            System.out.print(key[0][i] + " = " + key[1][i] + ", ");
        }
        System.out.println();

        for (int i = 0; i < 26; i++) {
            if (contain(key[0][i], trueLetters)) {
                s = s.replace(key[1][i], key[0][i]);
            }
        }

        System.out.println(s);
    }

    public static boolean contain(char c, ArrayList<Character> trueLetters) {
        for (int i = 0; i < trueLetters.size(); i++) {
            if (trueLetters.get(i) == c) return true;
        }
        return false;
    }

    public static void modifyKey(ArrayList<Character> trueLetters, char key[][], char c1, char c2) {
        trueLetters.add(c1);
        for (int i = 0; i < 26; i++) {
            if (key[0][i] == c1) {
                key[1][i] = c2;
            }
        }
    }
}
