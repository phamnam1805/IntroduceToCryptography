import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class BaiTap3 {
    static int registerA[] = new int[3];
    static int registerB[] = new int[3];
    static int registerC[] = new int[4];

    public static void main(String[] args) {
        String inputFilePath = "E:\\Document\\ATTT\\CodeBaiTap\\InputFile.txt";
        String outputFilePath = "E:\\Document\\ATTT\\CodeBaiTap\\OutputFile.txt";
        String ivOutputFilePath = "E:\\Document\\ATTT\\CodeBaiTap\\iv.txt";
        String keyOutputFilePath = "E:\\Document\\ATTT\\CodeBaiTap\\key.txt";
        String decodeFilePath = "E:\\Document\\ATTT\\CodeBaiTap\\DecodeFile.txt";

        encode(inputFilePath, outputFilePath, ivOutputFilePath, keyOutputFilePath);
        decode(outputFilePath, decodeFilePath, ivOutputFilePath, keyOutputFilePath);

    }

    public static void encode(String inputFilePath, String outputFilePath, String ivOutputFilePath, String keyOutputFilePath){
        constructRegisters();
        File ivOutputFile = new File(ivOutputFilePath);
        File keyOutputFile = new File(keyOutputFilePath);
        try {
            FileOutputStream ivOutputStream = new FileOutputStream(ivOutputFile);
            FileOutputStream keyOutputStream = new FileOutputStream(keyOutputFile);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            baos.write(Integer.toHexString(registerA[0]).getBytes());
            baos.write(Integer.toHexString(registerA[1]).getBytes());
            baos.write(Integer.toHexString(registerA[2]).getBytes());
            baos.writeTo(ivOutputStream);
            baos.close();
            ivOutputStream.close();
            baos = new ByteArrayOutputStream();
            baos.write(Integer.toHexString(registerB[0]).getBytes());
            baos.write(Integer.toHexString(registerB[1]).getBytes());
            baos.write(Integer.toHexString(registerB[2]).getBytes());
            baos.writeTo(keyOutputStream);
            baos.close();
            keyOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        for (int i = 1; i <= 1152 ; i++) {
            generateNextBit();
        }

        File inputFile = new File(inputFilePath);
        File outputFile = new File(outputFilePath);
        try {
            FileInputStream inputStream = new FileInputStream(inputFile);
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            byte content[] = new byte[(int)inputFile.length()];
            inputStream.read(content);
            int k = 0;
            long start = System.currentTimeMillis();
            for (int i = 0; i < content.length; i++) {
                k = generateNextByte();
                content[i] =(byte) (content[i] ^ k);
            }
            long end = System.currentTimeMillis();
            System.out.println("Encode time: "+(end-start));
            outputStream.write(content);
            inputStream.close();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void decode(String inputFilePath, String outputFilePath, String ivInputFilePath, String keyInputFilePath){
        File ivInputFile = new File(ivInputFilePath);
        File keyInputFile = new File(keyInputFilePath);
        int IV[] = new int[3];
        int key[] = new int[3];
        try {
            FileInputStream ivInputStream = new FileInputStream(ivInputFile);
            FileInputStream keyInputStream = new FileInputStream(keyInputFile);
            byte ivBytes[] = new byte[(int)ivInputFile.length()];
            ivInputStream.read(ivBytes);
            ivInputStream.close();
            String ivString = new String(ivBytes, StandardCharsets.UTF_8);
            IV[0] =(int) Long.parseLong(ivString.substring(0, 8),16);
            IV[1] =(int) Long.parseLong(ivString.substring(8, 16),16);
            IV[2] =(int) Long.parseLong(ivString.substring(16, ivString.length()),16);


            byte keyBytes[] = new byte[(int)keyInputFile.length()];
            keyInputStream.read(keyBytes);
            keyInputStream.close();
            String keyString = new String(keyBytes, StandardCharsets.UTF_8);
            key[0] =(int) Long.parseLong(keyString.substring(0, 8),16);
            key[1] =(int) Long.parseLong(keyString.substring(8, 16),16);
            key[2] =(int) Long.parseLong(keyString.substring(16, keyString.length()),16);
            constructRegisters(IV, key);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        for (int i = 1; i <= 1152 ; i++) {
            generateNextBit();
        }

        File inputFile = new File(inputFilePath);
        File outputFile = new File(outputFilePath);
        try {
            FileInputStream inputStream = new FileInputStream(inputFile);
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            byte content[] = new byte[(int)inputFile.length()];
            inputStream.read(content);
            int k = 0;
            long start = System.currentTimeMillis();
            for (int i = 0; i < content.length; i++) {
                k = generateNextByte();
                content[i] =(byte) (content[i] ^ k);
            }
            long end = System.currentTimeMillis();
            System.out.println("Decode time: "+(end-start));
            outputStream.write(content);
            inputStream.close();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void constructRegisters() {
        registerA = constructRandom80bits();
        registerB = constructRandom80bits();
        registerC[0] = 0;
        registerC[1] = 0;
        registerC[2] = 0;
        registerC[3] = 7 << 17;
    }

    public static void constructRegisters(int IV[], int key[]) {
        registerA = IV;
        registerB = key;
        registerC[0] = 0;
        registerC[1] = 0;
        registerC[2] = 0;
        registerC[3] = 7 << 17;
    }

    public static int[] constructRandom80bits() {
        int randomBits[] = new int[3];
        Random generator = new Random();
        randomBits[0] = generator.nextInt();
        randomBits[1] = generator.nextInt();
        int temp = generator.nextInt(65536);
        randomBits[2] = temp << 16;
        return randomBits;
    }

    public static int generateNextBit() {
        int bitA = getBit(registerA[2], 66 - 64);
        bitA = bitA ^ (getBit(registerA[2], 91 - 64) & getBit(registerA[2], 92 - 64));
        bitA = bitA ^ getBit(registerA[2], 93 - 64);

        int bitB = getBit(registerB[2], 69 - 64);
        bitB = bitB ^ (getBit(registerB[2], 82 - 64) & getBit(registerB[2], 83 - 64));
        bitB = bitB ^ getBit(registerB[2], 84 - 64);

        int bitC = getBit(registerC[2], 66 - 64);
        bitC = bitC ^ (getBit(registerC[3], 109 - 96) & getBit(registerC[3], 110 - 96));
        bitC = bitC ^ getBit(registerC[3], 111 - 96);

        int headA = getBit(registerA[2], 69 - 64) ^ bitC;
        int headB = getBit(registerB[2], 78 - 64) ^ bitA;
        int headC = getBit(registerC[2], 87 - 64) ^ bitB;
        shiftBit(registerA, headA, 93);
        shiftBit(registerB, headB, 84);
        shiftBit(registerC, headC, 111);
        int nextBit = bitA ^ bitB ^ bitC;
        return nextBit;
    }

    public static int generateNextByte(){
        int b = 0;
        for (int i = 1; i <= 8 ; i++) {
            b = appendRightMost(b, generateNextBit() == 1? true: false);
        }
        return b;
    }

    public static int appendLeftMost(int number, boolean bit) {
        if (bit) {
            return number >> 1 | 1 << 31;
        } else {
            return number >> 1 & 0x7fffffff;
        }
    }

    public static int appendRightMost(int number, boolean bit){
        if(bit){
            return number << 1 | 1;
        }else{
            return number << 1;
        }
    }

    public static void shiftBit(int number[], int bit, int length) {
        int n = length / 32 + 1;
        int lastBits[] = new int[n];
        for (int i = 0; i <= n - 2; i++) {
            lastBits[i] = getBit(number[i], 32);
        }
        for (int i = 1; i <= n - 1; i++) {
            number[i] = appendLeftMost(number[i], lastBits[i - 1] == 1 ? true : false);
        }
        number[0] = appendLeftMost(number[0], bit == 1 ? true : false);
    }

    public static int getBit(int number, int index) {
        return (number & (1 << (32 - index))) >> (32 - index);
    }

    public static String getBits(int number, int groupSize) {
        StringBuilder result = new StringBuilder();

        for (int i = 31; i >= 0; i--) {
            int mask = 1 << i;
            result.append((number & mask) != 0 ? "1" : "0");

            if (i % groupSize == 0)
                result.append(" ");
        }
        result.replace(result.length() - 1, result.length(), "");

        return result.toString();
    }

}

